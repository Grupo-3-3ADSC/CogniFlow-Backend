package sptech.school.CRUD.application.service.ItemOrdemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.CRUD.domain.entity.*;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.ConjuntoOrdemDeCompra.ConjuntoOrdemDeCompraRepository;
import sptech.school.CRUD.infrastructure.persistence.estoque.EstoqueRepository;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.infrastructure.persistence.ordemDeCompra.OrdemDeCompraRepository;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CadastrarMultiplasOrdensService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstoqueRepository estoqueRepository;
    private final ConjuntoOrdemDeCompraRepository conjuntoRepository;
    private final FornecedorRepository fornecedorRepository;

    @Transactional
    public List<OrdemDeCompraModel> cadastrarMultiplasOrdens(List<OrdemDeCompraCadastroDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            throw new RequisicaoInvalidaException("Nenhuma ordem de compra foi enviada.");
        }

        // 1️⃣ Cria o CONJUNTO primeiro (vazio)
        ConjuntoOrdemDeCompraModel conjunto = new ConjuntoOrdemDeCompraModel();
        conjunto = conjuntoRepository.save(conjunto);

        // 2️⃣ Processa cada DTO e cria as ordens
        List<OrdemDeCompraModel> ordens = new ArrayList<>();
        ConjuntoOrdemDeCompraModel conjuntoFinal = conjunto; // Para usar no loop

        for (OrdemDeCompraCadastroDto dto : dtos) {
            // Usa o Mapper para construir a entidade
            OrdemDeCompraModel ordemDeCompra = OrdemDeCompraMapper.toEntity(dto);

            // Busca e seta as entidades relacionadas
            FornecedorModel fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado"));
            ordemDeCompra.setFornecedor(fornecedor);

            EstoqueModel estoque = estoqueRepository.findById(dto.getEstoqueId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado"));
            ordemDeCompra.setEstoque(estoque);

            UsuarioModel usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
            ordemDeCompra.setUsuario(usuario);

            // Validação de rastreabilidade
            if (ordemDeCompraRepository.existsByRastreabilidadeAndEstoqueId(dto.getRastreabilidade(), dto.getEstoqueId())) {
                throw new RequisicaoConflitanteException("Rastreabilidade já cadastrada para este estoque");
            }

            ordemDeCompra.setPendenciaAlterada(false);

            // 3️⃣ ASSOCIA a ordem ao conjunto
            ordemDeCompra.setConjuntoOrdemDeCompra(conjuntoFinal);

            // Cálculo do total com base no tipo de compra
            Double total = 0.0;
            if ("UNIDADE".equalsIgnoreCase(dto.getTipoCompra())) {
                total = dto.getQuantidade() * dto.getValorUnitario();
                ordemDeCompra.setValorKg(null);
            } else if ("QUILO".equalsIgnoreCase(dto.getTipoCompra())) {
                total = dto.getQuantidade() * dto.getValorKg();
                ordemDeCompra.setValorUnitario(null);
            }


            ordens.add(ordemDeCompra);
        }

        // 4️⃣ Salva todas as ordens de uma vez (batch insert)
        List<OrdemDeCompraModel> ordensSalvas = ordemDeCompraRepository.saveAll(ordens);

        // 5️⃣ Atualiza os estoques
        for (int i = 0; i < ordensSalvas.size(); i++) {
            OrdemDeCompraModel ordemSalva = ordensSalvas.get(i);
            OrdemDeCompraCadastroDto dto = dtos.get(i);

            EstoqueModel estoque = estoqueRepository.findById(dto.getEstoqueId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado"));

            // Atualiza a quantidade no estoque (sem distinção de tipo)
            Integer novaQuantidade = (dto.getQuantidade() != null ? dto.getQuantidade() : 0)
                    + ordemSalva.getQuantidade();

            // Verifica se a quantidade ultrapassa o limite máximo de estoque
            if (estoque.getQuantidadeMaxima() != null && novaQuantidade > estoque.getQuantidadeMaxima()) {
                throw new RequisicaoInvalidaException("A quantidade comprada ultrapassa o limite máximo de estoque permitido.");
            }

            // Atualiza a quantidade no estoque e salva
            estoque.setQuantidadeAtual(novaQuantidade);
            estoque.setUltimaMovimentacao(LocalDateTime.now());
            estoqueRepository.save(estoque);
        }

        return ordensSalvas;
    }

    @Transactional
    public List<OrdemDeCompraModel> criarOrdens(List<OrdemDeCompraModel> ordens) {
        if (ordens == null || ordens.isEmpty()) {
            throw new RuntimeException("Nenhuma ordem de compra foi enviada.");
        }

        // 1. Cria o conjunto primeiro
        ConjuntoOrdemDeCompraModel conjunto = new ConjuntoOrdemDeCompraModel();
        conjunto = conjuntoRepository.save(conjunto);

        // 2. Associa cada ordem ao conjunto
        ConjuntoOrdemDeCompraModel conjuntoFinal = conjunto;
        ordens.forEach(ordem -> {
            ordem.setConjuntoOrdemDeCompra(conjuntoFinal);
        });

        // 3. Salva as ordens (já associadas ao conjunto)
        List<OrdemDeCompraModel> ordensSalvas = ordemDeCompraRepository.saveAll(ordens);

        return ordensSalvas;
    }
}

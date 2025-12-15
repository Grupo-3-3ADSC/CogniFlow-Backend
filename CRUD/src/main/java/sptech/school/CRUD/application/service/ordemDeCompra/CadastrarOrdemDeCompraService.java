package sptech.school.CRUD.application.service.ordemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.entity.FornecedorModel;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.estoque.EstoqueRepository;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.infrastructure.persistence.ordemDeCompra.OrdemDeCompraRepository;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CadastrarOrdemDeCompraService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final EstoqueRepository estoqueRepository;
    private final UsuarioRepository usuarioRepository;
    private final FornecedorRepository fornecedorRepository;

    public OrdemDeCompraModel cadastroOrdemDeCompra(OrdemDeCompraCadastroDto dto) {

        // 1) Valida tipoCompra
        if (dto.getTipoCompra() == null ||
                !(dto.getTipoCompra().equalsIgnoreCase("UNIDADE")
                        || dto.getTipoCompra().equalsIgnoreCase("QUILO"))) {
            throw new RequisicaoInvalidaException("Tipo de compra inválido. Use UNIDADE ou QUILO.");
        }

        // 2) Valida campos conforme o tipo
        if (dto.getTipoCompra().equalsIgnoreCase("UNIDADE")) {
            if (dto.getValorUnitario() == null) {
                throw new RequisicaoInvalidaException("Valor unitário é obrigatório para compra por UNIDADE.");
            }
            if (dto.getValorKg() != null) {
                throw new RequisicaoInvalidaException("Valor por kg não deve ser informado para compra por UNIDADE.");
            }
            if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
                throw new RequisicaoInvalidaException("Quantidade (unidades) deve ser maior que zero.");
            }
        }
        else { // QUILO
            if (dto.getValorKg() == null) {
                throw new RequisicaoInvalidaException("Valor por kg é obrigatório para compra por QUILO.");
            }
            if (dto.getValorUnitario() != null) {
                throw new RequisicaoInvalidaException("Valor unitário não deve ser informado para compra por QUILO.");
            }
            if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
                throw new RequisicaoInvalidaException("Peso (kg) deve ser maior que zero.");
            }
        }

        // 3) Mapper -> entidade
        OrdemDeCompraModel ordemDeCompra = OrdemDeCompraMapper.toEntity(dto);

        // 4) Carrega relações
        FornecedorModel fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado"));
        ordemDeCompra.setFornecedor(fornecedor);

        EstoqueModel estoque = estoqueRepository.findById(dto.getEstoqueId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado"));
        ordemDeCompra.setEstoque(estoque);

        UsuarioModel usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        ordemDeCompra.setUsuario(usuario);

        // 5) Rastreabilidade duplicada
        if (ordemDeCompraRepository.existsByRastreabilidadeAndEstoqueId(dto.getRastreabilidade(), dto.getEstoqueId())) {
            throw new RequisicaoConflitanteException("Rastreabilidade já cadastrada para este estoque");
        }

        ordemDeCompra.setPendenciaAlterada(false);

        // 6) Salva a ordem
        OrdemDeCompraModel ordemSalva = ordemDeCompraRepository.save(ordemDeCompra);

// 7) Atualiza estoque SOMENTE se tipo = UNIDADE
        if (dto.getTipoCompra().equalsIgnoreCase("UNIDADE")) {
            // estoque.quantidadeAtual está em unidades (Integer ou similar)
            // Somamos as unidades compradas (arredondando a quantidade para inteiro)
            int unidadesCompradas = (int) Math.round(dto.getQuantidade()); // ex: 2.0 -> 2
            Integer quantidadeAtualEstoque = estoque.getQuantidadeAtual() != null ? estoque.getQuantidadeAtual() : 0;
            long novaQuantidade = (long) quantidadeAtualEstoque + unidadesCompradas;

            if (estoque.getQuantidadeMaxima() != null && novaQuantidade > estoque.getQuantidadeMaxima()) {
                throw new RequisicaoInvalidaException("A quantidade comprada ultrapassa o limite máximo de estoque permitido.");
            }

            // Atualiza no modelo e salva
            estoque.setQuantidadeAtual((int) novaQuantidade);
        }

        // Atualiza última movimentação sempre (bom registro)
        estoque.setUltimaMovimentacao(LocalDateTime.now());
        estoqueRepository.save(estoque);

        return ordemSalva;
    }
}


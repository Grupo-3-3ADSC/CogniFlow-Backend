package sptech.school.CRUD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.Model.OrdemDeCompraModel;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.Repository.FornecedorRepository;
import sptech.school.CRUD.Repository.OrdemDeCompraRepository;
import sptech.school.CRUD.Repository.UsuarioRepository;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdemDeCompraService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final EstoqueRepository estoqueRepository;
    private final UsuarioRepository usuarioRepository;
    private  final FornecedorRepository fornecedorRepository;

    public OrdemDeCompraModel cadastroOrdemDeCompra(OrdemDeCompraCadastroDto dto) {

        // Criar a entidade
        OrdemDeCompraModel ordemDeCompra = new OrdemDeCompraModel();

        // Setar os campos básicos
        ordemDeCompra.setPrazoEntrega(dto.getPrazoEntrega());
        ordemDeCompra.setIe(dto.getIe());
        ordemDeCompra.setCondPagamento(dto.getCondPagamento());
        ordemDeCompra.setValorKg(dto.getValorKg());
        ordemDeCompra.setRastreabilidade(dto.getRastreabilidade());
        ordemDeCompra.setValorPeca(dto.getValorPeca());
        ordemDeCompra.setDescricaoMaterial(dto.getDescricaoMaterial());
        ordemDeCompra.setValorUnitario(dto.getValorUnitario());
        ordemDeCompra.setQuantidade(dto.getQuantidade());
        ordemDeCompra.setIpi(dto.getIpi());
        ordemDeCompra.setDataDeEmissao(LocalDateTime.now());

        // ✅ AQUI ESTÁ A CORREÇÃO PRINCIPAL: Setar os IDs das FKs
        ordemDeCompra.setFornecedorId(dto.getFornecedorId());
        ordemDeCompra.setEstoqueId(dto.getEstoqueId());
        ordemDeCompra.setUsuarioId(dto.getUsuarioId());

        // Buscar e setar as entidades relacionadas (opcional para uso imediato)
        if (dto.getFornecedorId() != null) {
            FornecedorModel fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                    .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
            ordemDeCompra.setFornecedor(fornecedor);
        }

        if (dto.getEstoqueId() != null) {
            EstoqueModel estoque = estoqueRepository.findById(dto.getEstoqueId())
                    .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
            ordemDeCompra.setEstoque(estoque);
        }

        if (dto.getUsuarioId() != null) {
            UsuarioModel usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            ordemDeCompra.setUsuario(usuario);
        }

        // Salvar
        OrdemDeCompraModel ordemSalva = ordemDeCompraRepository.save(ordemDeCompra);

        // Atualizar estoque
        EstoqueModel estoque = ordemSalva.getEstoque();
        if (estoque != null) {
            Integer novaQuantidade = (estoque.getQuantidadeAtual() != null ? estoque.getQuantidadeAtual() : 0)
                    + ordemSalva.getQuantidade();
            estoque.setQuantidadeAtual(novaQuantidade);
            estoque.setUltimaMovimentacao(LocalDateTime.now());
            estoqueRepository.save(estoque);
        }

        return ordemSalva;
    }

    public List<OrdemDeCompraModel> getAll(){return ordemDeCompraRepository.findAll();}
}

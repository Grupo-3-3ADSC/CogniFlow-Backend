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
import sptech.school.CRUD.dto.OrdemDeCompra.ListagemOrdemDeCompra;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraMapper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdemDeCompraService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final EstoqueRepository estoqueRepository;
    private final UsuarioRepository usuarioRepository;
    private final FornecedorRepository fornecedorRepository;

    public OrdemDeCompraModel cadastroOrdemDeCompra(OrdemDeCompraCadastroDto dto) {
        // Usa o Mapper para construir a entidade
        OrdemDeCompraModel ordemDeCompra = OrdemDeCompraMapper.toEntity(dto);

        // Busca e seta as entidades relacionadas
        FornecedorModel fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        ordemDeCompra.setFornecedor(fornecedor);

        EstoqueModel estoque = estoqueRepository.findById(dto.getEstoqueId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        ordemDeCompra.setEstoque(estoque);

        UsuarioModel usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        ordemDeCompra.setUsuario(usuario);



        // Salva a ordem de compra
        OrdemDeCompraModel ordemSalva = ordemDeCompraRepository.save(ordemDeCompra);

        // Atualiza a quantidade no estoque
        Integer novaQuantidade = (estoque.getQuantidadeAtual() != null ? estoque.getQuantidadeAtual() : 0)
                + ordemSalva.getQuantidade();
        estoque.setQuantidadeAtual(novaQuantidade);
        estoque.setUltimaMovimentacao(LocalDateTime.now());
        estoqueRepository.save(estoque);

        return ordemSalva;
    }

    public List<ListagemOrdemDeCompra> getAll() {
        return ordemDeCompraRepository.findAll()
                .stream()
                .map(OrdemDeCompraMapper::toListagemDto)
                .collect(Collectors.toList());
    }
}

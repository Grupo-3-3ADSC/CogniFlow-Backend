package sptech.school.CRUD.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.Model.OrdemDeCompraModel;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.Repository.FornecedorRepository;
import sptech.school.CRUD.Repository.OrdemDeCompraRepository;
import sptech.school.CRUD.Repository.UsuarioRepository;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.exception.RecursoNaoEncontradoException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemDeCompraServiceTest {


@Mock
private OrdemDeCompraRepository ordemDeCompraRepository;

@Mock
private EstoqueRepository estoqueRepository;

@Mock
private UsuarioRepository usuarioRepository;

@Mock
private FornecedorRepository fornecedorRepository;

@InjectMocks
private OrdemDeCompraService ordemDeCompraService;

private OrdemDeCompraCadastroDto dto;
private FornecedorModel fornecedor;
private EstoqueModel estoque;
private UsuarioModel usuario;
private OrdemDeCompraModel ordemDeCompra;

@BeforeEach
void setUp() {
    // Configuração dos dados de teste
    dto = new OrdemDeCompraCadastroDto();
    dto.setPrazoEntrega(String.valueOf(LocalDate.now().plusDays(30)));
    dto.setIe("123456789");
    dto.setCondPagamento("30 dias");
    dto.setValorKg(15.50);
    dto.setRastreabilidade("TRACE123");
    dto.setValorPeca(25.00);
    dto.setDescricaoMaterial("Material de teste");
    dto.setValorUnitario(10.00);
    dto.setQuantidade(100);
    dto.setIpi(5.0);
    dto.setFornecedorId(1);
    dto.setEstoqueId(1);
    dto.setUsuarioId(1);

    fornecedor = new FornecedorModel();
    fornecedor.setId(1);
    fornecedor.setNome("Fornecedor Teste");

    estoque = new EstoqueModel();
    estoque.setId(1);
    estoque.setQuantidadeAtual(50);

    usuario = new UsuarioModel();
    usuario.setId(1);
    usuario.setNome("Usuario Teste");

    ordemDeCompra = new OrdemDeCompraModel();
    ordemDeCompra.setId(1);
    ordemDeCompra.setQuantidade(100);
    ordemDeCompra.setEstoque(estoque);
}

@Test
@DisplayName("Cadastro de ordem de compra - Sucesso")
void testCadastroOrdemDeCompraComSucesso() {


    // Arrange
    when(fornecedorRepository.findById(1)).thenReturn(Optional.of(fornecedor));
    when(estoqueRepository.findById(1)).thenReturn(Optional.of(estoque));
    when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
    when(ordemDeCompraRepository.save(any(OrdemDeCompraModel.class))).thenReturn(ordemDeCompra);

    // Act
    OrdemDeCompraModel resultado = ordemDeCompraService.cadastroOrdemDeCompra(dto);

    // Assert
    assertNotNull(resultado);
    assertEquals(1, resultado.getId());
    assertEquals(100, resultado.getQuantidade());
    
    // Verifica se o estoque foi atualizado (50 + 100 = 150)
    verify(estoqueRepository).save(argThat(estoqueArg -> {
        return estoqueArg.getQuantidadeAtual() == 150 && 
               estoqueArg.getUltimaMovimentacao() != null;
    }));
    
    // Verifica se todas as entidades foram buscadas
    verify(fornecedorRepository).findById(1);
    verify(estoqueRepository).findById(1);
    verify(usuarioRepository).findById(1);
    verify(ordemDeCompraRepository).save(any(OrdemDeCompraModel.class));
}

@Test
@DisplayName("Cadastro de ordem de compra - Fornecedor não encontrado")
void testCadastroOrdemDeCompraFornecedorNaoEncontrado() {

    // Arrange
    when(fornecedorRepository.findById(1)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(RecursoNaoEncontradoException.class,
        () -> ordemDeCompraService.cadastroOrdemDeCompra(dto));

    
    // Verifica que o save não foi chamado devido ao erro
    verify(ordemDeCompraRepository, never()).save(any(OrdemDeCompraModel.class));
    verify(estoqueRepository, never()).save(any(EstoqueModel.class));
}

@Test
@DisplayName("Cadastro de ordem de compra - Estoque não encontrado")
void testCadastroOrdemDeCompraEstoqueNaoEncontrado() {

    // Arrange
    when(fornecedorRepository.findById(1)).thenReturn(Optional.of(fornecedor));
    when(estoqueRepository.findById(1)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(RecursoNaoEncontradoException.class,
            () -> ordemDeCompraService.cadastroOrdemDeCompra(dto));
    

    
    // Verifica que o save não foi chamado devido ao erro
    verify(ordemDeCompraRepository, never()).save(any(OrdemDeCompraModel.class));
}

@Test
@DisplayName("Cadastro de ordem de compra - Usuário não encontrado")
void testCadastroOrdemDeCompraUsuarioNaoEncontrado() {

    // Arrange
    when(fornecedorRepository.findById(1)).thenReturn(Optional.of(fornecedor));
    when(estoqueRepository.findById(1)).thenReturn(Optional.of(estoque));
    when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(RecursoNaoEncontradoException.class,
            () -> ordemDeCompraService.cadastroOrdemDeCompra(dto));
    
    // Verifica que o save não foi chamado devido ao erro
    verify(ordemDeCompraRepository, never()).save(any(OrdemDeCompraModel.class));
}

@Test
@DisplayName("Cadastro de ordem de compra - Atualização de estoque com quantidade inicial nula")
void testCadastroOrdemDeCompraComEstoqueQuantidadeNula() {

    // Arrange
    EstoqueModel estoqueComQuantidadeNula = new EstoqueModel();
    estoqueComQuantidadeNula.setId(1);
    estoqueComQuantidadeNula.setQuantidadeAtual(null); // Quantidade nula

    OrdemDeCompraModel ordemComEstoqueNulo = new OrdemDeCompraModel();
    ordemComEstoqueNulo.setId(1);
    ordemComEstoqueNulo.setQuantidade(75);
    ordemComEstoqueNulo.setEstoque(estoqueComQuantidadeNula);

    when(fornecedorRepository.findById(1)).thenReturn(Optional.of(fornecedor));
    when(estoqueRepository.findById(1)).thenReturn(Optional.of(estoqueComQuantidadeNula));
    when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
    when(ordemDeCompraRepository.save(any(OrdemDeCompraModel.class))).thenReturn(ordemComEstoqueNulo);

    // Act
    OrdemDeCompraModel resultado = ordemDeCompraService.cadastroOrdemDeCompra(dto);

    // Assert
    assertNotNull(resultado);
    
    // Verifica se o estoque foi atualizado corretamente (null tratado como 0 + 75 = 75)
    verify(estoqueRepository).save(argThat(estoqueArg -> {
        return estoqueArg.getQuantidadeAtual() == 75 && 
               estoqueArg.getUltimaMovimentacao() != null;
    }));
    
    verify(ordemDeCompraRepository).save(any(OrdemDeCompraModel.class));
}


}
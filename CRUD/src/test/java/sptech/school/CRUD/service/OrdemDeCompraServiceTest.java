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

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemDeCompraServiceTest {

```
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
    dto.setPrazoEntrega(LocalDate.now().plusDays(30));
    dto.setIe("123456789");
    dto.setCondPagamento("30 dias");
    dto.setValorKg(15.50);
    dto.setRastreabilidade("TRACE123");
    dto.setValorPeca(25.00);
    dto.setDescricaoMaterial("Material de teste");
    dto.setValorUnitario(10.00);
    dto.setQuantidade(100);
    dto.setIpi(5.0);
    dto.setFornecedorId(1L);
    dto.setEstoqueId(1L);
    dto.setUsuarioId(1L);

    fornecedor = new FornecedorModel();
    fornecedor.setId(1L);
    fornecedor.setNome("Fornecedor Teste");

    estoque = new EstoqueModel();
    estoque.setId(1L);
    estoque.setQuantidadeAtual(50);

    usuario = new UsuarioModel();
    usuario.setId(1L);
    usuario.setNome("Usuario Teste");

    ordemDeCompra = new OrdemDeCompraModel();
    ordemDeCompra.setId(1L);
    ordemDeCompra.setQuantidade(100);
    ordemDeCompra.setEstoque(estoque);
}

@Test
@DisplayName("Teste 1: Cadastro de ordem de compra com sucesso - cenário completo")
void testCadastroOrdemDeCompraComSucesso() {
    // **O QUE ESTE TESTE FAZ:**
    // Verifica se uma ordem de compra é cadastrada corretamente quando todos os dados
    // estão válidos e as entidades relacionadas (fornecedor, estoque, usuário) existem.
    // Também testa se o estoque é atualizado corretamente após o cadastro.

    // Arrange
    when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
    when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
    when(ordemDeCompraRepository.save(any(OrdemDeCompraModel.class))).thenReturn(ordemDeCompra);

    // Act
    OrdemDeCompraModel resultado = ordemDeCompraService.cadastroOrdemDeCompra(dto);

    // Assert
    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals(100, resultado.getQuantidade());
    
    // Verifica se o estoque foi atualizado (50 + 100 = 150)
    verify(estoqueRepository).save(argThat(estoqueArg -> {
        return estoqueArg.getQuantidadeAtual() == 150 && 
               estoqueArg.getUltimaMovimentacao() != null;
    }));
    
    // Verifica se todas as entidades foram buscadas
    verify(fornecedorRepository).findById(1L);
    verify(estoqueRepository).findById(1L);
    verify(usuarioRepository).findById(1L);
    verify(ordemDeCompraRepository).save(any(OrdemDeCompraModel.class));
}

@Test
@DisplayName("Teste 2: Falha ao cadastrar - fornecedor não encontrado")
void testCadastroOrdemDeCompraFornecedorNaoEncontrado() {
    // **O QUE ESTE TESTE FAZ:**
    // Verifica se o sistema lança exceção adequada quando tenta cadastrar uma ordem
    // de compra com um fornecedor que não existe no banco de dados.

    // Arrange
    when(fornecedorRepository.findById(1L)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, 
        () -> ordemDeCompraService.cadastroOrdemDeCompra(dto));
    
    assertEquals("Fornecedor não encontrado", exception.getMessage());
    
    // Verifica que o save não foi chamado devido ao erro
    verify(ordemDeCompraRepository, never()).save(any(OrdemDeCompraModel.class));
    verify(estoqueRepository, never()).save(any(EstoqueModel.class));
}

@Test
@DisplayName("Teste 3: Falha ao cadastrar - estoque não encontrado")
void testCadastroOrdemDeCompraEstoqueNaoEncontrado() {
    // **O QUE ESTE TESTE FAZ:**
    // Verifica se o sistema lança exceção adequada quando tenta cadastrar uma ordem
    // de compra com um estoque que não existe no banco de dados.

    // Arrange
    when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
    when(estoqueRepository.findById(1L)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, 
        () -> ordemDeCompraService.cadastroOrdemDeCompra(dto));
    
    assertEquals("Estoque não encontrado", exception.getMessage());
    
    // Verifica que o save não foi chamado devido ao erro
    verify(ordemDeCompraRepository, never()).save(any(OrdemDeCompraModel.class));
}

@Test
@DisplayName("Teste 4: Falha ao cadastrar - usuário não encontrado")
void testCadastroOrdemDeCompraUsuarioNaoEncontrado() {
    // **O QUE ESTE TESTE FAZ:**
    // Verifica se o sistema lança exceção adequada quando tenta cadastrar uma ordem
    // de compra com um usuário que não existe no banco de dados.

    // Arrange
    when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
    when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
    when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, 
        () -> ordemDeCompraService.cadastroOrdemDeCompra(dto));
    
    assertEquals("Usuário não encontrado", exception.getMessage());
    
    // Verifica que o save não foi chamado devido ao erro
    verify(ordemDeCompraRepository, never()).save(any(OrdemDeCompraModel.class));
}

@Test
@DisplayName("Teste 5: Atualização de estoque com quantidade inicial nula")
void testCadastroOrdemDeCompraComEstoqueQuantidadeNula() {
    // **O QUE ESTE TESTE FAZ:**
    // Verifica se o sistema trata corretamente o caso onde o estoque tem quantidade
    // atual nula (null), tratando como 0 e somando corretamente a nova quantidade.
    // Este é um caso edge importante para evitar NullPointerException.

    // Arrange
    EstoqueModel estoqueComQuantidadeNula = new EstoqueModel();
    estoqueComQuantidadeNula.setId(1L);
    estoqueComQuantidadeNula.setQuantidadeAtual(null); // Quantidade nula

    OrdemDeCompraModel ordemComEstoqueNulo = new OrdemDeCompraModel();
    ordemComEstoqueNulo.setId(1L);
    ordemComEstoqueNulo.setQuantidade(75);
    ordemComEstoqueNulo.setEstoque(estoqueComQuantidadeNula);

    when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
    when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoqueComQuantidadeNula));
    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
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
```

}
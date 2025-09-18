package sptech.school.CRUD.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import sptech.school.CRUD.application.service.EstoqueService;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.repository.EstoqueRepository;
import sptech.school.CRUD.dto.Estoque.AtualizarEstoqueDto;
import sptech.school.CRUD.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.dto.Estoque.RetirarEstoqueDto;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    @DisplayName("Entrada de Estoque - Material não encontrado")
    void testAtualizarEstoqueMaterialInexistente() {
        // Arrange
        AtualizarEstoqueDto dto = new AtualizarEstoqueDto("Cabo", 50);
        when(estoqueRepository.findByTipoMaterial("Cabo")).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RecursoNaoEncontradoException.class, () -> {
            estoqueService.atualizarEstoque(dto);
        });

        verify(estoqueRepository, never()).save(any());
    }

    @Test
    @DisplayName("Entrada de Estoque - Quantidade máxima ultrapassada")
    void testAtualizarEstoqueExcedeMaximoDoEstoque() {
        // Arrange
        EstoqueModel estoque = new EstoqueModel();
        estoque.setTipoMaterial("SAE 1020");
        estoque.setQuantidadeAtual(9990);
        estoque.setQuantidadeMaxima(10000);
        estoque.setQuantidadeMinima(10);

        when(estoqueRepository.findByTipoMaterial("SAE 1020")).thenReturn(Optional.of(estoque));

        AtualizarEstoqueDto dto = new AtualizarEstoqueDto("SAE 1020", 50);

        // Act + Assert
        assertThrows(RequisicaoInvalidaException.class, () -> estoqueService.atualizarEstoque(dto));
    }

    @Test
    @DisplayName("Entrada de estoque - Quantidade válida")
    void testEntradaQuantidadeValida() {
        // Arrange
        EstoqueModel estoqueModel = new EstoqueModel();
        estoqueModel.setTipoMaterial("SAE 1020");
        estoqueModel.setQuantidadeAtual(200);
        estoqueModel.setQuantidadeMinima(100);
        estoqueModel.setQuantidadeMaxima(10000);

        AtualizarEstoqueDto dto = new AtualizarEstoqueDto("SAE 1020", 100);

        when(estoqueRepository.findByTipoMaterial("SAE 1020"))
                .thenReturn(Optional.of(estoqueModel));
        when(estoqueRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        EstoqueListagemDto resultado = estoqueService.atualizarEstoque(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("SAE 1020", resultado.getTipoMaterial());
        assertEquals(300, resultado.getQuantidadeAtual());
    }

    @Test
    @DisplayName("Entrada de estoque - Entrada mínima válida")
    void testEntradaMinimaValida() {
        EstoqueModel estoque = new EstoqueModel();
        estoque.setTipoMaterial("SAE 1045");
        estoque.setQuantidadeAtual(9999);
        estoque.setQuantidadeMaxima(10000);
        estoque.setQuantidadeMinima(100);

        AtualizarEstoqueDto dto = new AtualizarEstoqueDto("SAE 1045", 1);

        when(estoqueRepository.findByTipoMaterial("SAE 1045")).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        EstoqueListagemDto resultado = estoqueService.atualizarEstoque(dto);

        assertNotNull(resultado);
        assertEquals(10000, resultado.getQuantidadeAtual());
    }


    @Test
    @DisplayName("Entrada de estoque - Exatamente até o limite máximo permitido")
    void testEntradaAteLimiteMaximo() {
        EstoqueModel estoque = new EstoqueModel();
        estoque.setTipoMaterial("HARDOX 450");
        estoque.setQuantidadeAtual(9500);
        estoque.setQuantidadeMaxima(10000);
        estoque.setQuantidadeMinima(100);

        AtualizarEstoqueDto dto = new AtualizarEstoqueDto("HARDOX 450", 500);

        when(estoqueRepository.findByTipoMaterial("HARDOX 450")).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        EstoqueListagemDto resultado = estoqueService.atualizarEstoque(dto);

        assertNotNull(resultado);
        assertEquals(10000, resultado.getQuantidadeAtual());
    }


    @Test
    @DisplayName("Entrada de estoque - Nome do material case insensitive (SAE 1045)")
    void testEntradaCaseInsensitive() {
        // Arrange
        EstoqueModel estoque = new EstoqueModel();
        estoque.setTipoMaterial("SAE 1045"); // nome armazenado
        estoque.setQuantidadeAtual(400);
        estoque.setQuantidadeMaxima(1000);
        estoque.setQuantidadeMinima(100);

        AtualizarEstoqueDto dto = new AtualizarEstoqueDto("sae 1045", 100); // nome com letras minúsculas

        when(estoqueRepository.findByTipoMaterial("sae 1045")).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // Act
        EstoqueListagemDto resultado = estoqueService.atualizarEstoque(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(500, resultado.getQuantidadeAtual());
        assertEquals("SAE 1045", resultado.getTipoMaterial()); // opcional, para garantir que o nome original seja mantido
    }


    @Test
    @DisplayName("Retirada de material interna - Sucesso")
    void testRetirarEstoqueSucessoInterna() {
        // Arrange
        EstoqueModel estoque = new EstoqueModel();
        estoque.setTipoMaterial("SAE 1045");
        estoque.setQuantidadeAtual(100);
        estoque.setInterno(0);
        estoque.setExterno(0);

        when(estoqueRepository.findByTipoMaterial("SAE 1045")).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        RetirarEstoqueDto dto = new RetirarEstoqueDto("SAE 1045", 20, "Interna");

        // Act
        EstoqueListagemDto resultado = estoqueService.retirarEstoque(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(80, estoque.getQuantidadeAtual());
        assertEquals(1, estoque.getInterno());
    }

    @Test
    @DisplayName("Retirada de estoque - Quantidade Insuficiente")
    void testRetirarEstoqueQuantidadeInsuficiente() {
        // Arrange
        EstoqueModel estoque = new EstoqueModel();
        estoque.setTipoMaterial("SAE 1020");
        estoque.setQuantidadeAtual(10);

        when(estoqueRepository.findByTipoMaterial("SAE 1020")).thenReturn(Optional.of(estoque));

        RetirarEstoqueDto dto = new RetirarEstoqueDto("SAE 1020", 20, "Externa");

        // Act & Assert
        RuntimeException e = assertThrows(RuntimeException.class, () -> estoqueService.retirarEstoque(dto));
        assertTrue(e.getMessage().contains("insuficiente"));
    }



    @Test
    @DisplayName("Retirada de estoque - Tipo de material nulo")
    void testRetirarEstoqueTipoMaterialVazio() {
        RetirarEstoqueDto dto = new RetirarEstoqueDto("", 10, "Interna");

        // Act & Assert
        assertThrows(RequisicaoInvalidaException.class, () -> estoqueService.retirarEstoque(dto));
    }

    @Test
    @DisplayName("Retirada de estoque - Quantidade Atual nula")
    void testRetirarEstoqueQuantidadeNegativa() {
        RetirarEstoqueDto dto = new RetirarEstoqueDto("SAE 1020", -5, "Externa");

        // Act & Assert
        assertThrows(RequisicaoInvalidaException.class, () -> estoqueService.retirarEstoque(dto));
    }
}

package sptech.school.CRUD.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.dto.Estoque.AtualizarEstoqueDto;
import sptech.school.CRUD.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.dto.Estoque.RetirarEstoqueDto;
import sptech.school.CRUD.exception.BadRequestException;
import sptech.school.CRUD.dto.Estoque.EstoqueMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.CRUD.exception.EntidadeNaoEncontrado;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    @DisplayName("Ao atualizar o estoque de material inexistente")
    void testAtualizarEstoqueMaterialInexistente() {
        // Arrange
        AtualizarEstoqueDto dto = new AtualizarEstoqueDto("Cabo", 50);
        when(estoqueRepository.findByTipoMaterial("Cabo")).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(EntidadeNaoEncontrado.class, () -> {
            estoqueService.atualizarEstoque(dto);
        });

        verify(estoqueRepository, never()).save(any());
    }

    @Test
    @DisplayName("ao tentar adicionar uma quantidade que ultrapassa o máximo permitido no estoque")
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
        assertThrows(BadRequestException.class, () -> estoqueService.atualizarEstoque(dto));
    }

    @Test
    @DisplayName("Ao retira do estoque o tipo material na tranferencia interna")
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
    @DisplayName("quando a quantidade solicitada é maior do que a quantidade atual no estoque.")
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
    @DisplayName("Quando o tipo de material estiver vazio")
    void testRetirarEstoqueTipoMaterialVazio() {
        RetirarEstoqueDto dto = new RetirarEstoqueDto("", 10, "Interna");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> estoqueService.retirarEstoque(dto));
    }

    @Test
    @DisplayName("Quando a quantidade Atual do estoque de material estiver vazio")
    void testRetirarEstoqueQuantidadeNegativa() {
        RetirarEstoqueDto dto = new RetirarEstoqueDto("SAE 1020", -5, "Externa");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> estoqueService.retirarEstoque(dto));
    }
}

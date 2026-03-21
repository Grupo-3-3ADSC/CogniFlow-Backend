package sptech.school.CRUD.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sptech.school.CRUD.application.service.cargo.CargoService;
import sptech.school.CRUD.domain.entity.CargoModel;
import sptech.school.CRUD.infrastructure.persistence.cargo.CargoRepository;
import sptech.school.CRUD.interfaces.dto.Cargo.CargoCadastraDto;
import sptech.school.CRUD.interfaces.dto.Cargo.CargoListagemDto;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;

import java.util.List;


class CargoServiceTest {

    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Listagem de cargos - Sucesso")
    void testeGetAllCargos() {
        // Arrange
        CargoModel cargo1 = new CargoModel();
        cargo1.setNome("Funcionário");

        CargoModel cargo2 = new CargoModel();
        cargo2.setNome("Gestor");

        List<CargoModel> listaCargos = List.of(cargo1, cargo2);

        when(cargoRepository.findAll()).thenReturn(listaCargos);

        // Act
        List<CargoListagemDto> resultado = cargoService.getAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Funcionário", resultado.get(0).getNome());
        assertEquals("Gestor", resultado.get(1).getNome());
        verify(cargoRepository).findAll();
    }
}

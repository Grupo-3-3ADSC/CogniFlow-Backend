package sptech.school.CRUD.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Repository.CargoRepository;
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
    @DisplayName("Cadastro de cargo - sucesso")
    void testePostCargoSucesso() {
        // Arrange
        CargoModel cargo = new CargoModel();
        cargo.setNome("Desenvolvedor");

        when(cargoRepository.save(any(CargoModel.class))).thenReturn(cargo);

        // Act
        CargoModel resultado = cargoService.post(cargo);

        // Assert
        assertNotNull(resultado);
        assertEquals("Desenvolvedor", resultado.getNome());
        verify(cargoRepository).save(cargo);
    }

    @Test
    @DisplayName("Cadastro de cargo - falha (cargo nulo)")
    void testePostCargoNulo() {
        // Act
        CargoModel resultado = cargoService.post(null);

        // Assert
        assertNull(resultado);
        verify(cargoRepository, never()).save(any());
    }




    @Test
    @DisplayName("Listagem de cargos - sucesso")
    void testeGetAllCargos() {
        // Arrange
        CargoModel cargo1 = new CargoModel();
        cargo1.setNome("Desenvolvedor");

        CargoModel cargo2 = new CargoModel();
        cargo2.setNome("Analista de Sistemas");

        List<CargoModel> listaCargos = List.of(cargo1, cargo2);

        when(cargoRepository.findAll()).thenReturn(listaCargos);

        // Act
        List<CargoModel> resultado = cargoService.getAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Desenvolvedor", resultado.get(0).getNome());
        assertEquals("Analista de Sistemas", resultado.get(1).getNome());
        verify(cargoRepository).findAll();
    }
}

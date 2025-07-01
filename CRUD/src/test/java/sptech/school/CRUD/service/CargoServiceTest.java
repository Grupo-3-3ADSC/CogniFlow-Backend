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
import sptech.school.CRUD.dto.Cargo.CargoCadastraDto;
import sptech.school.CRUD.dto.Cargo.CargoListagemDto;

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
        CargoCadastraDto dto = new CargoCadastraDto();
        dto.setNome("Desenvolvedor");

        CargoModel salvo = new CargoModel();
        salvo.setNome("Desenvolvedor");

        when(cargoRepository.save(any(CargoModel.class))).thenReturn(salvo);

        // Act
        CargoListagemDto resultado = cargoService.post(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Desenvolvedor", resultado.getNome());
        verify(cargoRepository).save(any(CargoModel.class));
    }

    @Test
    @DisplayName("Cadastro de cargo - falha (cargo nulo)")
    void testePostCargoNulo() {
        // Act
        CargoListagemDto resultado = cargoService.post(null);

        // Assert
        assertNull(resultado);
        verify(cargoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Listagem de cargos - sucesso")
    void testeGetAllCargos() {
        // Arrange
        CargoModel cargo1 = new CargoModel();
        cargo1.setNome("Funcionário");

        CargoModel cargo2 = new CargoModel();
        cargo2.setNome("Gestor");

        List<CargoModel> listaCargos = List.of(cargo1, cargo2);

        when(cargoRepository.findAll()).thenReturn(listaCargos);

        // Act
        List<CargoModel> resultado = cargoService.getAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Funcionário", resultado.get(0).getNome());
        assertEquals("Gestor", resultado.get(1).getNome());
        verify(cargoRepository).findAll();
    }
}

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
import sptech.school.CRUD.infrastructure.persistence.CargoRepository;
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
    @DisplayName("Cadastro de cargo - Sucesso")
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
    @DisplayName("Cadastro de cargo - Falha (cargo nulo)")
    void testePostCargoNulo() {
        // Act & Assert
        RequisicaoInvalidaException exception = assertThrows(RequisicaoInvalidaException.class, () -> {
            cargoService.post(null);
        });

        assertEquals("O nome do cargo é obrigatório.", exception.getMessage());
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

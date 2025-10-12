package sptech.school.CRUD.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import sptech.school.CRUD.application.service.fornecedor.CadastroFornecedorService;
import sptech.school.CRUD.application.service.ViaCepService;
import sptech.school.CRUD.domain.entity.ContatoModel;
import sptech.school.CRUD.domain.entity.EnderecoModel;
import sptech.school.CRUD.domain.entity.FornecedorModel;
import sptech.school.CRUD.infrastructure.persistence.contato.ContatoRepository;
import sptech.school.CRUD.infrastructure.persistence.endereco.EnderecoRepository;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorCadastroDto;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ViaCepService viaCepService;

    @InjectMocks
    private CadastroFornecedorService cadastroService;

    @Test
    @DisplayName("Cadastro de Fornecedor - Sucesso")
    void testCadastroFornecedorSucesso() {
        // Arrange
        FornecedorCadastroDto dto = new FornecedorCadastroDto();
        dto.setCnpj("12345678000199");
        dto.setRazaoSocial("Fornecedor X");
        dto.setNomeFantasia("Fornecedor X");
        dto.setIe("199999888");
        dto.setCep("12345-000");
        dto.setEndereco("Rua XPTO");
        dto.setNumero(123);
        dto.setTelefone("11999999999");
        dto.setEmail("email@fornecedor.com");

        when(fornecedorRepository.findByCnpj("12345678000199")).thenReturn(Optional.empty());
        when(contatoRepository.existsByEmail("email@fornecedor.com")).thenReturn(false);

        FornecedorModel salvo = new FornecedorModel();
        salvo.setId(1);
        when(fornecedorRepository.save(any())).thenReturn(salvo);
        when(enderecoRepository.save(any())).thenReturn(new EnderecoModel());
        when(contatoRepository.save(any())).thenReturn(new ContatoModel());

        // Act
        FornecedorCadastroDto resultado = cadastroService.cadastroFornecedor(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("12345678000199", resultado.getCnpj());
        assertEquals("email@fornecedor.com", resultado.getEmail());

        verify(fornecedorRepository).save(any());
        verify(enderecoRepository).save(any());
        verify(contatoRepository).save(any());
    }

    @Test
    @DisplayName("Cadastro de Fornecedor - CNPJ nulo")
    void testCadastroFornecedorCnpjNulo() {
        // Arrange
        FornecedorCadastroDto dto = new FornecedorCadastroDto();
        dto.setCnpj(null);

        // Act & Assert
        assertThrows(RequisicaoInvalidaException.class, () -> cadastroService.cadastroFornecedor(dto));
    }

    @Test
    @DisplayName("Cadastro de Fornecedor - CNPJ inválido")
    void testCadastroFornecedorCnpjInvalido() {
        // Arrange
        FornecedorCadastroDto dto = new FornecedorCadastroDto();
        dto.setCnpj("123");

        // Act & Assert
        assertThrows(RequisicaoInvalidaException.class, () -> cadastroService.cadastroFornecedor(dto));
    }

    @Test
    @DisplayName("Cadastro de Fornecedor - Cnpj duplicado")
    void testCadastroFornecedorCnpjDuplicado() {
        // Arrange
        FornecedorCadastroDto dto = new FornecedorCadastroDto();
        dto.setCnpj("12345678000199");

        when(fornecedorRepository.findByCnpj("12345678000199"))
                .thenReturn(Optional.of(new FornecedorModel()));

        // Act & Assert
        assertThrows(RequisicaoConflitanteException.class, () -> cadastroService.cadastroFornecedor(dto));
    }
}

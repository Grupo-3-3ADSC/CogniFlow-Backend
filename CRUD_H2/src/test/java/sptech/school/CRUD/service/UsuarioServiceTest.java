package sptech.school.CRUD.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.CRUD.Repository.CargoRepository;
import sptech.school.CRUD.Repository.UsuarioRepository;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.exception.EntidadeNaoEncontrado;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

class UsuarioServiceTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("Cadastro de funcionário comum")
    void testeCadastroUsuario() {
        //A - Arrange (configuração)
        // Simular o cargo retornado do banco
        CargoModel cargo = new CargoModel();
        cargo.setId(1);
        cargo.setNome("Funcionário");

        // Criar o UsuarioModel diretamente (sem usar DTO)
        UsuarioModel usuario = UsuarioModel.builder()
                .nome("Bianca")
                .email("bianca@email.com")
                .password("1234")
                .cargo(cargo)
                .build();

        // Action /Assert
        // Simular findById do cargo
        when(cargoRepository.findById(any())).thenReturn(Optional.of(cargo));

        // Simular o usuário salvo no banco
        UsuarioModel usuarioSalvo = new UsuarioModel();
        usuarioSalvo.setId(1);
        usuarioSalvo.setNome("Bianca");
        usuarioSalvo.setEmail("bianca@email.com");
        usuarioSalvo.setPassword("senhaCriptografada");
        usuarioSalvo.setCargo(cargo);
        usuarioSalvo.setAtivo(true);
        usuarioSalvo.setCreatedAt(LocalDateTime.now());

        // Simular o save
        when(usuarioRepository.save(any())).thenReturn(usuarioSalvo);

        // Executar o método
        UsuarioModel resultado = usuarioService.cadastrarUsuarioComum(usuario);

        //Assert
        // Verificações
        assertNotNull(resultado);
        assertEquals("Bianca", resultado.getNome());
        verify(usuarioRepository).save(any());

        // Confirmar que passwordEncoder está sendo realmente chamado
        verify(passwordEncoder, times(1)).encode(anyString());

    }

    @Test
    @DisplayName("Cadastro de usuário gestor")
    void testeCadastroUsuarioGestor() {
        // Arrange
        CargoModel cargo = new CargoModel();
        cargo.setId(2);
        cargo.setNome("gestor");

        UsuarioModel usuario = UsuarioModel.builder()
                .nome("Miguel")
                .email("miguel@email.com")
                .password("abcd")
                .cargo(cargo)
                .build();

        when(cargoRepository.findByNome("gestor")).thenReturn(cargo);

        UsuarioModel usuarioSalvo = new UsuarioModel();
        usuarioSalvo.setId(2);
        usuarioSalvo.setNome("Carlos");
        usuarioSalvo.setEmail("carlos@email.com");
        usuarioSalvo.setPassword("senhaCriptografada");
        usuarioSalvo.setCargo(cargo);
        usuarioSalvo.setAtivo(true);
        usuarioSalvo.setCreatedAt(LocalDateTime.now());

        when(usuarioRepository.save(any())).thenReturn(usuarioSalvo);

        // Act
        UsuarioModel resultado = usuarioService.cadastrarUsuarioGestor(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNome());
        verify(usuarioRepository).save(any());
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    @DisplayName("Cadastro de usuário gestor sem cargo válido")
    void testeCadastroUsuarioGestorSemCargo() {
        // Arrange
        UsuarioModel usuario = UsuarioModel.builder()
                .nome("Gabriel")
                .email("gabriel@email.com")
                .password("abcd")
                .build();

        when(cargoRepository.findByNome("gestor")).thenReturn(null);

        // Act
        UsuarioModel resultado = usuarioService.cadastrarUsuarioGestor(usuario);

        // Assert
        assertNull(resultado);
    }

    @Test
    @DisplayName("Buscar usuário por ID - sucesso")
    void testeGetByIdSucesso() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1);
        usuario.setNome("Isaias");
        usuario.setEmail("isaias@email.com");
        usuario.setPassword("senhaCriptografada");
        usuario.setAtivo(true);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        // Act
        UsuarioModel resultado = usuarioService.getById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Isaias", resultado.getNome());
        verify(usuarioRepository).findById(1);
    }

    @Test
    @DisplayName("Buscar usuário por ID - usuário não encontrado")
    void testeGetByIdErro() {
        // Arrange
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntidadeNaoEncontrado.class, () -> usuarioService.getById(99));

        assertEquals("Usuario de id 99 não encontrado", exception.getMessage());
        verify(usuarioRepository).findById(99);
    }

    @Test
    @DisplayName("Atualizar usuário - sucesso")
    void testePutSucesso() {
        // Arrange
        UsuarioModel usuarioExistente = new UsuarioModel();
        usuarioExistente.setId(1);
        usuarioExistente.setNome("Matheus");
        usuarioExistente.setEmail("matheus@email.com");
        usuarioExistente.setPassword("senhaAntiga");
        usuarioExistente.setAtivo(true);

        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setNome("Matheus Souza");
        usuarioAtualizado.setEmail("matheussouza@email.com");
        usuarioAtualizado.setPassword("senhaNova");

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.save(any())).thenReturn(usuarioAtualizado);

        // Act
        UsuarioModel resultado = usuarioService.put(usuarioAtualizado, 1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Matheus Souza", resultado.getNome());
        assertEquals("matheussouza@email.com", resultado.getEmail());
        verify(usuarioRepository).save(any());
    }

    @Test
    @DisplayName("Atualizar usuário - usuário não encontrado")
    void testePutUsuarioNaoEncontrado() {
        // Arrange
        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setNome("Guilherme Souza");
        usuarioAtualizado.setEmail("guilhermesouza@email.com");
        usuarioAtualizado.setPassword("senhaNova");

        when(usuarioRepository.existsById(anyInt())).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntidadeNaoEncontrado.class, () -> usuarioService.put(usuarioAtualizado, 99));

        assertEquals("Usuario de id 99 não encontrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Excluir usuário - sucesso")
    void testeDeleteSucesso() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1);
        usuario.setNome("Bruna");
        usuario.setEmail("bruna@email.com");
        usuario.setPassword("senhaCriptografada");
        usuario.setAtivo(true);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        // Act
        Optional<UsuarioModel> resultado = usuarioService.delete(1);

        // Assert
        assertTrue(resultado.isPresent());
        assertFalse(resultado.get().getAtivo());
        verify(usuarioRepository).save(any());
    }

    @Test
    @DisplayName("Excluir usuário - usuário não encontrado")
    void testeDeleteUsuarioNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        Optional<UsuarioModel> resultado = usuarioService.delete(99);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository, never()).save(any());
    }


}


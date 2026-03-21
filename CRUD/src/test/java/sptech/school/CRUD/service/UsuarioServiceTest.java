package sptech.school.CRUD.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.CRUD.application.service.usuario.AtualizarUsuarioService;
import sptech.school.CRUD.application.service.usuario.CadastroUsuarioService;
import sptech.school.CRUD.application.service.usuario.UsuarioService;
import sptech.school.CRUD.infrastructure.persistence.cargo.CargoRepository;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;
import sptech.school.CRUD.domain.entity.CargoModel;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.interfaces.dto.Usuario.UsuarioAtivoDto;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.interfaces.dto.Usuario.UsuarioDeleteDto;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @InjectMocks
    private CadastroUsuarioService cadastroService;

    @InjectMocks
    private AtualizarUsuarioService atualizarService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Cadastro de funcionário comum - Sucesso")
    void testeCadastroUsuario() {
        // Arrange
        CargoModel cargo = new CargoModel();
        cargo.setId(1);
        cargo.setNome("Funcionário");

        UsuarioModel usuario = UsuarioModel.builder()
                .nome("Bianca")
                .email("bianca@email.com")
                .password("1234567")
                .build();

        // Mock do cargo
        when(cargoRepository.findById(1)).thenReturn(Optional.of(cargo));

        // Mock do passwordEncoder
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada");

        // Mock do save
        when(usuarioRepository.save(any(UsuarioModel.class))).thenAnswer(invocation -> {
            UsuarioModel u = invocation.getArgument(0);
            u.setId(1);
            u.setAtivo(true);
            u.setCreatedAt(LocalDateTime.now());
            return u;
        });

        // Act
        UsuarioModel resultado = cadastroService.cadastrarUsuario(usuario, 1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Bianca", resultado.getNome());
        assertEquals("bianca@email.com", resultado.getEmail());
        assertEquals(cargo, resultado.getCargo());
        verify(usuarioRepository).save(any(UsuarioModel.class));
        verify(passwordEncoder).encode("1234567");
    }

    @Test
    @DisplayName("Cadastro de usuário - E-mail já existente")
    void testeCadastroUsuarioEmailDuplicado() {
        // Arrange
        String email = "usuario@email.com";

        UsuarioModel novoUsuario = new UsuarioModel();
        novoUsuario.setEmail(email);
        novoUsuario.setNome("Usuário Novo");
        novoUsuario.setPassword("senhaValida123");

        when(usuarioRepository.existsByEmail(email)).thenReturn(true);

        // Act & Assert
        RequisicaoConflitanteException exception = assertThrows(
                RequisicaoConflitanteException.class,
                () -> cadastroService.cadastrarUsuario(novoUsuario, 1)
        );

        assertEquals("Email já cadastrado.", exception.getMessage());
        verify(usuarioRepository, times(1)).existsByEmail(email);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Cadastro de usuário - Senha muito curta")
    void testeCadastroUsuarioSenhaCurta() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("Usuário");
        usuario.setEmail("usuario@email.com");
        usuario.setPassword("123");

        // Act & Assert
        RequisicaoInvalidaException exception = assertThrows(
                RequisicaoInvalidaException.class,
                () -> cadastroService.cadastrarUsuario(usuario, 1)
        );

        assertEquals("Senha deve ter pelo menos 6 caracteres.", exception.getMessage());
    }

    @Test
    @DisplayName("Cadastro de usuário - Senha nula")
    void testeCadastroUsuarioSenhaNula() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("Bianca");
        usuario.setEmail("bia@email.com");
        usuario.setPassword(null);

        // Act & Assert
        RequisicaoInvalidaException exception = assertThrows(
                RequisicaoInvalidaException.class,
                () -> cadastroService.cadastrarUsuario(usuario, 1)
        );

        assertEquals("Senha não pode ser nula ou vazia.", exception.getMessage());
    }

    @Test
    @DisplayName("Cadastro de usuário - Nome muito curto")
    void testeCadastroUsuarioNomeCurto() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("Bi");
        usuario.setEmail("bi@email.com");
        usuario.setPassword("senha123");

        // Act & Assert
        RequisicaoInvalidaException exception = assertThrows(
                RequisicaoInvalidaException.class,
                () -> cadastroService.cadastrarUsuario(usuario, 1)
        );

        assertEquals("O nome deve ter pelo menos 3 caracteres.", exception.getMessage());
    }

    @Test
    @DisplayName("Cadastro de usuário - Cargo não encontrado")
    void testeCadastroUsuarioCargoNaoEncontrado() {
        // Arrange
        UsuarioModel usuario = UsuarioModel.builder()
                .nome("Gabriel")
                .email("gabriel@email.com")
                .password("abcdefg")
                .build();

        when(cargoRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> cadastroService.cadastrarUsuario(usuario, 999)
        );

        assertEquals("Cargo não encontrado com id 999", exception.getMessage());
    }

    @Test
    @DisplayName("Buscar usuário por ID - Sucesso")
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
    @DisplayName("Buscar usuário por ID - Usuário não encontrado")
    void testeGetByIdErro() {
        // Arrange
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> usuarioService.getById(99)
        );

        assertEquals("Usuario de id 99 não encontrado", exception.getMessage());
        verify(usuarioRepository).findById(99);
    }

    @Test
    @DisplayName("Buscar usuário por ID - ID inválido")
    void testeBuscarUsuarioIdInvalido() {
        // Arrange
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> usuarioService.getById(-1)
        );

        assertEquals("Usuario de id -1 não encontrado", exception.getMessage());
        verify(usuarioRepository).findById(-1);
    }

    @Test
    @DisplayName("Atualizar usuário - Sucesso")
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

        when(usuarioRepository.existsById(1)).thenReturn(true);
        //when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenAnswer(invocation -> {
            UsuarioModel saved = invocation.getArgument(0);
            return saved;
        });

        // Act
        UsuarioModel resultado = atualizarService.put(usuarioAtualizado, 1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Matheus Souza", resultado.getNome());
        assertEquals("matheussouza@email.com", resultado.getEmail());
        verify(usuarioRepository).save(any(UsuarioModel.class));
    }

    @Test
    @DisplayName("Atualizar usuário - Atualização parcial")
    void testeAtualizarUsuarioParcial() {
        // Arrange
        UsuarioModel usuarioExistente = new UsuarioModel();
        usuarioExistente.setId(1);
        usuarioExistente.setNome("Antigo Nome");
        usuarioExistente.setEmail("email@email.com");
        usuarioExistente.setPassword("senha@2025");

        when(usuarioRepository.existsById(1)).thenReturn(true);
        //when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioExistente));

        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setNome("Novo Nome");
        usuarioAtualizado.setEmail("emailnovo@email.com");

        when(usuarioRepository.save(any(UsuarioModel.class))).thenAnswer(invocation -> {
            UsuarioModel saved = invocation.getArgument(0);
            return saved;
        });

        // Act
        UsuarioModel resultado = atualizarService.put(usuarioAtualizado, 1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Novo Nome", resultado.getNome());
        assertEquals("emailnovo@email.com", resultado.getEmail());
        verify(usuarioRepository).save(any(UsuarioModel.class));
    }

    @Test
    @DisplayName("Atualizar usuário - Dados inválidos")
    void testePutDadosInvalidos() {
        // Arrange
        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setNome("");
        usuarioAtualizado.setEmail("");
        usuarioAtualizado.setPassword("senhaVali123");

        when(usuarioRepository.existsById(1)).thenReturn(true);

        // Act & Assert
        RequisicaoInvalidaException exception = assertThrows(
                RequisicaoInvalidaException.class,
                () -> atualizarService.put(usuarioAtualizado, 1)
        );

        // Verifica que a mensagem contém algo sobre nome ou email vazio
        assertTrue(
                exception.getMessage().contains("Nome") ||
                        exception.getMessage().contains("nome") ||
                        exception.getMessage().contains("Email") ||
                        exception.getMessage().contains("email") ||
                        exception.getMessage().contains("vazio")
        );
    }

    @Test
    @DisplayName("Atualizar usuário - Usuário não encontrado")
    void testePutUsuarioNaoEncontrado() {
        // Arrange
        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setNome("Guilherme Souza");
        usuarioAtualizado.setEmail("guilhermesouza@email.com");
        usuarioAtualizado.setPassword("senhaNova");

        when(usuarioRepository.existsById(anyInt())).thenReturn(false);

        // Act & Assert
        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> atualizarService.put(usuarioAtualizado, 99)
        );

        assertEquals("Usuario de id 99 não encontrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Excluir usuário - Sucesso")
    void testeDeleteSucesso() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1);
        usuario.setNome("Bruna");
        usuario.setEmail("bruna@email.com");
        usuario.setPassword("senhaCriptografada");
        usuario.setAtivo(true);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        // Act
        Optional<UsuarioDeleteDto> resultado = usuarioService.deletarUsuarios(1);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Bruna", resultado.get().getNome());
        assertEquals("bruna@email.com", resultado.get().getEmail());
        verify(usuarioRepository).delete(usuario);
    }

    @Test
    @DisplayName("Excluir usuário - Usuário não encontrado")
    void testeDeleteUsuarioNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        Optional<UsuarioDeleteDto> resultado = usuarioService.deletarUsuarios(99);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Desativar usuário - Usuário desativado com sucesso")
    void deveDesativarUsuarioComSucesso() {
        // Arrange
        Integer idUsuario = 1;
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(idUsuario);
        usuario.setAtivo(true);

        UsuarioAtivoDto dto = new UsuarioAtivoDto();
        dto.setAtivo(false);

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuario);

        // Act
        UsuarioAtivoDto response = atualizarService.desativarUsuario(idUsuario, dto);

        // Assert
        assertFalse(response.getAtivo());
        assertFalse(usuario.getAtivo());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    @DisplayName("Ativar usuário - Usuário ativado com sucesso")
    void deveAtivarUsuarioComSucesso() {
        // Arrange
        Integer idUsuario = 2;
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(idUsuario);
        usuario.setAtivo(false);

        UsuarioAtivoDto dto = new UsuarioAtivoDto();
        dto.setAtivo(true);

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuario);

        // Act
        UsuarioAtivoDto response = atualizarService.desativarUsuario(idUsuario, dto);

        // Assert
        assertTrue(response.getAtivo());
        assertTrue(usuario.getAtivo());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    @DisplayName("Desativar usuário - Deve lançar exceção quando usuário não encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        Integer id = 999;
        UsuarioAtivoDto dto = new UsuarioAtivoDto();
        dto.setAtivo(false);

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            atualizarService.desativarUsuario(id, dto);
        });
    }

    @Test
    @DisplayName("Ativar usuário - Deve lançar exceção quando usuário não encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoParaAtivar() {
        // Arrange
        Integer id = 999;
        UsuarioAtivoDto dto = new UsuarioAtivoDto();
        dto.setAtivo(true);

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            atualizarService.desativarUsuario(id, dto);
        });
    }
}
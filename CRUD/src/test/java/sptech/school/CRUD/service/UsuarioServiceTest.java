package sptech.school.CRUD.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.CRUD.application.service.UsuarioService;
import sptech.school.CRUD.domain.repository.CargoRepository;
import sptech.school.CRUD.domain.repository.UsuarioRepository;
import sptech.school.CRUD.domain.entity.CargoModel;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.dto.Usuario.UsuarioAtivoDto;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.dto.Usuario.UsuarioDeleteDto;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {


    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("Cadastro de funcionário comum - Sucesso")
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
                .password("1234567")
                .cargo(cargo)
                .build();

        // Action /Assert
        // Simular findById do cargo
        when(cargoRepository.findByNome("comum")).thenReturn(cargo);
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
    }

    @Test
    @DisplayName("Cadastro de usuário - E-mail já existente")
    void testeCadastroUsuarioEmailDuplicado() {
        // Arrange
        String email = "usuario@email.com";

        when(usuarioRepository.existsByEmail(email))
                .thenReturn(true);

        UsuarioModel novoUsuario = new UsuarioModel();
        novoUsuario.setEmail(email);
        novoUsuario.setNome("Usuário Novo");
        novoUsuario.setPassword("senhaValida123");

        // Act & Assert
        RequisicaoConflitanteException exception = assertThrows(
                RequisicaoConflitanteException.class,
                () -> usuarioService.cadastrarUsuarioComum(novoUsuario)
        );

        verify(usuarioRepository, times(1)).existsByEmail(email);
        assertEquals("Email já cadastrado.", exception.getMessage());
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
        RequisicaoInvalidaException exception = assertThrows(RequisicaoInvalidaException.class, () -> usuarioService.cadastrarUsuarioGestor(usuario));

        assertEquals("Senha deve ter pelo menos 6 caracteres.", exception.getMessage());
    }

    @Test
    @DisplayName("Cadastro de usuário - Senha nula")
    void testeCadastroUsuarioSenhaNula() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("Bianca");
        usuario.setEmail("bia@email.com");
        usuario.setPassword(null); // Simulamos um usuário sem senha

        // Act & Assert
        RequisicaoInvalidaException exception = assertThrows(
                RequisicaoInvalidaException.class,
                () -> usuarioService.cadastrarUsuarioGestor(usuario)
        );
        assertEquals("Senha não pode ser nulo ou vazio.", exception.getMessage());
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
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> usuarioService.getById(99));

        assertEquals("Usuario de id 99 não encontrado", exception.getMessage());
        verify(usuarioRepository).findById(99);
    }

    @Test
    @DisplayName("Buscar usuário por ID - ID inválido")
    void testeBuscarUsuarioIdInvalido() {
        // Arrange
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> usuarioService.getById(-1));

        assertEquals("Usuario de id -1 não encontrado", exception.getMessage());
        verify(usuarioRepository).findById(-1);
    }

    @Test
    @DisplayName("Cadastro de usuário - Cargo inválido")
    void testeCadastroUsuarioSemCargo() {
        // Arrange
        UsuarioModel usuario = UsuarioModel.builder()
                .nome("Gabriel")
                .email("gabriel@email.com")
                .password("abcdefg")
                .build();

        when(cargoRepository.findByNome("comum")).thenReturn(null);
        // Act
        UsuarioModel resultado = usuarioService.cadastrarUsuarioComum(usuario);

        // Assert
        assertNull(resultado);
    }

    @Test
    @DisplayName("Cadastro de usuário gestor - Sucesso")
    void testeCadastroUsuarioGestor() {
        // Arrange
        CargoModel cargo = new CargoModel();
        cargo.setId(2);
        cargo.setNome("gestor");

        UsuarioModel usuario = UsuarioModel.builder()
                .nome("Miguel")
                .email("miguel@email.com")
                .password("abcdefg")
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
    @DisplayName("Cadastro de usuário gestor - E-mail já existente")
    void testeCadastroUsuarioGestorEmailDuplicado() {
        // Arrange
        String email = "diego@email.com";

        when(usuarioRepository.existsByEmail(email))
                .thenReturn(true);

        UsuarioModel novoUsuario = new UsuarioModel();
        novoUsuario.setEmail(email);
        novoUsuario.setNome("Usuário Novo");
        novoUsuario.setPassword("senhaVali123");

        // Act & Assert
        RequisicaoConflitanteException exception = assertThrows(
                RequisicaoConflitanteException.class,
                () -> usuarioService.cadastrarUsuarioComum(novoUsuario)
        );

        verify(usuarioRepository, times(1)).existsByEmail(email);
        assertEquals("Email já cadastrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Cadastro de usuário gestor- Senha muito curta")
    void testeCadastroUsuarioGestorSenhaCurta() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("Usuário");
        usuario.setEmail("usuario@email.com");
        usuario.setPassword("123");

        // Act & Assert
        RequisicaoInvalidaException exception = assertThrows(RequisicaoInvalidaException.class, () -> usuarioService.cadastrarUsuarioGestor(usuario));

        assertEquals("Senha deve ter pelo menos 6 caracteres.", exception.getMessage());
    }

    @Test
    @DisplayName("Cadastro de usuário gestor- Senha nula")
    void testeCadastroUsuarioGestorSenhaNula() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("Usuário");
        usuario.setEmail("usuario@email.com");
        usuario.setPassword(null);

        // Act & Assert
        RequisicaoInvalidaException exception = assertThrows(
                RequisicaoInvalidaException.class,
                () -> usuarioService.cadastrarUsuarioGestor(usuario)
        );
        assertEquals("Senha não pode ser nulo ou vazio.", exception.getMessage());

    }


    @Test
    @DisplayName("Cadastro de usuário gestor - Cargo inválido")
    void testeCadastroUsuarioGestorSemCargo() {
        // Arrange
        UsuarioModel usuario = UsuarioModel.builder()
                .nome("Gabriel")
                .email("gabriel@email.com")
                .password("abcdefg")
                .build();

        when(cargoRepository.findByNome("gestor")).thenReturn(null);

        // Act
        UsuarioModel resultado = usuarioService.cadastrarUsuarioGestor(usuario);

        // Assert
        assertNull(resultado);
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
        usuarioAtualizado.setPassword("senhaNova");

        lenient().when(usuarioRepository.existsById(1)).thenReturn(true);
        lenient().when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioExistente));
        lenient().when(usuarioRepository.save(any())).thenReturn(usuarioAtualizado);

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
    @DisplayName("Atualizar usuário - Atualização parcial")
    void testeAtualizarUsuarioParcial() {
        // Arrange
        UsuarioModel usuarioExistente = new UsuarioModel();
        usuarioExistente.setId(1);
        usuarioExistente.setNome("Antigo Nome");
        usuarioExistente.setEmail("email@email.com");
        usuarioExistente.setPassword("senha@2025");

        lenient().when(usuarioRepository.existsById(1)).thenReturn(true);
        lenient().when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioExistente));

        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setNome("Novo Nome");
        usuarioAtualizado.setEmail("emailnovo@email.com");

        when(usuarioRepository.save(any())).thenAnswer(invocation -> {
            UsuarioModel usuarioSalvo = invocation.getArgument(0);
            return usuarioSalvo; // Retorna o objeto atualizado
        });

        // Act
        UsuarioModel resultado = usuarioService.put(usuarioAtualizado, 1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Novo Nome", resultado.getNome());
        verify(usuarioRepository).save(any());
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
        RequisicaoInvalidaException exception = assertThrows(RequisicaoInvalidaException.class, () -> usuarioService.put(usuarioAtualizado, 1));

        System.out.println("Mensagem de erro retornada: " + exception.getMessage());

        assertTrue(
                exception.getMessage().equals("Nome e email não podem estar vazios") ||
                        exception.getMessage().equals("Nome não pode estar vazio") ||
                        exception.getMessage().equals("Email não pode estar vazio")
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
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> usuarioService.put(usuarioAtualizado, 99));

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
        verify(usuarioRepository, never()).save(any());
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
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Desativar usuário - Usuário desativado com sucesso")
    void deveDesativarUsuarioComSucesso() {
        Integer idUsuario = 1;
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(idUsuario);
        usuario.setAtivo(true);

        UsuarioAtivoDto dto = new UsuarioAtivoDto();
        dto.setAtivo(false); // queremos desativar

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuario);

        UsuarioAtivoDto response = usuarioService.desativarUsuario(idUsuario, dto);

        assertFalse(response.getAtivo()); // o usuário deve estar inativo
        assertFalse(usuario.getAtivo());  // também vale verificar o model salvo
    }

    @Test
    @DisplayName("Ativar usuário - Usuário ativado com sucesso")
    void deveAtivarUsuarioComSucesso() {
        Integer idUsuario = 2;
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(idUsuario);
        usuario.setAtivo(false);

        UsuarioAtivoDto dto = new UsuarioAtivoDto();
        dto.setAtivo(true);

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuario);

        UsuarioAtivoDto response = usuarioService.desativarUsuario(idUsuario, dto);

        assertTrue(response.getAtivo());
        assertTrue(usuario.getAtivo());
    }

    void assertUsuarioNaoEncontrado(Integer id, boolean ativo) {
        UsuarioAtivoDto dto = new UsuarioAtivoDto();
        dto.setAtivo(ativo);

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.desativarUsuario(id, dto);
        });
    }

    @Test
    @DisplayName("Desativar usuário - Deve ser lançada uma exceção quando o id for inexistente!")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        assertUsuarioNaoEncontrado(999, false);
    }

    @Test
    @DisplayName("Ativar usuário - Deve ser lançada uma exceção quando o id for inexistente!")
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoParaAtivar() {
        assertUsuarioNaoEncontrado(999, true);
    }
}


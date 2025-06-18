package sptech.school.CRUD.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.Repository.UsuarioRepository;
import static org.mockito.Mockito.*;

import java.util.Optional;

class AutenticacaoServiceTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationManager = mock(AuthenticationManager.class);
    }
    @InjectMocks
    private UsuarioService usuarioService;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("Autenticação - sucesso")
    void testeLoginSucesso() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1);
        usuario.setNome("Carlos");
        usuario.setEmail("carlos@email.com");
        usuario.setPassword("senhaCriptografada");

        when(usuarioRepository.findByEmail("carlos@email.com")).thenReturn(Optional.of(usuario));

        // Act
        UserDetails resultado = autenticacaoService.loadUserByUsername("carlos@email.com");

        // Assert
        assertNotNull(resultado);
        assertEquals("carlos@email.com", resultado.getUsername());
        assertEquals("senhaCriptografada", resultado.getPassword());
        verify(usuarioRepository).findByEmail("carlos@email.com");
    }

    @Test
    @DisplayName("Autenticação - email não cadastrado")
    void testeLoginEmailNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        // Act & Assert
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> autenticacaoService.loadUserByUsername("emailInexistente@email.com"));

        assertEquals("usuario: emailInexistente@email.com não encontrado", exception.getMessage());
        verify(usuarioRepository).findByEmail("emailInexistente@email.com");
    }
    

}
package sptech.school.CRUD.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sptech.school.CRUD.interfaces.dto.Usuario.UsuarioDetalhesDto;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GerenciadorTokenJwt {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private long jwtTokenValidity;

    public String getUsernameFromToken(String token) {return getClaimForToken(token, Claims::getSubject);}

    public Date getExpirationDateFromToken(String token) {return getClaimForToken(token, Claims::getExpiration);}

    public String generateToken(final Authentication authentication) {
        Object principal = authentication.getPrincipal();

        Long userId = null;
        if (principal instanceof UsuarioDetalhesDto) {
            userId = ((UsuarioDetalhesDto) principal).getId();
        }

        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("id", userId)   // id do usuário incluso aqui
                .claim("roles", authorities) // opcional
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1_000))
                .signWith(parseSecret())
                .compact();
    }



    public <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsForToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return  expirationDate.before(new Date(System.currentTimeMillis()));
    }

    private Claims getAllClaimsForToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(parseSecret())
                .build()
                .parseClaimsJws(token).getBody();
    }

    public boolean isResetTokenValid(String jwt, String email){
        Jws<Claims> parsed = Jwts.parserBuilder()
                .setSigningKey(parseSecret())
                .build()
                .parseClaimsJws(jwt);

        Claims c = parsed.getBody();

        String purpose = c.get("purpose", String.class);
        if(purpose == null || !purpose.equals("password_reset"))
        {
            return false;
        }

        String sub = c.getSubject();
        if (sub == null || !sub.equals(email)) {
            return false;
        }

        // não pode estar expirado
        Date exp = c.getExpiration();
        if (exp == null || exp.before(new Date())) {
            return false;
        }

        String jti = c.getId();
        if (jti == null) {
            return false;
        }

        return true;
    }

    public String extractJti(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(parseSecret())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getId();
    }

    public LocalDateTime extrairExpiracao(String jwt) {
        Date expiracao = Jwts.parserBuilder()
                .setSigningKey(parseSecret())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getExpiration();

        return expiracao.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private SecretKey parseSecret() {return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));}
}

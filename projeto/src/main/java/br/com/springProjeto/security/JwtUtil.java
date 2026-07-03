package br.com.springProjeto.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // CHAVE SECRETA: Em produção, deve vir de variáveis de ambiente.
    // Usamos uma string longa para cumprir o requisito mínimo de 256 bits do algoritmo HS256.
    private static final String SECRET_KEY_STRING = "9f7a8b6c5d4e3f2g1h0i9j8k7l6m5n4o3p2q1r0s9t8u7v6w5x4y3z2A1B3C4D5E";

    // TEMPO DE EXPIRAÇÃO: 86400000 milissegundos equivalem a exatamente 24 horas de validade.
    private static final long EXPIRATION_TIME = 86400000;

    private final SecretKey secretKey;

    public JwtUtil() {
        // Converte a String secreta num formato de chave segura legível pela biblioteca JJWT
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    }

    // 1. GERAÇÃO: Fabrica o Token JWT com base no UserDetails do utilizador autenticado
    public String gerarToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return criarToken(claims, userDetails.getUsername());
    }

    private String criarToken(Map<String, Object> claims, String subject) {
        long agora = System.currentTimeMillis();
        return Jwts.builder()
                .claims(claims)
                .subject(subject) // O assunto do token (no nosso caso, o e-mail do utilizador)
                .issuedAt(new Date(agora)) // Data de emissão do token
                .expiration(new Date(agora + EXPIRATION_TIME)) // Data de expiração
                .signWith(secretKey, Jwts.SIG.HS256) // Assina digitalmente o token usando o algoritmo HS256
                .compact();
    }

    // 2. EXTRAÇÃO: Extrai o e-mail (username) de dentro do token
    public String extrairUsername(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    // 3. EXTRAÇÃO: Extrai a data de expiração para checagem de validade
    public Date extrairExpiracao(String token) {
        return extrairClaim(token, Claims::getExpiration);
    }

    public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extrairTodosClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extrairTodosClaims(String token) {
        // Abre e decodifica o token usando a chave secreta para validar a integridade da assinatura
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 4. VALIDAÇÃO: Verifica se o token pertence ao utilizador e se ainda não expirou
    public boolean validarToken(String token, UserDetails userDetails) {
        final String username = extrairUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpirado(token));
    }

    private boolean isTokenExpirado(String token) {
        return extrairExpiracao(token).before(new Date());
    }

    public long getTempoExpiracaoEmMilissegundos() {
        return EXPIRATION_TIME;
    }
}

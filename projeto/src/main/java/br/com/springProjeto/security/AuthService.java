package br.com.springProjeto.security;

import br.com.springProjeto.dto.request.LoginRequestDTO;
import br.com.springProjeto.dto.response.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public TokenResponseDTO autenticar(LoginRequestDTO dto) {
        // 1. Cria o token bruto com as credenciais enviadas pelo utilizador
        UsernamePasswordAuthenticationToken autenticacaoToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());

        // 2. O AuthenticationManager valida as credenciais contra o banco de dados (usando BCrypt)
        Authentication authentication = authenticationManager.authenticate(autenticacaoToken);

        // 3. Recupera o UserDetails (UsuarioPrincipal) do utilizador autenticado com sucesso
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 4. Fabrica o passaporte digital (Token JWT) para o utilizador
        String tokenJwt = jwtUtil.gerarToken(userDetails);

        // 5. Monta e retorna o DTO de resposta estruturado com os dados do token
        return TokenResponseDTO.builder()
                .token(tokenJwt)
                .tipo("Bearer")
                .expiracaoEmMilissegundos(jwtUtil.getTempoExpiracaoEmMilissegundos())
                .build();
    }
}

package br.com.springProjeto.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFiltroAutenticacao extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AutenticacaoService autenticacaoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Obtém o cabeçalho 'Authorization' da requisição HTTP
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Se o cabeçalho for nulo ou não começar com 'Bearer ', ignora e segue o fluxo normal
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extrai o token removendo o prefixo 'Bearer ' (7 caracteres)
        jwt = authHeader.substring(7);

        // 3. Extrai o e-mail do utilizador de dentro do Payload do Token
        userEmail = jwtUtil.extrairUsername(jwt);

        // Se encontrou o e-mail e o utilizador ainda não estiver autenticado no contexto do Spring
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carrega o espelho do utilizador (UserDetails) do nosso banco de dados
            UserDetails userDetails = this.autenticacaoService.loadUserByUsername(userEmail);

            // 4. Valida matematicamente se o token é íntegro e se pertence ao utilizador
            if (jwtUtil.validarToken(jwt, userDetails)) {

                // Cria o objeto de autenticação preenchido com o utilizador e os seus privilégios (Roles)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Acopla detalhes extras da requisição HTTP original ao token de autenticação
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // INJETA A AUTENTICAÇÃO NO CONTEXTO GLOBAL DO SPRING SECURITY
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continua o envio da requisição para o próximo filtro na cadeia de execução
        filterChain.doFilter(request, response);
    }
}

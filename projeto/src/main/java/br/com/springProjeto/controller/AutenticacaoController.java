package br.com.springProjeto.controller;

import br.com.springProjeto.dto.request.LoginRequestDTO;
import br.com.springProjeto.dto.response.TokenResponseDTO;
import br.com.springProjeto.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthService authService;

    // ENDPOINT PÚBLICO: Recebe as credenciais, valida-as e gera o Token JWT
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        TokenResponseDTO tokenResponse = authService.autenticar(dto);
        return ResponseEntity.ok(tokenResponse);
    }
}

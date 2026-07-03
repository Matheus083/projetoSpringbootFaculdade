package br.com.springProjeto.controller;

import br.com.springProjeto.dto.request.UsuarioRequestDTO;
import br.com.springProjeto.dto.response.UsuarioResponseDTO;
import br.com.springProjeto.security.UsuarioPrincipal;
import br.com.springProjeto.service.UsuarioService;
import br.com.springProjeto.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ENDPOINT PÚBLICO: Permite o cadastro de novos utilizadores com perfil base 'ROLE_CLIENTE'
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioSalvo = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    // ENDPOINT PROTEGIDO: Retorna os dados do perfil do utilizador atualmente autenticado via Token JWT
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioResponseDTO> obterPerfilAtual(@AuthenticationPrincipal UsuarioPrincipal usuarioLogado) {
        // Extrai a entidade Usuario de dentro do contexto de segurança e converte para DTO
        UsuarioResponseDTO perfil = MapperUtil.toUsuarioResponse(usuarioLogado.getUsuario());
        return ResponseEntity.ok(perfil);
    }

    // ENDPOINT PROTEGIDO: Lista todos os utilizadores (Exige autenticação base configurada na Fase 16)
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }
}
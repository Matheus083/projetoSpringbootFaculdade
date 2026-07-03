package br.com.springProjeto.controller;

import br.com.springProjeto.dto.request.AssinaturaRequestDTO;
import br.com.springProjeto.dto.response.AssinaturaResponseDTO;
import br.com.springProjeto.security.UsuarioPrincipal;
import br.com.springProjeto.service.AssinaturaService;
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
@RequestMapping("/api/assinaturas")
@RequiredArgsConstructor
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    // ENDPOINT PROTEGIDO: Cria uma nova assinatura vinculada automaticamente ao cliente logado
    @PostMapping
    public ResponseEntity<AssinaturaResponseDTO> criarAssinatura(
            @AuthenticationPrincipal UsuarioPrincipal usuarioLogado,
            @Valid @RequestBody AssinaturaRequestDTO dto) {

        // Extrai o ID do utilizador autenticado e passa para a camada de serviço
        AssinaturaResponseDTO assinatura = assinaturaService.criarAssinatura(usuarioLogado.getUsuario().getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assinatura);
    }

    // ENDPOINT PROTEGIDO: Lista o histórico de todas as assinaturas do próprio cliente autenticado
    @GetMapping("/mehas")
    public ResponseEntity<List<AssinaturaResponseDTO>> listarMinhasAssinaturas(
            @AuthenticationPrincipal UsuarioPrincipal usuarioLogado) {

        List<AssinaturaResponseDTO> assinaturas = assinaturaService.listarPorUsuario(usuarioLogado.getUsuario().getId());
        return ResponseEntity.ok(assinaturas);
    }
}

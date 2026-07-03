package br.com.springProjeto.controller;

import br.com.springProjeto.dto.request.PlanoRequestDTO;
import br.com.springProjeto.dto.response.PlanoResponseDTO;
import br.com.springProjeto.service.PlanoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/planos")
@RequiredArgsConstructor
public class PlanoController {

    private final PlanoService planoService;

    // ENDPOINT RESTRITO (ADMIN): Cadastra um novo plano de caixas no sistema
    @PostMapping
    public ResponseEntity<PlanoResponseDTO> cadastrar(@Valid @RequestBody PlanoRequestDTO dto) {
        PlanoResponseDTO planoSalvo = planoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(planoSalvo);
    }

    // ENDPOINT PROTEGIDO: Busca os detalhes de um plano específico pelo seu ID
    @GetMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> buscarPorId(@PathVariable Long id) {
        PlanoResponseDTO plano = planoService.buscarPorId(id);
        return ResponseEntity.ok(plano);
    }

    // ENDPOINT PROTEGIDO: Lista todos os planos de assinatura cadastrados e ativos
    @GetMapping
    public ResponseEntity<List<PlanoResponseDTO>> listarTodos() {
        List<PlanoResponseDTO> planos = planoService.listarTodos();
        return ResponseEntity.ok(planos);
    }
}

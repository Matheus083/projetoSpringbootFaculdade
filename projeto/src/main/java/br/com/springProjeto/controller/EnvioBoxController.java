package br.com.springProjeto.controller;

import br.com.springProjeto.dto.response.EnvioBoxResponseDTO;
import br.com.springProjeto.service.EnvioBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/envios")
@RequiredArgsConstructor
public class EnvioBoxController {

    private final EnvioBoxService envioBoxService;

    // ENDPOINT PROTEGIDO: Lista todo o histórico de envios vinculados a uma assinatura específica
    @GetMapping("/assinatura/{assinaturaId}")
    public ResponseEntity<List<EnvioBoxResponseDTO>> listarPorAssinatura(@PathVariable Long assinaturaId) {
        List<EnvioBoxResponseDTO> envios = envioBoxService.listarPorAssinatura(assinaturaId);
        return ResponseEntity.ok(envios);
    }

    // ENDPOINT RESTRITO (ADMIN): Atualiza o status de uma caixa para ENVIADA e anexa o código de rastreio
    @PutMapping("/{id}/enviar")
    public ResponseEntity<EnvioBoxResponseDTO> despacharCaixa(
            @PathVariable Long id,
            @RequestParam String codigoRastreamento) {

        EnvioBoxResponseDTO envioAtualizado = envioBoxService.atualizarParaEnviado(id, codigoRastreamento);
        return ResponseEntity.ok(envioAtualizado);
    }
}

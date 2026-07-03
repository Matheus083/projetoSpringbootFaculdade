package br.com.springProjeto.controller;

import br.com.springProjeto.dto.response.DashboardResumoDTO;
import br.com.springProjeto.service.AssinaturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final AssinaturaService assinaturaService;

    // ENDPOINT RESUMO (MANDATÓRIO): Retorna os dados consolidados e métricas globais do ecossistema
    @GetMapping("/resumo")
    public ResponseEntity<DashboardResumoDTO> obterResumoGeral() {
        DashboardResumoDTO resumo = assinaturaService.obterDashboardResumo();
        return ResponseEntity.ok(resumo);
    }
}

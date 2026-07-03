package br.com.springProjeto.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResumoDTO {

    private long totalAssinantesAtivos;
    private BigDecimal faturamentoMensalEstimado;
    private long totalCaixasPendentesEntrega;
}
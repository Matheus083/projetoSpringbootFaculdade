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
public class PlanoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valorMensal;
}

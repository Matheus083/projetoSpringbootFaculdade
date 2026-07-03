package br.com.springProjeto.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanoRequestDTO {

    @NotBlank(message = "O nome do plano é obrigatório.")
    private String nome;

    @NotBlank(message = "A descrição do plano é obrigatória.")
    private String descricao;

    @NotNull(message = "O valor mensal do plano é obrigatório.")
    @Positive(message = "O valor mensal do plano deve ser maior que zero.")
    private BigDecimal valorMensal;
}

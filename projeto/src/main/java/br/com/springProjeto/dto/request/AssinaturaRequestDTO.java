package br.com.springProjeto.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaRequestDTO {

    @NotNull(message = "O ID do plano é obrigatório para realizar uma assinatura.")
    private Long planoId;
}

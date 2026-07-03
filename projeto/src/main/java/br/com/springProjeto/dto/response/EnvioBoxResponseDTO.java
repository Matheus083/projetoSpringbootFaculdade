package br.com.springProjeto.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvioBoxResponseDTO {

    private Long id;
    private Long assinaturaId;
    private LocalDate dataPrevisao;
    private LocalDate dataEnvio;
    private String codigoRastreamento;
    private String status;
}

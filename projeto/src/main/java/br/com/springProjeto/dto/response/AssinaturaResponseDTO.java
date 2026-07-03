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
public class AssinaturaResponseDTO {

    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private Long planoId;
    private String planoNome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status;
}

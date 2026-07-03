package br.com.springProjeto.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail fornecido é inválido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;
}

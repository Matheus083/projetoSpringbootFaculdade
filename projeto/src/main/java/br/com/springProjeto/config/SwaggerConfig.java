package br.com.springProjeto.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String nomeSeguranca = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Clube de Assinaturas API")
                        .version("1.0.0")
                        .description("Documentação interativa dos endpoints do ecossistema do Clube de Assinaturas."))
                // Adiciona a exigência de segurança global para os testes dos endpoints
                .addSecurityItem(new SecurityRequirement().addList(nomeSeguranca))
                // Configura o componente que exibirá o botão "Authorize" para inserção do Token JWT
                .components(new Components()
                        .addSecuritySchemes(nomeSeguranca, new SecurityScheme()
                                .name(nomeSeguranca)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Insira o seu Token JWT gerado no endpoint de Login para autenticar as requisições.")));
    }
}

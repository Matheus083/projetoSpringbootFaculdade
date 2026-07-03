package br.com.springProjeto.util;

import br.com.springProjeto.entity.Assinatura;
import br.com.springProjeto.entity.Endereco;
import br.com.springProjeto.entity.EnvioBox;
import br.com.springProjeto.entity.Plano;
import br.com.springProjeto.entity.Usuario;
import br.com.springProjeto.dto.request.PlanoRequestDTO;
import br.com.springProjeto.dto.request.UsuarioRequestDTO;
import br.com.springProjeto.dto.response.AssinaturaResponseDTO;
import br.com.springProjeto.dto.response.EnvioBoxResponseDTO;
import br.com.springProjeto.dto.response.PlanoResponseDTO;
import br.com.springProjeto.dto.response.UsuarioResponseDTO;
import br.com.springProjeto.enums.StatusAssinatura;

public class MapperUtil {

    // Construtor privado para impedir que a classe utilitária seja instanciada
    private MapperUtil() {
        throw new IllegalStateException("Classe Utilitária - não deve ser instanciada.");
    }

    // 1. USUÁRIO: Request DTO -> Entidade Usuario + Endereco
    public static Usuario toUsuarioEntity(UsuarioRequestDTO dto) {
        if (dto == null) return null;

        Endereco endereco = Endereco.builder()
                .logradouro(dto.getLogradouro())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .build();

        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .endereco(endereco)
                .build();
    }

    // 2. USUÁRIO: Entidade -> Response DTO
    public static UsuarioResponseDTO toUsuarioResponse(Usuario usuario) {
            if (usuario == null) return null;

    UsuarioResponseDTO.UsuarioResponseDTOBuilder builder = UsuarioResponseDTO.builder()
            .id(usuario.getId())
            .nome(usuario.getNome())
            .email(usuario.getEmail());

        if (usuario.getEndereco() != null) {
        Endereco end = usuario.getEndereco();
        builder.logradouro(end.getLogradouro())
                .numero(end.getNumero())
                .complemento(end.getComplemento())
                .bairro(end.getBairro())
                .cidade(end.getCidade())
                .estado(end.getEstado())
                .cep(end.getCep());
    }

        return builder.build();
}

// 3. PLANO: Request DTO -> Entidade
public static Plano toPlanoEntity(PlanoRequestDTO dto) {
    if (dto == null) return null;

    return Plano.builder()
            .nome(dto.getNome())
            .descricao(dto.getDescricao())
            .valorMensal(dto.getValorMensal())
            .build();
}

// 4. PLANO: Entidade -> Response DTO (Nota: Criamos o DTO inline para consistência)
public static PlanoResponseDTO toPlanoResponse(Plano plano) {
    if (plano == null) return null;

    // Caso o PlanoResponseDTO não tenha sido criado explicitamente na fase 9,
    // geramos um Builder compatível com os atributos da entidade Plano.
    return PlanoResponseDTO.builder()
            .id(plano.getId())
            .nome(plano.getNome())
            .descricao(plano.getDescricao())
            .valorMensal(plano.getValorMensal())
            .build();
}

// 5. ASSINATURA: Entidade -> Response DTO
public static AssinaturaResponseDTO toAssinaturaResponse(Assinatura assinatura) {
    if (assinatura == null) return null;

    return AssinaturaResponseDTO.builder()
            .id(assinatura.getId())
            .usuarioId(assinatura.getUsuario() != null ? colocarIdUsuario(assinatura.getUsuario()) : null)
            .usuarioNome(assinatura.getUsuario() != null ? assinatura.getUsuario().getNome() : null)
            .planoId(assinatura.getPlano() != null ? assinatura.getPlano().getId() : null)
            .planoNome(assinatura.getPlano() != null ? assinatura.getPlano().getNome() : null)
            .dataInicio(assinatura.getDataInicio())
            .dataFim(assinatura.getDataFim())
            .status(assinatura.getStatus() != null ? assinatura.getStatus().name() : null)
            .build();
}

// 6. ENVIO BOX: Entidade -> Response DTO
public static EnvioBoxResponseDTO toEnvioBoxResponse(EnvioBox envio) {
    if (envio == null) return null;

    return EnvioBoxResponseDTO.builder()
            .id(envio.getId())
            .assinaturaId(envio.getAssinatura() != null ? envio.getAssinatura().getId() : null)
            .dataPrevisao(envio.getDataPrevisao())
            .dataEnvio(envio.getDataEnvio())
            .codigoRastreamento(envio.getCodigoRastreamento())
            .status(envio.getStatus() != null ? envio.getStatus().name() : null)
            .build();
}

// Método auxiliar privado de desmembramento para evitar duplicação ou acoplamento direto
private static Long colocarIdUsuario(Usuario usuario) {
    return usuario.getId();
}
}

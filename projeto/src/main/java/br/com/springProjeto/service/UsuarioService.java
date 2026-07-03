package br.com.springProjeto.service;

import br.com.springProjeto.dto.request.UsuarioRequestDTO;
import br.com.springProjeto.dto.response.UsuarioResponseDTO;
import br.com.springProjeto.entity.Usuario;
import br.com.springProjeto.exception.RecursoNaoEncontradoException;
import br.com.springProjeto.exception.RegraNegocioException;
import br.com.springProjeto.repository.UsuarioRepository;
import br.com.springProjeto.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Injeção do codificador de senhas

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Não é possível realizar o cadastro: o e-mail fornecido já está em uso.");
        }

        Usuario usuario = MapperUtil.toUsuarioEntity(dto);

        // REGRA DE SEGURANÇA: Criptografa a senha em formato hash BCrypt antes de persistir
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setRole("ROLE_CLIENTE");

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return MapperUtil.toUsuarioResponse(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID informado: " + id));
        return MapperUtil.toUsuarioResponse(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(MapperUtil::toUsuarioResponse)
                .collect(Collectors.toList());
    }
}

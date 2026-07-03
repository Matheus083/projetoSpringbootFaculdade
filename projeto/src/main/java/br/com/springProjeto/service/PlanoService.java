package br.com.springProjeto.service;

import br.com.springProjeto.dto.request.PlanoRequestDTO;
import br.com.springProjeto.dto.response.PlanoResponseDTO;
import br.com.springProjeto.entity.Plano;
import br.com.springProjeto.exception.RecursoNaoEncontradoException;
import br.com.springProjeto.exception.RegraNegocioException;
import br.com.springProjeto.repository.PlanoRepository;
import br.com.springProjeto.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanoService {

    private final PlanoRepository planoRepository;

    @Transactional
    public PlanoResponseDTO cadastrar(PlanoRequestDTO dto) {
        if (planoRepository.existsByNome(dto.getNome())) {
            throw new RegraNegocioException("Não é possível cadastrar o plano: já existe um plano com este nome.");
        }

        Plano plano = MapperUtil.toPlanoEntity(dto);
        Plano planoSalvo = planoRepository.save(plano);
        return MapperUtil.toPlanoResponse(planoSalvo);
    }

    @Transactional(readOnly = true)
    public PlanoResponseDTO buscarPorId(Long id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado com o ID informado: " + id));
        return MapperUtil.toPlanoResponse(plano);
    }

    @Transactional(readOnly = true)
    public List<PlanoResponseDTO> listarTodos() {
        return planoRepository.findAll().stream()
                .map(MapperUtil::toPlanoResponse)
                .collect(Collectors.toList());
    }
}

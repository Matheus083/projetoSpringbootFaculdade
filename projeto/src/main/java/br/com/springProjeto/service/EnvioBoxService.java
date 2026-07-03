package br.com.springProjeto.service;

import br.com.springProjeto.dto.response.EnvioBoxResponseDTO;
import br.com.springProjeto.entity.EnvioBox;
import br.com.springProjeto.enums.StatusEntrega;
import br.com.springProjeto.exception.RecursoNaoEncontradoException;
import br.com.springProjeto.repository.EnvioBoxRepository;
import br.com.springProjeto.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnvioBoxService {

    private final EnvioBoxRepository envioBoxRepository;

    @Transactional(readOnly = true)
    public List<EnvioBoxResponseDTO> listarPorAssinatura(Long assinaturaId) {
        return envioBoxRepository.findByAssinaturaId(assinaturaId).stream()
                .map(MapperUtil::toEnvioBoxResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnvioBoxResponseDTO atualizarParaEnviado(Long id, String codigoRastreamento) {
        EnvioBox envio = envioBoxRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro de envio de caixa não encontrado para o ID: " + id));

        envio.setStatus(StatusEntrega.ENVIADA);
        envio.setDataEnvio(LocalDate.now());
        envio.setCodigoRastreamento(codigoRastreamento);

        EnvioBox salvo = envioBoxRepository.save(envio);
        return MapperUtil.toEnvioBoxResponse(salvo);
    }
}

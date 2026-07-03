package br.com.springProjeto.service;

import br.com.springProjeto.dto.request.AssinaturaRequestDTO;
import br.com.springProjeto.dto.response.AssinaturaResponseDTO;
import br.com.springProjeto.dto.response.DashboardResumoDTO;
import br.com.springProjeto.dto.response.EnvioBoxResponseDTO;
import br.com.springProjeto.entity.Assinatura;
import br.com.springProjeto.entity.EnvioBox;
import br.com.springProjeto.entity.Plano;
import br.com.springProjeto.entity.Usuario;
import br.com.springProjeto.enums.StatusAssinatura;
import br.com.springProjeto.enums.StatusEntrega;
import br.com.springProjeto.exception.RecursoNaoEncontradoException;
import br.com.springProjeto.repository.AssinaturaRepository;
import br.com.springProjeto.repository.EnvioBoxRepository;
import br.com.springProjeto.repository.PlanoRepository;
import br.com.springProjeto.repository.UsuarioRepository;
import br.com.springProjeto.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlanoRepository planoRepository;
    private final EnvioBoxRepository envioBoxRepository;

    @Transactional
    public AssinaturaResponseDTO criarAssinatura(Long usuarioId, AssinaturaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível assinar: Usuário não encontrado com ID: " + usuarioId));

        Plano plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível assinar: Plano não encontrado com ID: " + dto.getPlanoId()));

        // Cria a Assinatura Ativa na data atual
        Assinatura assinatura = Assinatura.builder()
                .usuario(usuario)
                .plano(plano)
                .dataInicio(LocalDate.now())
                .status(StatusAssinatura.ATIVA)
                .build();

        Assinatura assinaturaSalva = assinaturaRepository.save(assinatura);

        // REGRA DE NEGÓCIO: Gera automaticamente o primeiro envio previsto para daqui a 10 dias
        EnvioBox primeiroEnvio = EnvioBox.builder()
                .assinatura(assinaturaSalva)
                .dataPrevisao(LocalDate.now().plusDays(10))
                .status(StatusEntrega.PENDENTE)
                .build();

        envioBoxRepository.save(primeiroEnvio);

        return MapperUtil.toAssinaturaResponse(assinaturaSalva);
    }

    @Transactional(readOnly = true)
    public List<AssinaturaResponseDTO> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + usuarioId);
        }
        return assinaturaRepository.findByUsuarioId(usuarioId).stream()
                .map(MapperUtil::toAssinaturaResponse)
                .collect(Collectors.toList());
    }

    // REGRA DE NEGÓCIO MANDATÓRIA: Agregação de dados para o Endpoint Resumo
    @Transactional(readOnly = true)
    public DashboardResumoDTO obterDashboardResumo() {
        long ativos = assinaturaRepository.countByStatus(StatusAssinatura.ATIVA);
        long pendentes = envioBoxRepository.countByStatus(StatusEntrega.PENDENTE);
        BigDecimal faturamento = assinaturaRepository.calcularFaturamentoAtivo();

        // Evita retornar nulo caso não existam assinaturas ativas no banco
        if (faturamento == null) {
            faturamento = BigDecimal.ZERO;
        }

        return DashboardResumoDTO.builder()
                .totalAssinantesAtivos(ativos)
                .totalCaixasPendentesEntrega(pendentes)
                .faturamentoMensalEstimado(faturamento)
                .build();
    }
}

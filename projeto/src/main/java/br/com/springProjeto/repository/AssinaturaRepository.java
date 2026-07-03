package br.com.springProjeto.repository;

import br.com.springProjeto.entity.Assinatura;
import br.com.springProjeto.enums.StatusAssinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    List<Assinatura> findByUsuarioId(Long usuarioId);

    long countByStatus(StatusAssinatura status);

    @Query("SELECT SUM(p.valorMensal) FROM Assinatura a JOIN a.plano p WHERE a.status = 'ATIVA'")
    java.math.BigDecimal calcularFaturamentoAtivo();
}

package br.com.springProjeto.repository;

import br.com.springProjeto.entity.EnvioBox;
import br.com.springProjeto.enums.StatusEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnvioBoxRepository extends JpaRepository<EnvioBox, Long> {

    List<EnvioBox> findByAssinaturaId(Long assinaturaId);

    long countByStatus(StatusEntrega status);
}
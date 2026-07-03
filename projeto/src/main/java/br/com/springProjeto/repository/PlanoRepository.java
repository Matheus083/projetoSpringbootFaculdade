package br.com.springProjeto.repository;

import br.com.springProjeto.entity.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Long> {

    boolean existsByNome(String nome);
}

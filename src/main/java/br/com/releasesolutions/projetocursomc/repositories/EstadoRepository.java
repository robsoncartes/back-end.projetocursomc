package br.com.releasesolutions.projetocursomc.repositories;

import br.com.releasesolutions.projetocursomc.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    @Transactional(readOnly = true)
    Optional<Estado> findById(Integer id);
}

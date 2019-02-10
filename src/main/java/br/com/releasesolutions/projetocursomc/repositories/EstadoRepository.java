package br.com.releasesolutions.projetocursomc.repositories;

import br.com.releasesolutions.projetocursomc.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    @Transactional(readOnly = true)
    Optional<Estado> findById(Integer id);

    /* Busca todos os Estados ordenados utilizando o padrão de nomes do Spring Data.
    @Transactional(readOnly = true)
    List<Estado> findAllByOrderByNome();
    */

    // Busca todos os Estados utilizando o padrão e anotação @Query. Obs.: possui o mesmo efeito do método findAllByOrderByNome.
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT estado FROM Estado estado ORDER BY estado.nome ASC")
    List<Estado> findAllEstadosOrderedByName();
}
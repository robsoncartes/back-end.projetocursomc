package br.com.releasesolutions.projetocursomc.repositories;

import br.com.releasesolutions.projetocursomc.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Transactional(readOnly = true)
    Optional<Cidade> findById(Integer id);

    // Busca de Cidades pelo atributo PK de Estado utilizando o padrão Parâmetros Nomeados (Named parameters) do Spring Data.
    @Transactional(readOnly = true)
    @Query("SELECT cidade FROM Cidade cidade WHERE cidade.estado.id = :estadoId ORDER BY cidade.nome")
    List<Cidade> findCidades(@Param("estadoId") Integer estado_id);
}
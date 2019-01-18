package br.com.releasesolutions.projetocursomc.repositories;

import br.com.releasesolutions.projetocursomc.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Transactional(readOnly = true)
    Optional<Pedido> findById(Integer id);
}

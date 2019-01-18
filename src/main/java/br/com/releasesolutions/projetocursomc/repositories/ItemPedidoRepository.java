package br.com.releasesolutions.projetocursomc.repositories;

import br.com.releasesolutions.projetocursomc.domain.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

    @Transactional(readOnly = true)
    Optional<ItemPedido> findById(Integer id);
}

package br.com.releasesolutions.projetocursomc.repositories;

import br.com.releasesolutions.projetocursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    /* Não necessita ser envolvida como uma transação de banco de dados. Além disso, a consulta fica mais rápida e
        diminui o locking de gerenciamento de transações do banco de dados. */

    @Transactional(readOnly = true)
    Optional<Cliente> findById(Integer id);

    @Transactional(readOnly = true)
    Cliente findByEmail(String email);
}

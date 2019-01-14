package br.com.releasesolutions.projetocursomc.services;

// Nota: O Spring Boot na versão 2.X.X é compatível apenas com as versões do JAVA 8 em diante.

import br.com.releasesolutions.projetocursomc.domain.Cliente;
import br.com.releasesolutions.projetocursomc.repositories.ClienteRepository;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente buscarClientePorId(Integer id) {

        Cliente cliente = clienteRepository.findById(id).orElse(null);

        if (cliente == null)
            throw new ObjectNotFoundException("Cliente não encontrado. Id: " + id + ", Tipo: " + Cliente.class.getName());

        return cliente;
    }
}

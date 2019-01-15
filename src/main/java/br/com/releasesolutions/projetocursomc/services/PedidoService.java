package br.com.releasesolutions.projetocursomc.services;

// Nota: O Spring Boot na versão 2.X.X é compatível apenas com as versões do JAVA 8 em diante.

import br.com.releasesolutions.projetocursomc.domain.Pedido;
import br.com.releasesolutions.projetocursomc.repositories.PedidoRepository;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido buscarPedidoPorId(Integer id) {

        Pedido pedido = pedidoRepository.findById(id).orElse(null);

        if (pedido == null)
            throw new ObjectNotFoundException("Pedido não encontrado. Id: " + id + ", Tipo: " + Pedido.class.getName());

        return pedido;
    }
}

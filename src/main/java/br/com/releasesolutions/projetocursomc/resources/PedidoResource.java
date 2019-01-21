package br.com.releasesolutions.projetocursomc.resources;

import br.com.releasesolutions.projetocursomc.domain.Pedido;
import br.com.releasesolutions.projetocursomc.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    private final PedidoService pedidoService;

    public PedidoResource(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> findPedidoById(@PathVariable Integer id) {

        Pedido pedido = pedidoService.buscarPedidoPorId(id);

        return ResponseEntity.ok().body(pedido);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertPedido(@Valid @RequestBody Pedido pedido) {

        pedidoService.inserirPedido(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}

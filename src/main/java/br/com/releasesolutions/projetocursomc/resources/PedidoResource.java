package br.com.releasesolutions.projetocursomc.resources;

import br.com.releasesolutions.projetocursomc.domain.Pedido;
import br.com.releasesolutions.projetocursomc.dto.PedidoDTO;
import br.com.releasesolutions.projetocursomc.services.PedidoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<Void> insertPedido(@Valid @RequestBody PedidoDTO pedidoDto) {

        Pedido pedido = pedidoService.pedidoFromPedidoDTO(pedidoDto);
        Pedido pedidoSalvo = pedidoService.inserirPedido(pedido);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedidoSalvo.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        Page<Pedido> pedidos = pedidoService.buscarPagina(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok().body(pedidos);
    }
}

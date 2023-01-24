package br.com.releasesolutions.projetocursomc.resources;

import br.com.releasesolutions.projetocursomc.domain.Cliente;
import br.com.releasesolutions.projetocursomc.dto.ClienteDTO;
import br.com.releasesolutions.projetocursomc.dto.ClienteNewDTO;
import br.com.releasesolutions.projetocursomc.services.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    private final ClienteService clienteService;

    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> findClienteById(@PathVariable Integer id) {

        Cliente cliente = clienteService.buscarClientePorId(id);

        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public ResponseEntity<Cliente> findClienteByEmail(@RequestParam(value = "value") String email) {

        Cliente cliente = clienteService.buscarClientePorEmail(email);

        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertCliente(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {

        Cliente cliente = clienteService.fromClienteNewDTO(clienteNewDTO);
        clienteService.inserirCliente(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {

        Cliente cliente = clienteService.fromClienteDTO(clienteDTO);
        cliente.setId(id);
        clienteService.atualizarCliente(cliente);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteClienteById(@PathVariable Integer id) {

        clienteService.deletarClientePorId(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAllClientes() {

        List<Cliente> clientes = clienteService.buscarTodosClientes();
        List<ClienteDTO> clienteDTOS = clientes.stream().map(ClienteDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(clienteDTOS);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Page<Cliente> clientes = clienteService.buscarPagina(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> clienteDTOS = clientes.map(ClienteDTO::new);

        return ResponseEntity.ok().body(clienteDTOS);
    }

    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {

        URI uri = clienteService.uploadProfilePicture(file);

        return ResponseEntity.created(uri).build();
    }
}

package br.com.releasesolutions.projetocursomc.resources;

import br.com.releasesolutions.projetocursomc.domain.Categoria;
import br.com.releasesolutions.projetocursomc.dto.CategoriaDTO;
import br.com.releasesolutions.projetocursomc.services.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    private final CategoriaService categoriaService;

    public CategoriaResource(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> findCategoriaById(@PathVariable Integer id) {

        Categoria categoria = categoriaService.buscarCategoriaPorId(id);

        return ResponseEntity.ok().body(categoria);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {

        Categoria categoria = categoriaService.fromCategoriaDTO(categoriaDTO);
        categoriaService.inserirCategoria(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {

        Categoria categoria = categoriaService.fromCategoriaDTO(categoriaDTO);
        categoria.setId(id);
        categoriaService.atualizarCategoria(categoria);

        return ResponseEntity.noContent().build();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCategoriaById(@PathVariable Integer id) {

        categoriaService.deletarCategoriaPorId(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findAllCategorias() {

        List<Categoria> categorias = categoriaService.buscarTodasCategorias();
        List<CategoriaDTO> categoriaDTOS = categorias.stream().map(CategoriaDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(categoriaDTOS);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Page<Categoria> categorias = categoriaService.buscarPagina(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> categoriaDTOS = categorias.map(CategoriaDTO::new);

        return ResponseEntity.ok().body(categoriaDTOS);
    }
}

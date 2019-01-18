package br.com.releasesolutions.projetocursomc.resources;

import br.com.releasesolutions.projetocursomc.domain.Categoria;
import br.com.releasesolutions.projetocursomc.domain.Produto;
import br.com.releasesolutions.projetocursomc.dto.CategoriaDTO;
import br.com.releasesolutions.projetocursomc.dto.ProdutoDTO;
import br.com.releasesolutions.projetocursomc.resources.utils.URL;
import br.com.releasesolutions.projetocursomc.services.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    private final ProdutoService produtoService;

    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> findProdutoById(@PathVariable Integer id) {

        Produto produto = produtoService.buscarProdutoPorId(id);

        return ResponseEntity.ok().body(produto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "0") String nome,
            @RequestParam(value = "categorias", defaultValue = "0") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        String nomeDecoded = URL.decodeParam(nome);

        List<Integer> ids = URL.decodeIntList(categorias);

        Page<Produto> produtos = produtoService.pesquisarProduto(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> produtoDTOS = produtos.map(ProdutoDTO::new);

        return ResponseEntity.ok().body(produtoDTOS);
    }
}

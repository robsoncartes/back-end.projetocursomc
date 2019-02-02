package br.com.releasesolutions.projetocursomc.services;

// Nota: O Spring Boot na versão 2.X.X é compatível apenas com as versões do JAVA 8 em diante.

import br.com.releasesolutions.projetocursomc.domain.Categoria;
import br.com.releasesolutions.projetocursomc.domain.Produto;
import br.com.releasesolutions.projetocursomc.repositories.CategoriaRepository;
import br.com.releasesolutions.projetocursomc.repositories.ProdutoRepository;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto buscarProdutoPorId(Integer id) {

        Produto produto = produtoRepository.findById(id).orElse(null);

        if (produto == null)
            throw new ObjectNotFoundException("Produto não encontrado. Id: " + id + ", Tipo: " + Produto.class.getName());

        return produto;
    }

    public Page<Produto> pesquisarProduto(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);

        return produtoRepository.search(nome, categorias, pageRequest);
    }
}
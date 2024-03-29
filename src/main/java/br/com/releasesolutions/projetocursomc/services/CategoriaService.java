package br.com.releasesolutions.projetocursomc.services;

// Nota: O Spring Boot na versão 2.X.X é compatível apenas com as versões do JAVA 8 em diante.

import br.com.releasesolutions.projetocursomc.domain.Categoria;
import br.com.releasesolutions.projetocursomc.dto.CategoriaDTO;
import br.com.releasesolutions.projetocursomc.repositories.CategoriaRepository;
import br.com.releasesolutions.projetocursomc.services.exceptions.DataIntegrityException;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria buscarCategoriaPorId(Integer id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);

        if (categoria == null)
            throw new ObjectNotFoundException("Categoria não encontrada. Id: " + id + ", Tipo: " + Categoria.class.getName());

        return categoria;
    }

    @Transactional
    public Categoria inserirCategoria(Categoria categoria) {
        categoria.setId(null);

        return categoriaRepository.save(categoria);
    }

    public Categoria atualizarCategoria(Categoria categoria) {
        Categoria newCategoria = buscarCategoriaPorId(categoria.getId());
        updateData(newCategoria, categoria);

        return categoriaRepository.save(newCategoria);
    }

    public void deletarCategoriaPorId(Integer id) {
        buscarCategoriaPorId(id);
        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
        }
    }

    public List<Categoria> buscarTodasCategorias() {

        return categoriaRepository.findAll();
    }

    public Page<Categoria> buscarPagina(Integer page, Integer linesPerPage, String orderBy, String direction) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria fromCategoriaDTO(CategoriaDTO categoriaDTO) {
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }

    private void updateData(Categoria newCategoria, Categoria categoria) {
        newCategoria.setNome(categoria.getNome());
    }
}

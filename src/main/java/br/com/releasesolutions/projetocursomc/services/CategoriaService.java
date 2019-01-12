package br.com.releasesolutions.projetocursomc.services;

// Nota: O Spring Boot na versão 2.X.X é compatível apenas com as versões do JAVA 8 em diante.

import br.com.releasesolutions.projetocursomc.domain.Categoria;
import br.com.releasesolutions.projetocursomc.repositories.CategoriaRepository;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria buscarCategoria(Integer id) {

        Categoria categoria = categoriaRepository.findById(id).orElse(null);

        if (categoria == null)
            throw new ObjectNotFoundException("Categoria não encontrada. Id: " + id + ", Tipo: " + Categoria.class.getName());

        return categoria;
    }
}

package br.com.releasesolutions.projetocursomc.services;

import br.com.releasesolutions.projetocursomc.domain.Estado;
import br.com.releasesolutions.projetocursomc.repositories.EstadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    private EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> buscarTodosEstados() {

        return estadoRepository.findAllEstadosOrderedByName();
    }
}

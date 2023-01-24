package br.com.releasesolutions.projetocursomc.services;

import br.com.releasesolutions.projetocursomc.domain.Cidade;
import br.com.releasesolutions.projetocursomc.domain.Estado;
import br.com.releasesolutions.projetocursomc.repositories.CidadeRepository;
import br.com.releasesolutions.projetocursomc.repositories.EstadoRepository;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    public CidadeService(CidadeRepository cidadeRepository, EstadoRepository estadoRepository) {
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<Cidade> buscarCidadesPorEstado(Integer estadoId) {

        Estado estado = estadoRepository.findById(estadoId).orElse(null);

        if (estado == null)
            throw new ObjectNotFoundException("Não existe cidades para o Estado informado porque este Estado não existe. Id: "
                    + estadoId + ", Tipo: " + Estado.class.getName());

        return cidadeRepository.findCidades(estadoId);
    }
}

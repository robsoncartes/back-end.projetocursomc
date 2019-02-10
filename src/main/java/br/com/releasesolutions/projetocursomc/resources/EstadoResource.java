package br.com.releasesolutions.projetocursomc.resources;

import br.com.releasesolutions.projetocursomc.domain.Cidade;
import br.com.releasesolutions.projetocursomc.domain.Estado;
import br.com.releasesolutions.projetocursomc.dto.CidadeDTO;
import br.com.releasesolutions.projetocursomc.dto.EstadoDTO;
import br.com.releasesolutions.projetocursomc.services.CidadeService;
import br.com.releasesolutions.projetocursomc.services.EstadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    private EstadoService estadoService;
    private CidadeService cidadeService;

    public EstadoResource(EstadoService estadoService, CidadeService cidadeService) {
        this.estadoService = estadoService;
        this.cidadeService = cidadeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAllEstados() {

        List<Estado> estados = estadoService.buscarTodosEstados();
        List<EstadoDTO> estadoDTOS = estados.stream().map(EstadoDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(estadoDTOS);
    }

    @RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidadesByEstado(@PathVariable Integer estadoId) {

        List<Cidade> cidades = cidadeService.buscarCidadesPorEstado(estadoId);
        List<CidadeDTO> cidadeDTOS = cidades.stream().map(CidadeDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(cidadeDTOS);
    }
}

package br.com.releasesolutions.projetocursomc.services;

// Nota: O Spring Boot na versão 2.X.X é compatível apenas com as versões do JAVA 8 em diante.

import br.com.releasesolutions.projetocursomc.domain.Cliente;
import br.com.releasesolutions.projetocursomc.domain.Cliente;
import br.com.releasesolutions.projetocursomc.dto.ClienteDTO;
import br.com.releasesolutions.projetocursomc.repositories.ClienteRepository;
import br.com.releasesolutions.projetocursomc.services.exceptions.DataIntegrityException;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente buscarClientePorId(Integer id) {

        Cliente cliente = clienteRepository.findById(id).orElse(null);

        if (cliente == null)
            throw new ObjectNotFoundException("Cliente não encontrado. Id: " + id + ", Tipo: " + Cliente.class.getName());

        return cliente;
    }

    public Cliente atualizarCliente(Cliente cliente){
        Cliente newCliente = buscarClientePorId(cliente.getId());
        updateData(newCliente, cliente);

        return clienteRepository.save(newCliente);
    }

    public void deletarClientePorId(Integer id){
        buscarClientePorId(id);
        try{
            clienteRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir um cliente, pois há entidades relacionadas.");
        }
    }

    public List<Cliente> buscarTodosClientes(){

        return clienteRepository.findAll();
    }

    public Page<Cliente> buscarPagina(Integer page, Integer linesPerPage, String orderBy, String direction){

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromClienteDTO(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

    private void updateData(Cliente newCliente, Cliente cliente){
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }
}

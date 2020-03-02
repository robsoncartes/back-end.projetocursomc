package br.com.releasesolutions.projetocursomc.services;

// Nota: O Spring Boot na versão 2.X.X é compatível apenas com as versões do JAVA 8 em diante.

import br.com.releasesolutions.projetocursomc.domain.Cidade;
import br.com.releasesolutions.projetocursomc.domain.Cliente;
import br.com.releasesolutions.projetocursomc.domain.Endereco;
import br.com.releasesolutions.projetocursomc.domain.enums.Perfil;
import br.com.releasesolutions.projetocursomc.domain.enums.TipoCliente;
import br.com.releasesolutions.projetocursomc.dto.ClienteDTO;
import br.com.releasesolutions.projetocursomc.dto.ClienteNewDTO;
import br.com.releasesolutions.projetocursomc.repositories.ClienteRepository;
import br.com.releasesolutions.projetocursomc.repositories.EnderecoRepository;
import br.com.releasesolutions.projetocursomc.security.UserSS;
import br.com.releasesolutions.projetocursomc.services.exceptions.AuthorizationException;
import br.com.releasesolutions.projetocursomc.services.exceptions.DataIntegrityException;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;
    private EnderecoRepository enderecoRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private S3Service s3Service;
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefixo;

    @Value("${img.profile.size}")
    private Integer size;

    public ClienteService(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, BCryptPasswordEncoder passwordEncoder, S3Service s3Service, ImageService imageService) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
        this.imageService = imageService;
    }

    public Cliente buscarClientePorId(Integer id) {

        UserSS userSS = UserService.getUserAuthenticated();

        if (userSS == null || !userSS.hasRole(Perfil.ADMIN) && !id.equals(userSS.getId()))
            throw new AuthorizationException("Acesso negado.");

        Cliente cliente = clienteRepository.findById(id).orElse(null);

        if (cliente == null)
            throw new ObjectNotFoundException("Cliente não encontrado. Id: " + id + ", Tipo: " + Cliente.class.getName());

        return cliente;
    }

    public Cliente buscarClientePorEmail(String email) {

        UserSS userSS = UserService.getUserAuthenticated();

        if (userSS == null || !userSS.hasRole(Perfil.ADMIN) && !email.equals(userSS.getUsername()))
            throw new AuthorizationException("Acesso negado.");

        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente == null) {
            throw new ObjectNotFoundException("Cliente não encontrado. Email: " + email + ", Tipo: " + Cliente.class.getName());
        }

        return cliente;
    }

    @Transactional
    public Cliente inserirCliente(Cliente cliente) {
        cliente.setId(null);
        enderecoRepository.saveAll(cliente.getEnderecos());

        return clienteRepository.save(cliente);
    }

    public Cliente atualizarCliente(Cliente cliente) {
        Cliente newCliente = buscarClientePorId(cliente.getId());
        updateData(newCliente, cliente);

        return clienteRepository.save(newCliente);
    }

    public void deletarClientePorId(Integer id) {
        buscarClientePorId(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir o cliente informado porque há pedidos relacionados.");
        }
    }

    public List<Cliente> buscarTodosClientes() {

        return clienteRepository.findAll();
    }

    public Page<Cliente> buscarPagina(Integer page, Integer linesPerPage, String orderBy, String direction) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromClienteDTO(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
    }

    public Cliente fromClienteNewDTO(ClienteNewDTO clienteNewDTO) {
        Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipoCliente()), passwordEncoder.encode(clienteNewDTO.getSenha()));
        Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);
        Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);

        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteNewDTO.getTelefone1());

        if (clienteNewDTO.getTelefone2() != null)
            cliente.getTelefones().add(clienteNewDTO.getTelefone2());

        if (clienteNewDTO.getTelefone3() != null)
            cliente.getTelefones().add(clienteNewDTO.getTelefone3());


        return cliente;
    }

    private void updateData(Cliente newCliente, Cliente cliente) {
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {

        UserSS userSS = UserService.getUserAuthenticated();

        if (userSS == null)
            throw new AuthorizationException("Acesso negado.");

        BufferedImage pngImage = imageService.getPngImageFromFile(multipartFile);
        BufferedImage cropedImage = imageService.cropSquareImage(pngImage);
        BufferedImage resizeDImage = imageService.resizeImage(cropedImage, size);

        String fileName = prefixo + userSS.getId() + ".png";

        return s3Service.uploadFile(imageService.getInputStream(resizeDImage, "png"), fileName, "image");
    }
}
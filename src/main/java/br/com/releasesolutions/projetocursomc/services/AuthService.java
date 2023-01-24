package br.com.releasesolutions.projetocursomc.services;

import br.com.releasesolutions.projetocursomc.domain.Cliente;
import br.com.releasesolutions.projetocursomc.repositories.ClienteRepository;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class AuthService {

    private ClienteRepository clienteRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailService emailService;

    private Random random = new SecureRandom();

    public AuthService(ClienteRepository clienteRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.clienteRepository = clienteRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    public void sendNewPassword(String email) {

        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente == null)
            throw new ObjectNotFoundException("Email não encontrado");

        String newPassword = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPassword));

        clienteRepository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPassword);

    }

    private String newPassword() {

        char[] vet = new char[10];
        IntStream.range(0, 10).forEach(i -> vet[i] = randomChar());

        return new String(vet);
    }

    private char randomChar() {

        int opt = random.nextInt(3);


        if (opt == 0) // gera um dígito
            return (char) (random.nextInt(10) + 48);

        else if (opt == 1) // gera letra maiúscula
            return (char) (random.nextInt(26) + 65);

        else // gera letra minúscula
            return (char) (random.nextInt(26) + 97);
    }
}

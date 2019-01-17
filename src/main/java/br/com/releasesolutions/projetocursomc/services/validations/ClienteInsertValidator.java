package br.com.releasesolutions.projetocursomc.services.validations;

import br.com.releasesolutions.projetocursomc.domain.Cliente;
import br.com.releasesolutions.projetocursomc.domain.enums.TipoCliente;
import br.com.releasesolutions.projetocursomc.dto.ClienteNewDTO;
import br.com.releasesolutions.projetocursomc.repositories.ClienteRepository;
import br.com.releasesolutions.projetocursomc.resources.exceptions.FieldMessage;
import br.com.releasesolutions.projetocursomc.services.validations.utils.DocumentUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> messages = new ArrayList<>();

        if (objDto.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !DocumentUtil.isValidCPF(objDto.getCpfOuCnpj())) {
            messages.add(new FieldMessage("cpfOuCnpj", "CPF inválido."));
        }

        if (objDto.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !DocumentUtil.isValidCNPJ(objDto.getCpfOuCnpj())) {
            messages.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido."));
        }

        Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());

        if (cliente != null)
            messages.add(new FieldMessage("email", "Email já existente."));

        for (FieldMessage e : messages) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return messages.isEmpty();
    }
}
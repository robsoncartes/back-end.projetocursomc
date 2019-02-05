package br.com.releasesolutions.projetocursomc.services;

import br.com.releasesolutions.projetocursomc.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserSS getUserAuthenticated() {
        // o método pode gerar exceção, retornando null, caso não haja nenhum usuário logado.
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}

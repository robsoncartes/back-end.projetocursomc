package br.com.releasesolutions.projetocursomc.resources;

import br.com.releasesolutions.projetocursomc.dto.EmailDTO;
import br.com.releasesolutions.projetocursomc.security.JWTUtil;
import br.com.releasesolutions.projetocursomc.security.UserSS;
import br.com.releasesolutions.projetocursomc.services.AuthService;
import br.com.releasesolutions.projetocursomc.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private JWTUtil jwtUtil;
    private AuthService authService;

    public AuthResource(JWTUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {

        UserSS userSS = UserService.getUserAuthenticated();
        String token = jwtUtil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/forgot_password", method = RequestMethod.POST)
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody EmailDTO emailDTO) {

        authService.sendNewPassword(emailDTO.getEmail());

        return ResponseEntity.noContent().build();
    }
}

package br.com.releasesolutions.projetocursomc.resources;

import br.com.releasesolutions.projetocursomc.security.JWTUtil;
import br.com.releasesolutions.projetocursomc.security.UserSS;
import br.com.releasesolutions.projetocursomc.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private JWTUtil jwtUtil;

    public AuthResource(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {

        UserSS userSS = UserService.getUserAuthenticated();
        String token = jwtUtil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);

        return ResponseEntity.noContent().build();
    }
}

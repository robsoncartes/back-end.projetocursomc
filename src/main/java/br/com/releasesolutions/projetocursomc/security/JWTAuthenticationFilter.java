package br.com.releasesolutions.projetocursomc.security;

import br.com.releasesolutions.projetocursomc.dto.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            CredenciaisDTO credenciais = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credenciais.getEmail(), credenciais.getSenha(), new ArrayList<>());

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {

        String username = ((UserSS) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        /**
         * Called when an authentication attempt fails.
         *
         * @param request   the request during which the authentication attempt occurred.
         * @param response  the response.
         * @param exception the exception which was thrown to reject the authentication
         */
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();

            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado.\", "
                    + "\"message\": \"Falha na autenticação: Email ou senha inválidos.\", "
                    + "\"path\": \"/login\"}";
        }
    }
}
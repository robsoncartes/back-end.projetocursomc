package br.com.releasesolutions.projetocursomc.config;

import br.com.releasesolutions.projetocursomc.security.JWTAuthenticationFilter;
import br.com.releasesolutions.projetocursomc.security.JWTAuthorizationFilter;
import br.com.releasesolutions.projetocursomc.security.JWTUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * JSON Web Tokens are an open, industry standard RFC 7519 method for representing claims securely between two parties.
 * JWT.IO allows you to decode, verify and generate JWT.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Creates an instance with the default configuration enabled.
     */

    private Environment environment;
    //@Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    private JWTUtil jwtUtil;

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/produtos/**",
            "/categorias/**",
            "/estados/**"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/clientes/**",
            "/auth/forgot_password/**"
    };

    public SecurityConfig(Environment environment, @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.environment = environment;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */


    // Obs.: de modo geral é possível desabilitar proteção a ataques Cross-Site Request Forgery (CSRF) em sistemas stateless.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (Arrays.asList(environment.getActiveProfiles()).contains("test"))
            http.headers().frameOptions().disable();
        
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .anyRequest().authenticated();

        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }


    @Bean
    CorsConfigurationSource getCorsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }


    /*

    Substituir a função acima em casos de problema de CORS no método PUT e DELETE. Problema não ocorreu na versão do SpringBoot 2.0.5

    @Bean
    CorsConfigurationSource getCorsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
    */

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

}

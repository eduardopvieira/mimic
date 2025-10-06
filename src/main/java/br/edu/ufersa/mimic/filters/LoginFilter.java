package br.edu.ufersa.mimic.filters;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.service.auth.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.Collections;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

        public LoginFilter(String url, AuthenticationManager authManager) {
        super((HttpServletRequest req) -> req.getServletPath().equals(url) && "POST".equalsIgnoreCase(req.getMethod()));
        setAuthenticationManager(authManager);
}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        System.out.println("attemptAuthentication do LoginFilter");
        Usuario usu = new ObjectMapper().readValue(req.getInputStream(), Usuario.class);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                usu.getEmail(), usu.getSenha(), Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication auth)
            throws IOException, ServletException {
        AuthenticationService.addToken(res, auth.getName());
        System.out.println("successAuthentication do LoginFilter");
    }
}

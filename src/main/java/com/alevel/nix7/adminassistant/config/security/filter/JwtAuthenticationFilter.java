package com.alevel.nix7.adminassistant.config.security.filter;

import com.alevel.nix7.adminassistant.model.token.TokenRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            ObjectMapper objectMapper
    ) {
        setAuthenticationManager(authenticationManager);
        setUsernameParameter("login");
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        TokenRequest credentials;
        try {
            credentials = objectMapper.readValue(request.getInputStream(), TokenRequest.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        var authToken = new UsernamePasswordAuthenticationToken(
                credentials.login(),
                credentials.password()
        );
        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }
}

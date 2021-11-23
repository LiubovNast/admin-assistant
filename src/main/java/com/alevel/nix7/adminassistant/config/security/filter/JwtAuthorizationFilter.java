package com.alevel.nix7.adminassistant.config.security.filter;

import com.alevel.nix7.adminassistant.config.security.JwtComponent;
import com.alevel.nix7.adminassistant.model.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final Algorithm algorithm;
    private final JwtComponent jwtComponent;
    private static final String AUTH_TOKEN_PREFIX = "Bearer ";

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtComponent jwtComponent) {
        super(authenticationManager);
        this.jwtComponent = jwtComponent;
        algorithm = Algorithm.HMAC256(this.jwtComponent.getSecret());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        var securityContext = SecurityContextHolder.getContext();

        var authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(AUTH_TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String encodedJwt = header.substring(AUTH_TOKEN_PREFIX.length());
        authentication = getAuthentication(encodedJwt);

        securityContext.setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String encodedJwt) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(encodedJwt);
        } catch (Exception e) {
            return null;
        }

        var login = decodedJWT.getSubject();

        var authorities = decodedJWT.getClaim("role").as(GrantedAuthority.class);

        return new UsernamePasswordAuthenticationToken(login, null, Set.of(authorities));
    }
}

package com.alevel.nix7.adminassistant.config.security;

import com.alevel.nix7.adminassistant.exceptions.JwtAuthenticationException;
import com.alevel.nix7.adminassistant.service.impl.AdminServiceImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Date currentTime = new Date();
    private final JwtComponent jwtComponent;
    private final AdminServiceImpl adminService;

    public JwtTokenProvider(JwtComponent jwtComponent, AdminServiceImpl adminService) {
        this.jwtComponent = jwtComponent;
        this.adminService = adminService;
    }

    public String createToken(String login, String role) {
        return JWT.create().withSubject(login)
                .withIssuedAt(currentTime)
                .withExpiresAt(new Date(currentTime.getTime() + jwtComponent.getDuration()))
                .withClaim("role", role)
                .sign(Algorithm.HMAC256(jwtComponent.getSecret()));
    }

    public boolean validatedToken(String token) {
        Date expire = JWT.decode(token).getExpiresAt();
        if (currentTime.before(expire)) {
            throw new JwtAuthenticationException("Expired time of use " + token);
        }
        return true;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = adminService.loadUserByUsername(getLogin(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getLogin(String token) {
        return JWT.decode(token).getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

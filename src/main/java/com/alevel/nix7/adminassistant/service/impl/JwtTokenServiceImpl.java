package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.config.security.JwtComponent;
import com.alevel.nix7.adminassistant.exceptions.JwtAuthenticationException;
import com.alevel.nix7.adminassistant.model.details.AdminDetails;
import com.alevel.nix7.adminassistant.model.token.TokenResponse;
import com.alevel.nix7.adminassistant.service.JwtTokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private final Algorithm algorithm;
    private final JwtComponent jwtComponent;
    private final Date currentTime = new Date();

    public JwtTokenServiceImpl(JwtComponent jwtComponent) {
        this.jwtComponent = jwtComponent;
        algorithm = Algorithm.HMAC256(this.jwtComponent.getSecret());
    }

    @Override
    public TokenResponse getToken(AdminDetails userDetails) {
        return createToken(userDetails);
    }

    @Override
    public void invalidateToken(String token, String login) throws JwtAuthenticationException {
        checkOwner(token, login);
        validatedToken(token);
    }

    private TokenResponse createToken(AdminDetails userDetails) {
        long expIn = currentTime.getTime() + jwtComponent.getDuration();
        String token = JWT.create().withSubject(userDetails.getUsername())
                .withIssuedAt(currentTime)
                .withExpiresAt(new Date(expIn))
                .withClaim("role", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).findFirst().get())
                .sign(algorithm);
        return new TokenResponse(token, expIn);
    }

    private void checkOwner(String token, String login) throws JwtAuthenticationException {
        String loginFromToken = getLogin(token);
        if (!loginFromToken.equals(login)) {
            throw new JwtAuthenticationException("This login" + login + "does not match the token");
        }
    }

    private void validatedToken(String token) {
        Date expire = JWT.decode(token).getExpiresAt();
        if (currentTime.after(expire)) {
            throw new JwtAuthenticationException("Expired time of use " + token);
        }
    }

    private String getLogin(String token) {
        return JWT.decode(token).getSubject();
    }
}

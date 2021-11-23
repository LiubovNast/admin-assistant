package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.exceptions.AssistantException;
import com.alevel.nix7.adminassistant.exceptions.JwtAuthenticationException;
import com.alevel.nix7.adminassistant.model.admin.AdminDetails;
import com.alevel.nix7.adminassistant.model.token.TokenRequest;
import com.alevel.nix7.adminassistant.model.token.TokenResponse;
import com.alevel.nix7.adminassistant.service.JwtTokenService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RootPath.ROOT)
public class AuthenticationController {

    private final JwtTokenService jwtTokenService;

    public AuthenticationController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = TokenRequest.class)))
    TokenResponse login(@AuthenticationPrincipal AdminDetails userDetails) {
        return jwtTokenService.getToken(userDetails);
    }

    @PostMapping(value = "/invalidate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invalidate(@RequestBody String token, @AuthenticationPrincipal String login) {
        try {
            jwtTokenService.invalidateToken(token, login);
        } catch (JwtAuthenticationException e) {
            throw AssistantException.invalidToken(e);
        }
    }
}

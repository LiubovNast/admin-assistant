package com.alevel.nix7.adminassistant.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtComponent {

    @Value("${admin-assistant.security.jwt.access-expire-in}")
    private long duration;

    @Value("${admin-assistant.security.jwt.secret}")
    private String secret;

    public JwtComponent() {
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}

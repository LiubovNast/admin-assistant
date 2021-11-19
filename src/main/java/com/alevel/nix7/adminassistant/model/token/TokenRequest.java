package com.alevel.nix7.adminassistant.model.token;

import com.fasterxml.jackson.annotation.JsonAlias;

public record TokenRequest(@JsonAlias({"login", "phone"})
                           String login,
                           String password) {
}

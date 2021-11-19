package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RootPath.ROOT)
public class AuthenticationController {

    @PostMapping()
    ResponseEntity<?> login(String login, String password){
        return null;
    }
}

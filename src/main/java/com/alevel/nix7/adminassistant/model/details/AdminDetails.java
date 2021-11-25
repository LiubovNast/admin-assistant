package com.alevel.nix7.adminassistant.model.details;

import com.alevel.nix7.adminassistant.model.admin.Admin;
import org.springframework.security.core.userdetails.User;

import java.util.EnumSet;

public class AdminDetails extends User {

    private final Admin admin;

    public AdminDetails(Admin admin) {
        super(admin.getLogin(),
                admin.getPassword(),
                true,
                true,
                true,
                true,
                EnumSet.of(admin.getRole()));
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }
}

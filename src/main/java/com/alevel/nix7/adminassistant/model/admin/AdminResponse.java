package com.alevel.nix7.adminassistant.model.admin;

import com.alevel.nix7.adminassistant.model.Role;

public record AdminResponse(long id,
                            String fullName,
                            String login,
                            String password,
                            Role role
) {
    public static AdminResponse fromAdmin(Admin admin) {
        return new AdminResponse(admin.getId(),
                admin.getFullName(), admin.getLogin(),
                admin.getPassword(), admin.getRole());
    }
}

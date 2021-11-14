package com.alevel.nix7.adminassistant.model.admin;

public record AdminResponse(long id,
                            String fullName,
                            String login,
                            String password
) {
    public static AdminResponse fromAdmin(Admin admin) {
        return new AdminResponse(admin.getId(),
                admin.getFullName(), admin.getLogin(), admin.getPassword());
    }
}

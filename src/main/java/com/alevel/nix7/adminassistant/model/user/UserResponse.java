package com.alevel.nix7.adminassistant.model.user;

public record UserResponse(long id,
                           String fullName,
                           String phone) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getId(),
                user.getFullName(), user.getPhone());
    }
}

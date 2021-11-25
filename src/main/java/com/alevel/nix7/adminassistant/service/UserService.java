package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.user.UserRequest;
import com.alevel.nix7.adminassistant.model.user.UserResponse;

public interface UserService {

    UserResponse create(UserRequest user);

    void delete(Long id);

    UserResponse getByPhone(String phone);

    UserResponse getById(Long id);
}

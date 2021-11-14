package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.user.User;

public interface UserService {

    void create(User user);

    void update(User user);

    void delete(Long id);

    User getByPhone(String phone);

    User getById(Long id);
}

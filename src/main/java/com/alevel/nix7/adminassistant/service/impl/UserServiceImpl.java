package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.user.User;
import com.alevel.nix7.adminassistant.repository.UserRepository;
import com.alevel.nix7.adminassistant.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }
}

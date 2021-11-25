package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.exceptions.AssistantException;
import com.alevel.nix7.adminassistant.model.Role;
import com.alevel.nix7.adminassistant.model.user.User;
import com.alevel.nix7.adminassistant.model.user.UserRequest;
import com.alevel.nix7.adminassistant.model.user.UserResponse;
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
    public UserResponse create(UserRequest user) {
        checkPhone(user.phone());
        return saveUser(user);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw AssistantException.userNotFound(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getByPhone(String phone) {
        return UserResponse.fromUser(userRepository.findByPhone(phone)
                .orElseThrow(() -> AssistantException.userNotFound(phone)));
    }

    @Override
    public UserResponse getById(Long id) {
        return UserResponse.fromUser(userRepository.findById(id)
                .orElseThrow(() -> AssistantException.userNotFound(id)));
    }

    private UserResponse saveUser(UserRequest user) {
        User newUser = new User();
        newUser.setFullName(user.fullName());
        newUser.setPhone(user.phone());
        newUser.setRole(Role.ROLE_USER);
        userRepository.save(newUser);
        return UserResponse.fromUser(newUser);
    }

    private void checkPhone(String phone) {
        if ((phone.startsWith("+380") && phone.length() == 13)
                || (phone.startsWith("0") && phone.length() == 10)) {
            if (!phone.matches("\\+?\\d{10,12}")) {
                throw AssistantException.invalidPhone(phone);
            }
        } else {
            throw AssistantException.invalidPhone(phone);
        }
        if (userRepository.existsByPhone(phone)) {
            throw AssistantException.duplicatePhone(phone);
        }
    }
}

package com.alevel.nix7.adminassistant.model.user;

import com.alevel.nix7.adminassistant.model.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserRequest(@NotNull(message = "name must not be null")
                          String fullName,

                          @NotBlank(message = "password must not be blank")
                          String phone,

                          @NotBlank(message = "role must not be blank")
                          Role role) {

}

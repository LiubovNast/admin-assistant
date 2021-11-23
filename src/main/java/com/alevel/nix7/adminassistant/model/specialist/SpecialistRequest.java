package com.alevel.nix7.adminassistant.model.specialist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record SpecialistRequest(@NotNull(message = "name must not be null")
                                String fullName,
                                @NotBlank(message = "login must not be blank")
                                String login,
                                @NotBlank(message = "password must not be blank")
                                @Size(min = 8, message = "password's length must be at least 8")
                                String password) {
}

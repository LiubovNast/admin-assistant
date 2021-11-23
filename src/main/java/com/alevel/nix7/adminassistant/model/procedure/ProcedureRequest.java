package com.alevel.nix7.adminassistant.model.procedure;

import javax.validation.constraints.NotNull;

public record ProcedureRequest(@NotNull(message = "name must not be null")
                               String name,
                               @NotNull(message = "price must not be null")
                               Long price,
                               @NotNull(message = "duration must not be null")
                               Long duration) {
}

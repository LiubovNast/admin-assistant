package com.alevel.nix7.adminassistant.model.record;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public record RecordRequest(@NotNull(message = "id must not be null")
                            Long specialistId,
                            @NotNull(message = "id must not be null")
                            Long procedureId,
                            @NotBlank(message = "password must not be blank")
                            Timestamp when
) {
}

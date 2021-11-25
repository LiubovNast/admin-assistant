package com.alevel.nix7.adminassistant.model.record;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public record RecordRequest(@NotNull(message = "id must not be null")
                            Long procedureId,
                            @NotNull(message = "time must not be null")
                            Timestamp when
) {
}

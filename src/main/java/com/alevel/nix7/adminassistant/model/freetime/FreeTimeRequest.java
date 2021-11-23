package com.alevel.nix7.adminassistant.model.freetime;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public record FreeTimeRequest(@NotNull(message = "from must not be null")
                              Timestamp from,
                              @NotNull(message = "to must not be null")
                              Timestamp to) {
}

package com.alevel.nix7.adminassistant.model.freetime;

import java.sql.Timestamp;

public record FreeTimeResponse(long id,
                               Timestamp fromTime,
                               Timestamp toTime) {

    public static FreeTimeResponse fromTime(FreeTime freeTime) {
        return new FreeTimeResponse(freeTime.getId(), freeTime.getFrom(), freeTime.getTo());
    }
}

package com.alevel.nix7.adminassistant.model.record;

import java.sql.Timestamp;

public record RecordResponse(Long id,
                             String name,
                             Timestamp when,
                             Long userId) {

    public static RecordResponse fromRecord(Record record) {
        return new RecordResponse(record.getId(),
                record.getProcedure().getName(),
                record.getWhen(), record.getUser().getId());
    }
}

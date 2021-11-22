package com.alevel.nix7.adminassistant.model.record;

import java.sql.Timestamp;

public record RecordResponse(Long id,
                             Long specialistId,
                             Long procedureId,
                             Timestamp when,
                             Long userId) {

    public static RecordResponse fromRecord(Record record) {
        return new RecordResponse(record.getId(),
                record.getSpecialist().getId(), record.getProcedure().getId(),
                record.getWhen(), record.getUser().getId());
    }
}

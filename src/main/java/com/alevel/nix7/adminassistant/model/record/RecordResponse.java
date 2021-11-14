package com.alevel.nix7.adminassistant.model.record;

import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import com.alevel.nix7.adminassistant.model.user.User;

import java.sql.Timestamp;

public record RecordResponse(Long id,
                             Specialist specialist,
                             Procedure procedure,
                             Timestamp when,
                             User user) {

    public static RecordResponse fromRecord(Record record) {
        return new RecordResponse(record.getId(),
                record.getSpecialist(), record.getProcedure(),
                record.getWhen(), record.getUser());
    }
}

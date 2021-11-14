package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.record.Record;

import java.sql.Timestamp;
import java.util.List;

public interface RecordService {

    void create(Record record);

    void delete(Long id);

    List<Record> getRecordsBySpecialist(Long id);

    List<Record> getRecordsByUser(Long id);

    List<Record> getRecordsBetweenTime(Timestamp from, Timestamp to);
}

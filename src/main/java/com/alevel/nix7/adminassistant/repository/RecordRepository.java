package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findRecordsBySpecialist(Long id);

    List<Record> findRecordsByUser(Long id);

    List<Record> findRecordsBySpecialistAndWhenBetween(Long id, Timestamp from, Timestamp to);
}
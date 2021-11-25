package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.record.Record;
import com.alevel.nix7.adminassistant.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findRecordsByUser(User user);
}
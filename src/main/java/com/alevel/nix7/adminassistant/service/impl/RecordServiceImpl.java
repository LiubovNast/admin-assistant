package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.record.Record;
import com.alevel.nix7.adminassistant.repository.RecordRepository;
import com.alevel.nix7.adminassistant.service.RecordService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public void create(Record record) {
        recordRepository.save(record);
    }

    @Override
    public void delete(Long id) {
        recordRepository.deleteById(id);
    }

    @Override
    public List<Record> getRecordsBySpecialist(Long id) {
        return recordRepository.findRecordsBySpecialist(id);
    }

    @Override
    public List<Record> getRecordsByUser(Long id) {
        return recordRepository.findRecordsByUser(id);
    }

    @Override
    public List<Record> getRecordsBetweenTime(Timestamp from, Timestamp to) {
        return recordRepository.findRecordsByWhenBetween(from, to);
    }
}

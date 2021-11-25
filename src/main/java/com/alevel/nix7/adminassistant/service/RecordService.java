package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.record.RecordRequest;
import com.alevel.nix7.adminassistant.model.record.RecordResponse;

import java.util.List;

public interface RecordService {

    RecordResponse create(RecordRequest record, Long userId);

    void delete(Long id);

    List<RecordResponse> getRecordsByUser(Long id);
}

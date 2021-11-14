package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.model.record.Record;
import com.alevel.nix7.adminassistant.model.record.RecordRequest;
import com.alevel.nix7.adminassistant.service.RecordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RootPath.USER)
public class UserController {

    private final RecordService recordService;

    public UserController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void recordForProcedure(@PathVariable long id, @RequestBody @Valid RecordRequest request) {
        recordService.create(new Record());
    }

    @GetMapping("/{id}")
    public List<Record> RecordsForUser(@PathVariable long id) {
        return recordService.getRecordsByUser(id);
    }
}

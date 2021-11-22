package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.model.record.Record;
import com.alevel.nix7.adminassistant.model.record.RecordRequest;
import com.alevel.nix7.adminassistant.model.user.UserRequest;
import com.alevel.nix7.adminassistant.model.user.UserResponse;
import com.alevel.nix7.adminassistant.service.RecordService;
import com.alevel.nix7.adminassistant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RootPath.USER)
public class UserController {

    private final RecordService recordService;
    private final UserService userService;

    public UserController(RecordService recordService, UserService userService) {
        this.recordService = recordService;
        this.userService = userService;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void recordForProcedure(@PathVariable long id, @RequestBody @Valid RecordRequest request) {
        recordService.create(request, id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid UserRequest request) {
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public List<Record> recordsForUser(@PathVariable long id) {
        return recordService.getRecordsByUser(id);
    }

    @GetMapping()
    public UserResponse user(@RequestBody String phone) {
        return UserResponse.fromUser(userService.getByPhone(phone));
    }
}

package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.model.record.RecordRequest;
import com.alevel.nix7.adminassistant.model.record.RecordResponse;
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

    //user
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid UserRequest request) {
        return userService.create(request);
    }

    @GetMapping()
    public UserResponse user(@RequestBody String phone) {
        return userService.getByPhone(phone);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable long id) {
        userService.delete(id);
    }

    //records
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void recordForProcedure(@PathVariable long id, @RequestBody @Valid RecordRequest request) {
        recordService.create(request, id);
    }

    @GetMapping("/{id}")
    public List<RecordResponse> recordsForUser(@PathVariable long id) {
        return recordService.getRecordsByUser(id);
    }

    @DeleteMapping("/records/{idRecord}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecordById(@PathVariable Long idRecord) {
        recordService.delete(idRecord);
    }
}

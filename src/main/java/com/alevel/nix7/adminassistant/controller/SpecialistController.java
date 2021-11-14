package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import com.alevel.nix7.adminassistant.model.record.Record;
import com.alevel.nix7.adminassistant.model.user.User;
import com.alevel.nix7.adminassistant.service.RecordService;
import com.alevel.nix7.adminassistant.service.SpecialistService;
import com.alevel.nix7.adminassistant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(RootPath.WORKER)
public class SpecialistController {

    private final UserService userService;
    private final SpecialistService specialistService;
    private final RecordService recordService;

    public SpecialistController(UserService userService, SpecialistService specialistService, RecordService recordService) {
        this.userService = userService;
        this.specialistService = specialistService;
        this.recordService = recordService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid User request) {
        userService.create(request);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void recordForProcedure(@PathVariable long id, @RequestBody Procedure procedure) {
        recordService.create(new Record());
    }
}

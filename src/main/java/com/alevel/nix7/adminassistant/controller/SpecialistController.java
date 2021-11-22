package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.model.procedure.ProcedureResponse;
import com.alevel.nix7.adminassistant.model.record.RecordResponse;
import com.alevel.nix7.adminassistant.service.ProcedureService;
import com.alevel.nix7.adminassistant.service.RecordService;
import com.alevel.nix7.adminassistant.service.SpecialistService;
import com.alevel.nix7.adminassistant.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RootPath.WORKER)
public class SpecialistController {

    private final UserService userService;
    private final SpecialistService specialistService;
    private final RecordService recordService;
    private final ProcedureService procedureService;

    public SpecialistController(UserService userService, SpecialistService specialistService, RecordService recordService, ProcedureService procedureService) {
        this.userService = userService;
        this.specialistService = specialistService;
        this.recordService = recordService;
        this.procedureService = procedureService;
    }

    @GetMapping("/{id}")
    public List<ProcedureResponse> allProcedures(@PathVariable long id) {
        return procedureService.findAllBySpecialist(id);
    }

   /* @GetMapping("/{id}")
    public List<RecordResponse> allRecords(@PathVariable long id) {
        return recordService.getRecordsBySpecialist(id);
    }*/
}

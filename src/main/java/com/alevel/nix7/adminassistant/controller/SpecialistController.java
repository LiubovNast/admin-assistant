package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeRequest;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeResponse;
import com.alevel.nix7.adminassistant.model.procedure.ProcedureResponse;
import com.alevel.nix7.adminassistant.model.record.RecordResponse;
import com.alevel.nix7.adminassistant.model.specialist.SpecialistRequest;
import com.alevel.nix7.adminassistant.model.specialist.SpecialistResponse;
import com.alevel.nix7.adminassistant.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RootPath.WORKER)
public class SpecialistController {

    private final UserService userService;
    private final SpecialistService specialistService;
    private final RecordService recordService;
    private final ProcedureService procedureService;
    private final FreeTimeService freeTimeService;

    public SpecialistController(UserService userService, SpecialistService specialistService,
                                RecordService recordService, ProcedureService procedureService,
                                FreeTimeService freeTimeService) {
        this.userService = userService;
        this.specialistService = specialistService;
        this.recordService = recordService;
        this.procedureService = procedureService;
        this.freeTimeService = freeTimeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpecialistResponse registerWorker(@RequestBody @Valid SpecialistRequest request) {
        return specialistService.create(request);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public FreeTimeResponse addFreeTime(@PathVariable long id, @RequestBody @Valid FreeTimeRequest request) {
        return freeTimeService.create(request, id);
    }

    @GetMapping
    public List<SpecialistResponse> listWorkers() {
        return specialistService.getAllSpecialist();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialist(@PathVariable long id) {
        specialistService.delete(id);
    }

    @GetMapping("/procedures/{id}")
    public List<ProcedureResponse> allProcedures(@PathVariable long id) {
        return procedureService.findAllBySpecialist(id);
    }

    @GetMapping("/records/{id}")
    public List<RecordResponse> allRecords(@PathVariable long id) {
        return recordService.getRecordsBySpecialist(id);
    }

    @GetMapping("/work_time/{id}")
    public List<FreeTimeResponse> AllWorkTime(@PathVariable long id) {
        return freeTimeService.getAllFreeTimeSpecialist(id);
    }
}

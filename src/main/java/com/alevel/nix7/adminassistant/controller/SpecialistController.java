package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeRequest;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeResponse;
import com.alevel.nix7.adminassistant.model.procedure.ProcedureRequest;
import com.alevel.nix7.adminassistant.model.procedure.ProcedureResponse;
import com.alevel.nix7.adminassistant.model.specialist.SpecialistRequest;
import com.alevel.nix7.adminassistant.model.specialist.SpecialistResponse;
import com.alevel.nix7.adminassistant.service.FreeTimeService;
import com.alevel.nix7.adminassistant.service.ProcedureService;
import com.alevel.nix7.adminassistant.service.SpecialistService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RootPath.WORKER)
public class SpecialistController {

    private final SpecialistService specialistService;
    private final ProcedureService procedureService;
    private final FreeTimeService freeTimeService;

    public SpecialistController(SpecialistService specialistService,
                                ProcedureService procedureService,
                                FreeTimeService freeTimeService) {
        this.specialistService = specialistService;
        this.procedureService = procedureService;
        this.freeTimeService = freeTimeService;
    }

    //Specialist
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpecialistResponse registerWorker(@RequestBody @Valid SpecialistRequest request) {
        return specialistService.create(request);
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

    //Procedure
    @PostMapping("/procedures/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProcedureResponse addProcedure(@RequestBody @Valid ProcedureRequest request, @PathVariable long id) {
        return procedureService.create(request, id);
    }

    @GetMapping("/procedures/{id}")
    public List<ProcedureResponse> allProcedures(@PathVariable long id) {
        return procedureService.findAllBySpecialist(id);
    }

    //FreeTime
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public FreeTimeResponse addFreeTime(@PathVariable long id, @RequestBody @Valid FreeTimeRequest request) {
        return freeTimeService.create(request, id);
    }

    @GetMapping("/work_time/{id}")
    public List<FreeTimeResponse> AllWorkTime(@PathVariable long id) {
        return freeTimeService.getAllFreeTimeSpecialist(id);
    }
}

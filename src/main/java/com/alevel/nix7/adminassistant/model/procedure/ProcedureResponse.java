package com.alevel.nix7.adminassistant.model.procedure;

public record ProcedureResponse(Long id,
                                String name,
                                Long price,
                                Long duration) {

    public static ProcedureResponse fromProcedure(Procedure procedure) {
        return new ProcedureResponse(procedure.getId(),
                procedure.getName(), procedure.getPrice(),
                procedure.getDuration());
    }
}

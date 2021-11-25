package com.alevel.nix7.adminassistant.model.procedure;

public record ProcedureResponse(Long id,
                                String name,
                                Long price,
                                Long duration,
                                Long idSpecialist) {

    private static final long MINUTE = 60_000;

    public static ProcedureResponse fromProcedure(Procedure procedure) {
        return new ProcedureResponse(procedure.getId(),
                procedure.getName(), procedure.getPrice(),
                getInMinute(procedure.getDuration()),
                procedure.getSpecialist().getId());
    }

    private static long getInMinute(long duration) {
        return duration / MINUTE;
    }
}

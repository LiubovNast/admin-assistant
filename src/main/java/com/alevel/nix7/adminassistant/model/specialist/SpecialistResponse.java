package com.alevel.nix7.adminassistant.model.specialist;

public record SpecialistResponse(long id,
                                 String fullName,
                                 String login,
                                 String password
) {

    public static SpecialistResponse fromWorker(Specialist specialist) {
        return new SpecialistResponse(specialist.getId(),
                specialist.getFullName(), specialist.getLogin(), specialist.getPassword());
    }
}

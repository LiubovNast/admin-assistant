package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.freetime.FreeTime;
import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface FreeTimeRepository extends JpaRepository<FreeTime, Long> {

    List<FreeTime> findAllBySpecialist (Specialist specialist);

    FreeTime findBySpecialistAndFromBeforeAndToAfter(Specialist specialist, Timestamp from, Timestamp to);
}

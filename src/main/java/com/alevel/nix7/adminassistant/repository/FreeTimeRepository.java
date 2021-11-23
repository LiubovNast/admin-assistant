package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.freetime.FreeTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeTimeRepository extends JpaRepository<FreeTime, Long> {

    List<FreeTime> findFreeTimesBySpecialistIn(Long id);
}

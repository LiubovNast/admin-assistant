package com.alevel.nix7.adminassistant.model.freetime;

import com.alevel.nix7.adminassistant.model.specialist.Specialist;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "free_time")
public class FreeTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;

    @Column(name = "time_from", nullable = false)
    private Timestamp from;

    @Column(name = "time_to", nullable = false)
    private Timestamp to;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Timestamp getFrom() {
        return from;
    }

    public void setFrom(Timestamp from) {
        this.from = from;
    }

    public Timestamp getTo() {
        return to;
    }

    public void setTo(Timestamp to) {
        this.to = to;
    }
}

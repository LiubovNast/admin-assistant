package com.alevel.nix7.adminassistant.model.procedure;

import com.alevel.nix7.adminassistant.model.specialist.Specialist;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "procedures")
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long duration;

    @ManyToMany
    @JoinTable(name = "what_and_who",
            joinColumns = @JoinColumn(name = "procedure_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "specialist_id", referencedColumnName = "id")
    )
    private Set<Specialist> specialists = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Set<Specialist> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(Set<Specialist> specialists) {
        this.specialists = specialists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

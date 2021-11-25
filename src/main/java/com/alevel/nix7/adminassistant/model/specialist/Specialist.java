package com.alevel.nix7.adminassistant.model.specialist;

import com.alevel.nix7.adminassistant.model.Role;
import com.alevel.nix7.adminassistant.model.freetime.FreeTime;
import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import com.alevel.nix7.adminassistant.model.record.Record;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "specialists")
public class Specialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String fullName;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "specialist")
    private List<Procedure> procedures;

    @OneToMany(mappedBy = "specialist")
    private List<FreeTime> freeTimeList;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role = Role.ROLE_WORKER;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public List<FreeTime> getFreeTimeList() {
        return freeTimeList;
    }

    public void setFreeTimeList(List<FreeTime> freeTimeList) {
        this.freeTimeList = freeTimeList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

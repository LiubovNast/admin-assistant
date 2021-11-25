package com.alevel.nix7.adminassistant.model.record;

import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import com.alevel.nix7.adminassistant.model.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    private Procedure procedure;

    @Column(name = "time_when", nullable = false)
    private Timestamp when;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public Timestamp getWhen() {
        return when;
    }

    public void setWhen(Timestamp when) {
        this.when = when;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

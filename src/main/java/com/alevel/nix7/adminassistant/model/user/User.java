package com.alevel.nix7.adminassistant.model.user;

import com.alevel.nix7.adminassistant.model.Role;
import com.alevel.nix7.adminassistant.model.record.Record;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String fullName;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Record> records;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role = Role.ROLE_USER;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

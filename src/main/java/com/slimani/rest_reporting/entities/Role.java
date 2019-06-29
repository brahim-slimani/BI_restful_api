package com.slimani.rest_reporting.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    //@Enumerated(EnumType.STRING)
    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Role(@NotNull String role, List<User> users) {
        this.role = role;
        this.users = users;
    }

    public Role(@NotNull String role) {
        this.role = role;
    }

    public Role() {
    }
}

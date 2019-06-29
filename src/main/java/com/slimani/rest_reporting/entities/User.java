package com.slimani.rest_reporting.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String username;

    @Column(nullable = false)
    @NotNull
    private String password;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "id")})
    private List<Role> roles;


    @OneToMany(mappedBy = "userR", cascade = CascadeType.ALL)
    private Set<Report> reports;

    @OneToMany(mappedBy = "userD", cascade = CascadeType.ALL)
    private Set<Dashboard> dashboards;



    public User(@NotNull String username, @NotNull String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(@NotNull String username, @NotNull String password, List<Role> roles, Set<Report> reports) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.reports = reports;
    }

    public User(@NotNull String username, @NotNull String password, List<Role> roles, Set<Report> reports, Set<Dashboard> dashboards) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.reports = reports;
        this.dashboards = dashboards;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Set<Dashboard> getDashboards() {
        return dashboards;
    }

    public void setDashboards(Set<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }
}

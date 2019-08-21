package com.slimani.rest_reporting.entities;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String title;

    @Column
    @NotNull
    private String context;

    @Column
    @NotNull
    private String type;

    @Column
    @NotNull
    private String columns;

    @Column
    @NotNull
    private String rows;

    @ManyToMany(mappedBy = "reports", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Dashboard> dashboards;

    @ManyToOne
    @JoinColumn(name = "report_user_id")
    private User userR;


    public Report() {
    }

    public Report(@NotNull String title, @NotNull String context, @NotNull String type, User user) {
        this.title = title;
        this.context = context;
        this.type = type;
        this.userR = user;
    }

    public Report(@NotNull String title, @NotNull String context, @NotNull String type, List<Dashboard> dashboards, User user) {
        this.title = title;
        this.context = context;
        this.type = type;
        this.dashboards = dashboards;
        this.userR = user;
    }

    public Report(@NotNull String title, @NotNull String context, @NotNull String type) {
        this.title = title;
        this.context = context;
        this.type = type;
    }

    public Report(@NotNull String title, @NotNull String context, @NotNull String type, @NotNull String columns, @NotNull String rows, List<Dashboard> dashboards, User userR) {
        this.title = title;
        this.context = context;
        this.type = type;
        this.columns = columns;
        this.rows = rows;
        this.dashboards = dashboards;
        this.userR = userR;
    }

    public Report(@NotNull String title, @NotNull String context, @NotNull String type, @NotNull String columns, @NotNull String rows) {
        this.title = title;
        this.context = context;
        this.type = type;
        this.columns = columns;
        this.rows = rows;
    }

    public Report(@NotNull String title, @NotNull String context, @NotNull String type, @NotNull String columns, @NotNull String rows, User userR) {
        this.title = title;
        this.context = context;
        this.type = type;
        this.columns = columns;
        this.rows = rows;
        this.userR = userR;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Dashboard> getDashboards() {
        return dashboards;
    }

    public void setDashboards(List<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }

    public User getUserR() {
        return userR;
    }

    public void setUserR(User userR) {
        this.userR = userR;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }
}

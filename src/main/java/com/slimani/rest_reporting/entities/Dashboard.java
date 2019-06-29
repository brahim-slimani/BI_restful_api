package com.slimani.rest_reporting.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Dashborads")
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String title;

    @ManyToMany(targetEntity = Report.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "dashboards_reports",
            joinColumns = {@JoinColumn(name = "DASHBOARD_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "REPORT_ID", referencedColumnName = "id")})
    private List<Report> reports;

    @ManyToOne
    @JoinColumn(name = "dashboard_user_id")
    private User userD;


    public Dashboard() {
    }

    public Dashboard(@NotNull String title, List<Report> reports, User userD) {
        this.title = title;
        this.reports = reports;
        this.userD = userD;
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

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public User getUserD() {
        return userD;
    }

    public void setUserD(User userD) {
        this.userD = userD;
    }
}

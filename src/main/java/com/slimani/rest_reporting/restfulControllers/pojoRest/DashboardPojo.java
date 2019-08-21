package com.slimani.rest_reporting.restfulControllers.pojoRest;


import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DashboardPojo implements Serializable {
    private Long id;
    private String title;
    private String username;
    private JSONArray reportsjsonarray = new JSONArray();
    private List<ReportPojo> reports = new ArrayList<>();
    private String reportsString;


    public DashboardPojo() {
    }

    public DashboardPojo(Long id, String title, String username, JSONArray reportsjsonarray, List<ReportPojo> reports) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.reportsjsonarray = reportsjsonarray;
        this.reports = reports;
    }

    public DashboardPojo(String title, JSONArray reportsjsonarray, String username) {
        this.title = title;
        this.username = username;
        this.reportsjsonarray = reportsjsonarray;
    }

    public DashboardPojo(Long id, String title, String username) {
        this.id = id;
        this.title = title;
        this.username = username;
    }

    public DashboardPojo(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public DashboardPojo(Long id, String title, String username, JSONArray reportsjsonarray) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.reportsjsonarray = reportsjsonarray;
    }


    public DashboardPojo(String title, JSONArray reportsjsonarray) {
        this.title = title;
        this.reportsjsonarray = reportsjsonarray;
    }

    public DashboardPojo(String title) {
        this.title = title;

    }


    public DashboardPojo(String title, String username) {
        this.title = title;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public JSONArray getReportsjsonarray() {
        return reportsjsonarray;
    }

    public void setReportsjsonarray(JSONArray reportsjsonarray) {
        this.reportsjsonarray = reportsjsonarray;
    }

    public List<ReportPojo> getReports() {
        return reports;
    }

    public void setReports(List<ReportPojo> reports) {
        this.reports = reports;
    }

    public String getReportsString() {
        return reportsString;
    }

    public void setReportsString(String reportsString) {
        this.reportsString = reportsString;
    }
}

package com.slimani.rest_reporting.restfulControllers.pojoRest;

public class ReportPojo {

    private Long id;
    private String title;
    private String context;
    private String type;
    private String columns;
    private String rows;
    private String username;



    public ReportPojo(String title, String context, String type, String columns, String rows, String username) {
        this.title = title;
        this.context = context;
        this.type = type;
        this.columns = columns;
        this.rows = rows;
        this.username = username;
    }
/*
    public ReportPojo(Long id, String title, String context) {
        this.id = id;
        this.title = title;
        this.context = context;
    }

    public ReportPojo(String title, String context) {
        this.title = title;
        this.context = context;
    }*/

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

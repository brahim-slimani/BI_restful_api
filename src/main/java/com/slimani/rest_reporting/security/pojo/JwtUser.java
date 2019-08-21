package com.slimani.rest_reporting.security.pojo;

public class JwtUser {

    private long id;
    private String userName;
    private String role;
    private String password;
    private String email;

    public JwtUser() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JwtUser(long id, String userName, String role, String password) {
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.password = password;
    }


    public JwtUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public JwtUser(String userName, String password, String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;

    }

    public JwtUser(String userName, String role, String password, String email) {
        this.userName = userName;
        this.role = role;
        this.password = password;
        this.email = email;
    }
}

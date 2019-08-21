package com.slimani.rest_reporting.restfulControllers.pojoRest;

public class UserPojo {

    private String username;
    private String password;
    private String role;
    private String mail;
    private String contact;

    public UserPojo() {
    }

    public UserPojo(String username, String password, String role, String mail, String contact) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.mail = mail;
        this.contact = contact;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

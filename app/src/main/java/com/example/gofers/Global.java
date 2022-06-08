package com.example.gofers;

public class Global {

    private String adhar;
    private String Licence;
    private String RC;
    private String firstname;
    private String lastname;
    private String email;
    private String isVerified;



    public Global() {
    }

    public Global(String adhar, String licence, String RC, String firstname, String lastname, String email, String isVerified) {
        this.adhar = adhar;
        Licence = licence;
        this.RC = RC;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.isVerified = isVerified;
    }

    public String getAdhar() {
        return adhar;
    }

    public void setAdhar(String adhar) {
        this.adhar = adhar;
    }

    public String getLicence() {
        return Licence;
    }

    public void setLicence(String licence) {
        Licence = licence;
    }

    public String getRC() {
        return RC;
    }

    public void setRC(String RC) {
        this.RC = RC;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }
}

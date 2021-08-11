package com.example.moviememoir.model;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

public class Credential {
    private Integer CId;
    private String username;
    private String passwordhs;
    private String signupdate;
    private Person PId;

    public Credential(){
        this.CId = 4;
        this.username="Unknow";
        this.passwordhs="Unknow";
        this.signupdate="Unknow";
        this.PId= new Person();

    }

    public Credential(Integer cId, String username, String passwordhs, String signupdate, Person pId) {
        this.CId = cId;
        this.username = username;
        this.passwordhs = passwordhs;
        this.signupdate = signupdate;
        this.PId = pId;
    }


    public Integer getcId() {
        return CId;
    }

    public void setcId(Integer cId) {
        this.CId = cId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhs() {
        return passwordhs;
    }

    public void setPasswordhs(String passwordhs) {
        this.passwordhs = passwordhs;
    }

    public String getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(String signupdate) {
        this.signupdate = signupdate;
    }

    public Person getpId() {
        return PId;
    }

    public void setpId(Person pId) {
        this.PId = pId;
    }


}

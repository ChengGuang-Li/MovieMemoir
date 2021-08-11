package com.example.moviememoir.model;

public class Person {

    private Integer PId;
    private String firstname;
    private String surname;
    private String gender;
    private String dob;
    private int SNumber;
    private String SName;
    private String stateOR;
    private int postcode;

    public Person ()
    {
         PId = 1;
         firstname = "Unknow";
         surname = "Unknow";
         gender = "Unknow";
         dob = "Unknow";
         SNumber = 1314;
         SName = "Unknow";
         stateOR = "Unknow";
         postcode = 1314;
    }

    public Person (Integer pId, String firstname, String surname, String gender, String dob, int sNumber, String sName, String stateOR, int postcode) {
        this.PId = pId;
        this.firstname = firstname;
        this.surname = surname;
        this.gender = gender;
        this.dob = dob;
        this.SNumber = sNumber;
        this.SName = sName;
        this.stateOR = stateOR;
        this.postcode = postcode;
    }

    public Integer getpId() {
        return PId;
    }

    public void setpId(Integer pId) {
        this.PId = pId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getsNumber() {
        return SNumber;
    }

    public void setsNumber(int sNumber) {
        this.SNumber = sNumber;
    }

    public String getsName() {
        return SName;
    }

    public void setsName(String sName) {
        this.SName = sName;
    }

    public String getStateOR() {
        return stateOR;
    }

    public void setStateOR(String stateOR) {
        this.stateOR = stateOR;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }


}

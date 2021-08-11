package com.example.moviememoir.model;

public class Cinema {
    private Integer CNo;
    private String CName;
    private long location;

    public Cinema(){
        this.CNo=0;
        this.CName="Unknow";
        this.location=1;
    }

    public Cinema(Integer cNo, String cName, long location) {
        this.CNo = cNo;
        this.CName = cName;
        this.location = location;
    }


    public Integer getcNo() {
        return CNo;
    }

    public void setcNo(Integer cNo) {
        this.CNo = cNo;
    }

    public String getcName() {
        return CName;
    }

    public void setcName(String cName) {
        this.CName = cName;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

}

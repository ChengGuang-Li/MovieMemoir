package com.example.moviememoir.model;

import com.example.moviememoir.Database.Movie;

import java.math.BigDecimal;
import java.util.Date;

public class Memoir {

    private Integer MNo;
    private String MName;
    private String mrlsdate;
    private String wcdatetime;
    private String comment;
    private BigDecimal RScore;
    private Cinema CNo;
    private Person PId;
    private String omdbid;
    private String image;

    public Memoir(){
        this.MNo = 0;
        this.MName = "Unknow";
        this.mrlsdate = "Unknow";
        this.wcdatetime = "Unknow";
        this.comment = "Unknow";
        this.RScore = new BigDecimal(1.22);
        this.CNo= new Cinema();
        this.PId = new Person();
        this.omdbid="Unknow";
        this.image="Unknow";
    }

    public Memoir(Integer mNo, String mName, String mrlsdate, String wcdatetime, String comment, BigDecimal rScore, Cinema cNo, Person pId,String omdbid,String image) {
        this.MNo = mNo;
        this.MName = mName;
        this.mrlsdate = mrlsdate;
        this.wcdatetime = wcdatetime;
        this.comment = comment;
        this.RScore = rScore;
        this.CNo = cNo;
        this.PId = pId;
        this.omdbid=omdbid;
        this.image=image;
    }


    public Integer getmNo() {
        return MNo;
    }

    public void setmNo(Integer mNo) {
        this.MNo = mNo;
    }

    public String getmName() {
        return MName;
    }

    public void setmName(String mName) {
        this.MName = mName;
    }

    public String getMrlsdate() {
        return mrlsdate;
    }

    public void setMrlsdate(String mrlsdate) {
        this.mrlsdate = mrlsdate;
    }

    public String getWcdatetime() {
        return wcdatetime;
    }

    public void setWcdatetime(String wcdatetime) {
        this.wcdatetime = wcdatetime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getrScore() {
        return RScore;
    }

    public void setrScore(BigDecimal rScore) {
        this.RScore = rScore;
    }

    public Cinema getcNo() {
        return CNo;
    }

    public void setcNo(Cinema cNo) {
        this.CNo = cNo;
    }

    public Person getpId() {
        return PId;
    }

    public void setpId(Person pId) {
        this.PId = pId;
    }

    public String getOmdbid() {
        return omdbid;
    }

    public void setOmdbid(String omdbid) {
        this.omdbid = omdbid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

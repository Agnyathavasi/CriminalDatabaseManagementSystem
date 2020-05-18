package com.example.kgf;

public class New_criminals {

    private String criminal_Name,case_Number,punishment,captured_by,height,crimImgId,crimImgurl,sex,cdate;

    New_criminals(String criminal_Name, String case_Number, String punishment, String captured_by, String height, String crimImgId, String crimImgurl, String sex, String cdate) {
        this.criminal_Name = criminal_Name;
        this.case_Number = case_Number;
        this.punishment = punishment;
        this.captured_by = captured_by;
        this.height = height;
        this.crimImgId = crimImgId;
        this.crimImgurl = crimImgurl;
        this.sex = sex;
        this.cdate = cdate;
    }

    public New_criminals() {
    }

    public String getCriminal_Name() {
        return criminal_Name;
    }

    public String getCase_Number() {
        return case_Number;
    }

    public String getPunishment() {
        return punishment;
    }

    public String getCaptured_by() {
        return captured_by;
    }

    public String getHeight() {
        return height;
    }

    public String getCrimImgId() {
        return crimImgId;
    }

    public String getCrimImgurl() {
        return crimImgurl;
    }

    public String getSex() {
        return sex;
    }

    public String getCdate() {
        return cdate;
    }
}



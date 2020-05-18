package com.example.kgf;

class Addcourts {

    public String courtName,caseid,date;

    public Addcourts() {
    }

    Addcourts(String courtName, String caseid, String date) {
        this.courtName = courtName;
        this.caseid = caseid;
        this.date = date;
    }

    String getCourtName() {
        return courtName;
    }

    String getCaseid() {
        return caseid;
    }

    String getDate() {
        return date;
    }
}

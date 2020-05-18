package com.example.kgf;

public class PoliceDB {

    private  String offid,offname,offarea,offduty,offmobile,offimgid,offdob,offimgurl;

    public PoliceDB() {
    }

    PoliceDB(String offid, String offname, String offarea, String offduty, String offmobile, String offimgid, String offdob, String offimgurl) {
        this.offid = offid;
        this.offname = offname;
        this.offarea = offarea;
        this.offduty = offduty;
        this.offmobile = offmobile;
        this.offimgid = offimgid;
        this.offdob = offdob;
        this.offimgurl = offimgurl;
    }

    public String getOffid() {
        return offid;
    }

    public String getOffname() {
        return offname;
    }

    public String getOffarea() {
        return offarea;
    }

    public String getOffduty() {
        return offduty;
    }

    public String getOffmobile() {
        return offmobile;
    }

    public String getOffimgid() {
        return offimgid;
    }

    public String getOffdob() {
        return offdob;
    }

    public String getOffimgurl() {
        return offimgurl;
    }
}

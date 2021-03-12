package in.baselinesoft.mahindraro;

/**
 * Created by Pratik on 12/03/2018.
 */

public class Employee {
    private String tmid;
    private String tmloc;
    private String tmstart;
    private String tmend;
    private String tmone;
    private String tmtwo;
    private String tmprocess;
    private String tmstatus;

    public Employee(String tmid, String tmloc, String tmstart, String tmend, String tmone, String tmtwo, String tmprocess, String tmstatus) {
        this.tmid = tmid;
        this.tmloc = tmloc;
        this.tmstart = tmstart;
        this.tmend = tmend;
        this.tmone = tmone;
        this.tmtwo = tmtwo;
        this.tmprocess = tmprocess;
        this.tmstatus = tmstatus;
    }

    public String getTmid() {
        return tmid;
    }


    public String getTmloc() {
        return tmloc;
    }


    public String getTmstart() {
        return tmstart;
    }


    public String getTmend() {
        return tmend;
    }


    public String getTmone() {
        return tmone;
    }


    public String getTmtwo() {
        return tmtwo;
    }


    public String getTmprocess() {
        return tmprocess;
    }


    public String getTmstatus() {
        return tmstatus;
    }

}

package com.zq.mywork.model;

public class Submit {
    private String w_id;
    private String s_id;
    private String subdate;
    private String enddate;
    private String pjdate;
    private String ps;
    private String pingjia;
    private int w_score;
    private String isdo;
    private String ischeck;
    private String status;

    public String getW_id() {
        return w_id;
    }

    public void setW_id(String w_id) {
        this.w_id = w_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getSubdate() {
        return subdate.substring(0,16);
    }

    public void setSubdate(String subdate) {
        this.subdate = subdate;
    }

    public String getEnddate() {
        return enddate.substring(0,16);
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getPjdate() {
        return pjdate;
    }

    public void setPjdate(String pjdate) {
        this.pjdate = pjdate;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getPingjia() {
        return pingjia;
    }

    public void setPingjia(String pingjia) {
        this.pingjia = pingjia;
    }

    public int getW_score() {
        return w_score;
    }

    public void setW_score(int w_score) {
        this.w_score = w_score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsdo() {
        return isdo;
    }

    public void setIsdo(String isdo) {
        this.isdo = isdo;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }
}

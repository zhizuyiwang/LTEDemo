package com.zed.hotsport.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/9.
 */

public class BlackBean implements Serializable{
    private String name;
    private String imsi;
    private String imei;
    private String esn;
    private String mac;
    private int flag; // 0表示黑名单，1表示白名单
    // 对应于指定id的案件
    public int c_id;

    public BlackBean(String name, String imsi, String imei, String esn, String mac, int flag) {
        this.name = name;
        this.imsi = imsi;
        this.imei = imei;
        this.esn = esn;
        this.mac = mac;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEsn() {
        return esn;
    }

    public void setEsn(String esn) {
        this.esn = esn;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
}

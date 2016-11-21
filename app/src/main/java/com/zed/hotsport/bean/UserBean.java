package com.zed.hotsport.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/22.
 */
public class UserBean implements Parcelable {
    //上报的IMSI
    private String imsi;
    //上报的IMEI
    private String imei;
    //上报的时间
    private Date timer;
    //上报的运营商
    private String oprerator;
    //黑名单名称
    private String blackName;
    //上报的TSM
    private String TSM;
    //上报的lac
    private String lac;
    //场强值
    private String power;
    //归属地
    private String place;
    //手机类型
    private String phoneType;

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

    public Date getTimer() {
        return timer;
    }

    public void setTimer(Date timer) {
        this.timer = timer;
    }

    public String getBlackName() {
        return blackName;
    }

    public void setBlackName(String blackName) {
        this.blackName = blackName;
    }

    public String getTSM() {
        return TSM;
    }

    public void setTSM(String TSM) {
        this.TSM = TSM;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getOprerator() {
        return oprerator;
    }

    public void setOprerator(String oprerator) {
        this.oprerator = oprerator;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imsi);
        dest.writeString(this.imei);
        dest.writeLong(this.timer != null ? this.timer.getTime() : -1);
        dest.writeString(this.oprerator);
        dest.writeString(this.blackName);
        dest.writeString(this.TSM);
        dest.writeString(this.lac);
        dest.writeString(this.power);
        dest.writeString(this.place);
        dest.writeString(this.phoneType);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.imsi = in.readString();
        this.imei = in.readString();
        long tmpTimer = in.readLong();
        this.timer = tmpTimer == -1 ? null : new Date(tmpTimer);
        this.oprerator = in.readString();
        this.blackName = in.readString();
        this.TSM = in.readString();
        this.lac = in.readString();
        this.power = in.readString();
        this.place = in.readString();
        this.phoneType = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}

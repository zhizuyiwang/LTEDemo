package com.zed.hotsport.bean;

/**
 * 小区的参数
 * Created by Administrator on 2016/11/17.
 */
public class CellBean {
    private String frequency;//频点
    private String scramble;//扰码
    private int power_down;//功率衰减
    private String power_rand;//功率等级
    private String tac;//位置区
    private String lac_period;//lac变更周期
    private String CDX;//重定向
    private String band;//频段
    private String frequency1;//频点
    private String dianPing;//最小接入电平

    public String getPower_rand() {
        return power_rand;
    }

    public void setPower_rand(String power_rand) {
        this.power_rand = power_rand;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getScramble() {
        return scramble;
    }

    public void setScramble(String scramble) {
        this.scramble = scramble;
    }

    public int getPower_down() {
        return power_down;
    }

    public void setPower_down(int power) {
        this.power_down = power;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getLac_period() {
        return lac_period;
    }

    public void setLac_period(String lac_period) {
        this.lac_period = lac_period;
    }

    public String getCDX() {
        return CDX;
    }

    public void setCDX(String CDX) {
        this.CDX = CDX;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getFrequency1() {
        return frequency1;
    }

    public void setFrequency1(String frequency1) {
        this.frequency1 = frequency1;
    }

    public String getDianPing() {
        return dianPing;
    }

    public void setDianPing(String dianPing) {
        this.dianPing = dianPing;
    }
}

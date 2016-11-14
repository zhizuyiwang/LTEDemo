package com.zed.hotsport.TCP;

/**
 * Created by Administrator on 2016/9/20.
 */
public class TCPConfig {
    //监听端口号
    public static final int SOCKET_CONFIG_PORT_3G = 60001;

    //通信初始化请求类型
    public static final short MSG_START_REQ = 1001;

    //通信初始化回应类型
    public static final short MSG_START_ACK = 1002;

    //心跳请求类型
    public static final short MSG_HEARTBEAT = 2;

    //心跳回应
    public static final short MSG_HEARTBAT_ACK = 3;

    //小区参数配置类型
    public static final short MSG_SET_CELL_RFPARA_REQ = 1011;

    //小区参数配置响应类型
    public static final short MSG_SET_CELL_RFPARA_ACK = 1012;

    //小区参数查询类型
    public static final short MSG_GET_CELL_PARA_REQ = 1013;

    //小区参数查询响应类型
    public static final short MSG_GET_CELL_PARA_ACK = 1014;

    //设备重启请求类型
    public static final short MSG_RESET_REQ = 1311;

    //设备重启响应类型
    public static final short MSG_RESET_ACK = 1312;

    //数据上报类型
    public static final short MSG_USERID_REPORT = 1111;

    //设备状态上报类型
    public static final short MSG_STATE_REPORT = 1;

    //设备告警上报类型
    public static final short MSG_WARNING_IND = 2;

    //开关功放操作类型
    public static final short MSG_PA_ONOFF_REQ = 3;

    //开放功放响应类型
    public static final short MSG_PA_ONOFF_ACK = 4;

    //开关侦码操作类型
    public static final short MSG_CATCHING_ONOFF_REQ = 5;

    //开关侦码响应类型
    public static final short MSG_CATCHING_ONOFF_ACK = 6;

    //版本查询请求类型
    public static final short MSG_GET_VERSION = 7;

    //版本查询回应类型
    public static final short MSG_GET_VERSION_ACK = 8;

    //设置/同步设备时间
    public static final short MSG_SET_DATETIME = 9;

    //设置/同步设备时间响应
    public static final short MSG_SET_DATETIME_ACK = 10;

    //获取设备时间请求
    public static final short MSG_GET_DATETIME = 11;

    //获取设备时间响应
    public static final  short MSG_GET_DATETIME_ACK = 12;


}

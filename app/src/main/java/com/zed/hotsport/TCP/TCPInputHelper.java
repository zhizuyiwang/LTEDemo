package com.zed.hotsport.TCP;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zed.hotsport.base.BaseApplication;
import com.zed.hotsport.bean.UserBean;
import com.zed.hotsport.utils.ConvertCodeUtility;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Date;

/**
 * Socket读取工具类
 * Created by Administrator on 2016/9/19.
 */
public class TCPInputHelper {

    private final String TAG = "SocketInputHelper"; // log标签
    private static TCPInputHelper mTCPInputHelper = null;//自身实例

    private BaseApplication mBaseApplication = null;

    private Context context = null;//上下文

    private int mTargetPort;//端口号

    private int eid = 0;

    private byte[] mBuffer = null;

    private TCPInputHelper(Context context,int port){
        this.context = context;
        this.mTargetPort = port;
        mBaseApplication = (BaseApplication) context.getApplicationContext();
        this.mBuffer = new byte[0];
    }

    public static TCPInputHelper getInstance(Context context,int port){
        if(mTCPInputHelper==null){
            mTCPInputHelper = new TCPInputHelper(context,port);
        }
        return mTCPInputHelper;
    }

    /**
     * 解读Socket消息方法，只解析消息头数据，并调用分发消息方法
     * @param socket 已建链的通信通道
     */
    public void readSocket(Socket socket){
        try {
            InputStream is = socket.getInputStream();
            byte[] data = new byte[0];

            if (mBuffer.length > 8) {
                // 若buffer长度超过头部长度，则读取缓存
                parseStreamDataBytes(null);

            } else if (is.available() > 0) {
                byte[] buffer = new byte[1024];
                int length = is.read(buffer);
                data = ConvertCodeUtility.subByteArr(buffer, 0, length);
                parseStreamDataBytes(data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析缓存及流中的字节数据，并分发处理
     * @param bytes 流中的字节数组
     */
    private void parseStreamDataBytes(byte[] bytes) {
        if(bytes != null){
            //将流中的字节复制进缓存容器
            int copyIndex = mBuffer.length;

            mBuffer = new byte[mBuffer.length+bytes.length];

            System.arraycopy(bytes,0,mBuffer,copyIndex,bytes.length);
        }

        //得到码流的事件类型字节数组
        byte[] type = ConvertCodeUtility.subByteArr(mBuffer,2,2);
        //当前设备ID
        eid = ConvertCodeUtility.bytes2Int(ConvertCodeUtility.subByteArr(mBuffer,4,4));

        mBaseApplication.CurrentEid = eid;
        short msgType = ConvertCodeUtility.bytes2Short(type);
        //消息体长度 = 消息总长度 - 消息头长度（8byte）
        int dataLength = ConvertCodeUtility.bytes2Short(ConvertCodeUtility
                .subByteArr(this.mBuffer,0,2)) - 8;
        if (this.mBuffer.length - 8 < dataLength) {
            return;
        }

        byte[] data = ConvertCodeUtility
                .subByteArr(this.mBuffer, 8, dataLength);

        //分发消息
        dispatchMsg(msgType, data);

        // 读取完消息字节后，清除缓存中已读取过的字节
        this.mBuffer = ConvertCodeUtility.subByteArr(this.mBuffer,
                dataLength + 8, this.mBuffer.length - dataLength - 8);
    }

    /**
     * 根据消息类型，交给指定的方法去处理
     * @param msgType 消息类型
     * @param data 消息体
     */
    private void dispatchMsg(short msgType, byte[] data) {
        switch (msgType){
            case TCPConfig.MSG_START_REQ:
                handStartReq(data);
                break;
            case TCPConfig.MSG_HEARTBEAT:
                break;
            case TCPConfig.MSG_SET_CELL_RFPARA_ACK:
                handSetCellParaAck(data);
                break;
            case TCPConfig.MSG_RESET_ACK:
                handResetAck(data);
                break;
            case TCPConfig.MSG_GET_CELL_PARA_ACK:
                handGetCellParaAck(data);
                break;
            case TCPConfig.MSG_USERID_REPORT:
                handUserReport(data);
                break;
            case TCPConfig.MSG_STATE_REPORT:
                handStateReport(data);
                break;
            case TCPConfig.MSG_PA_ONOFF_ACK:
                handPAOnOffACK();
                break;
            case TCPConfig.MSG_CATCHING_ONOFF_ACK:
                handCatchOnOffACK();
                break;
            case TCPConfig.MSG_GET_VERSION_ACK:
                handGetVersionACK();
                break;
            case TCPConfig.MSG_GET_DATETIME_ACK:
                handGetDateTimeACK();
                break;
        }
    }

    /**
     * 设备上报状态
     * @param data
     */
    private void handStateReport(byte[] data) {

    }

    /**
     * 获取设备系统时间
     */
    private void handGetDateTimeACK() {

    }

    /**
     * 获取设备版本号
     */
    private void handGetVersionACK() {

    }

    /**
     * 开关侦码响应
     */
    private void handCatchOnOffACK() {

    }

    /**
     * 开关功放响应
     */
    private void handPAOnOffACK() {
    }

    /**
     * 设置小区配置参数响应
     */
    private void handSetCellParaAck(byte[] data) {

    }

    /**
     * 获取小区配置参数响应
     * @param data
     */
    private void handGetCellParaAck(byte[] data) {

    }

    /**
     * 重启响应
     * @param data
     */
    private void handResetAck(byte[] data) {

    }

    /**
     * 数据上报
     * @param data
     */
    private void handUserReport(byte[] data) {

        //序列号
        byte[] sequenceByte = ConvertCodeUtility.subByteArr(data,0,4);
        //上号时间戳
        byte[] TimeStampByte = ConvertCodeUtility.subByteArr(data,4,4);
        //临时身份
        byte[] tempTypeByte = ConvertCodeUtility.subByteArr(data,8,1);
        //IMSI
        byte[] IMSIByte = ConvertCodeUtility.subByteArr(data,9,8);
        //IMEI
        byte[] IMEIByte = ConvertCodeUtility.subByteArr(data,17,8);
        //2G/3G数据域临时身份
        byte[] TMSIByte = ConvertCodeUtility.subByteArr(data,25,4);
        //国家码
        byte[] MCCByte = ConvertCodeUtility.subByteArr(data,29,2);
        //运营商码
        byte[] MNC = ConvertCodeUtility.subByteArr(data,31,1);
        //MME组ID
        byte[] MMEIDByte = ConvertCodeUtility.subByteArr(data,32,2);
        //MME代码
        byte[] MMECodeByte = ConvertCodeUtility.subByteArr(data,34,1);
        //MME临时身份
        byte[] MMETypeByte = ConvertCodeUtility.subByteArr(data,35,4);
        //TAC号码
        byte[] TACByte = ConvertCodeUtility.subByteArr(data,49,2);
        //基站内小区编号
        byte[] CellIDByte = ConvertCodeUtility.subByteArr(data,51,1);

        String IMSI = ConvertCodeUtility.bcd2String(IMSIByte);
        String IMEI = ConvertCodeUtility.bcd2String(IMEIByte);
        Date timeStamp = new Date(ConvertCodeUtility.bytes2Int(TimeStampByte));
        //运营商
        int MNCType = MNC[0];
        UserBean user = new UserBean();
        user.setImsi(IMSI);
        user.setImei(IMEI);
        user.setTimer(timeStamp);

        Intent intent = new Intent(TCPAction.ACTION_CATCH);
        //intent.putExtra("USER",user);
        context.sendBroadcast(intent);
    }

    /**
     * 通讯初始化请求
     * @param data
     */
    private void handStartReq(byte[] data) {
        /*Log.i(TAG, "设备ID：" + String.valueOf(eid));
        //设备号不存在
        if(eid!=1){
            //发送设备不存在的请求
            TCPOutHelper.getInstance(context).msgStartAck(eid, 1);
            Log.i(TAG,"设备号不存在!");
            return;
        }else{
            boolean flag = TCPOutHelper.getInstance(context).msgStartAck(
                    eid, 0);
            if (flag) {//通讯成功后就发起采集请求
                TCPOutHelper.getInstance(context).msgStartRF(eid, (byte) 1,(byte) 1);
            }
        }*/
        Intent intent = new Intent(TCPAction.ACTTON_START);
        //intent.putExtra("USER",user);
        context.sendBroadcast(intent);
    }

    /**
     * 取消心跳检测
     */
    public void stopTimer() {

    }

}

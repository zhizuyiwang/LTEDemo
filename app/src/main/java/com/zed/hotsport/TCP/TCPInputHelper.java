package com.zed.hotsport.TCP;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;

import com.zed.hotsport.activity.CatchSettingActivity;
import com.zed.hotsport.base.BaseApplication;
import com.zed.hotsport.base.Constant;
import com.zed.hotsport.bean.UserBean;
import com.zed.hotsport.utils.ByteToString;
import com.zed.hotsport.utils.ConvertCodeUtility;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Socket读取工具类
 * Created by Administrator on 2016/9/19.
 */
public class TCPInputHelper {

    private final String TAG = "SocketInputHelper"; // log标签
    private static TCPInputHelper mTCPInputHelper = null;//自身实例

    private BaseApplication mBaseApplication = null;
    /** 心跳更新时间 **/
    private static long mHeartBeatTime = 0L;

    private Context context = null;//上下文

    private int mTargetPort;//端口号

    private int eid = 0;

    private byte[] mBuffer = null;

    private Timer mTimer = null;

    private TimerTask task = null;

    private static SharedPreferences sp_device_status;

    private static SharedPreferences sp_catch_setting;

    private static SharedPreferences sp_device_para;

    private TCPInputHelper(Context context, int port) {
        this.context = context;
        this.mTargetPort = port;
        mBaseApplication = (BaseApplication) context.getApplicationContext();
        this.mBuffer = new byte[0];
    }

    public static TCPInputHelper getInstance(Context context, int port) {
        if (mTCPInputHelper == null) {
            mTCPInputHelper = new TCPInputHelper(context, port);
            sp_device_status = context.getSharedPreferences(Constant.DEVICE_STATUS,0);
            sp_catch_setting = context.getSharedPreferences(Constant.CATCH_SETTING,0);
            sp_device_para = context.getSharedPreferences(Constant.DEVICE_PARA,0);
        }
        return mTCPInputHelper;
    }

    /**
     * 解读Socket消息方法，只解析消息头数据，并调用分发消息方法
     *
     * @param socket 已建链的通信通道
     */
    public void readSocket(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            byte[] data;
            if (is.available() > 0) {
                byte[] buffer = new byte[1024 * 3];
                int length = is.read(buffer);
                data = ConvertCodeUtility.subByteArr(buffer, 0, length);

                Log.e("TAG", "消息总的长度为" + data.length);
                String sss = ByteToString.Byte2String(data);
                Log.e("收到总的数据：", sss);
                Log.e("数据的长度字节",ByteToString.Byte2String(ConvertCodeUtility.subByteArr(data,0,2)));
                Log.e("数据的长度的十六进制字节",ConvertCodeUtility.byteArrayToHex(ConvertCodeUtility.subByteArr(data,0,2)));
                Log.e("数据的命令字节：",ByteToString.Byte2String(ConvertCodeUtility.subByteArr(data,2,2)));
                Log.e("数据的命令十六进制字节：",ConvertCodeUtility.byteArrayToHex(ConvertCodeUtility.subByteArr(data,2,2)));
                Log.e("设备编号字节：",ByteToString.Byte2String(ConvertCodeUtility.subByteArr(data,4,4)));
                Log.e("设备编号十六进制字节：",ConvertCodeUtility.byteArrayToHex(ConvertCodeUtility.subByteArr(data,4,4)));
                //这里的逻辑是由于心跳消息只有消息头，没有消息体
                if (data.length <= 8) {
                    Log.e("TAG", "得到的数据长度小于8的消息总的长度为" + ConvertCodeUtility.bytes2Short(ConvertCodeUtility
                            .subByteArr(data, 0, 2)));
                    byte[] type = ConvertCodeUtility.subByteArr(data, 2, 2);
                    short msgType = ConvertCodeUtility.bytes2Short(type);
                    Log.e("TAG", "得到的数据长度小于8的命令：" + msgType);
                    if (data != null) {
                        String ss = ByteToString.Byte2String(data);
                        Log.e("得到的数据长度小于8的数据体：", ss);
                    }
                    if(msgType == 1321){//如果是心跳消息
                        handHeartBeat();
                    }
                    return;
                }
                Log.e("TAG", "开始解析数据");
                Log.e("得到的总数据的十六进制字节", ConvertCodeUtility.byteArrayToHex(data));
                parseStreamDataBytes(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化建链状态检测定时器
     */
    protected void startHeartBeatTimer() {
        this.mTimer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                long newTime = System.currentTimeMillis();
                // 若超过90s未更新时间，则判定为断链
                Log.e("+++++++++","lte心跳！"+newTime+"mHeartBeatTime"+mHeartBeatTime+"相差时间："+(newTime - mHeartBeatTime));
                if (newTime - mHeartBeatTime >= 1000*3) {
                    Log.e("!!!!!!!!!!!!!!!!!!!!!!!", "lte断开连接了！");
                    mTimer.cancel();
                    task.cancel();
                }
            }
        };
        this.mTimer.schedule(task, 1000, 1000);
    }
    /**
     * 取消心跳检测
     */
    protected void stopHeartBeatTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            if (task != null) {
                task.cancel();
            }
        }
    }
    /**
     * 解析缓存及流中的字节数据，并分发处理
     *
     * @param bytes 流中的字节数组
     */
    private void parseStreamDataBytes(byte[] bytes) {
        if (bytes == null) {
            //将流中的字节复制进缓存容器
            int copyIndex = mBuffer.length;
            mBuffer = new byte[mBuffer.length + bytes.length];
            System.arraycopy(bytes, 0, mBuffer, copyIndex, bytes.length);
            Log.e("TAG", "消息总的长度为" + mBuffer.length);
        }
        //得到码流的事件类型字节数组
        byte[] type = ConvertCodeUtility.subByteArr(bytes, 2, 2);
        //当前设备ID
        eid = ConvertCodeUtility.bytes2Int(ConvertCodeUtility.subByteArr(bytes, 4, 4));
        mBaseApplication.CurrentEid = eid;
        short msgType = ConvertCodeUtility.bytes2Short(type);
        Log.e("TAG", "命令" + msgType);
        //这里处理的逻辑是由于有的消息得到的长度大于8个，但是是心跳
        if(msgType == 1321){
            handHeartBeat();
            return;
        }
        //消息体长度 = 消息总长度 - 消息头长度（8byte）
        if (ConvertCodeUtility.bytes2Short(ConvertCodeUtility
                .subByteArr(bytes, 0, 2)) == 0) {
            return;
        }
        int dataLength = ConvertCodeUtility.bytes2Short(ConvertCodeUtility
                .subByteArr(bytes, 0, 2)) - 8;
        Log.e("TAG", "消息体的长度为" + dataLength);
        byte[] data = ConvertCodeUtility
                .subByteArr(bytes, 8, dataLength);

        if (data != null) {
            String ss = ByteToString.Byte2String(data);
            Log.e("收到数据体：", ss);
        }
        //分发消息
        dispatchMsg(msgType, data);
    }

    /**
     * 根据消息类型，交给指定的方法去处理
     *
     * @param msgType 消息类型
     * @param data    消息体
     */
    private void dispatchMsg(short msgType, byte[] data) {
        switch (msgType) {
            case TCPConfig.MSG_START_REQ://通讯初始化
                handStartReq(data);
                break;
            case TCPConfig.MSG_HEARTBEAT://心跳上报
                handHeartBeat();
                break;
            case TCPConfig.MSG_SET_CELL_RFPARA_ACK://设置小区参数回应
                handSetCellParaAck(data);
                break;
            case TCPConfig.MSG_RESET_ACK://设备重启响应
                handResetAck(data);
                break;
            case TCPConfig.MSG_GET_CELL_PARA_ACK://获取小区参数响应
                handGetCellParaAck(data);
                break;
            case TCPConfig.MSG_USERID_REPORT://数据上报
                handUserReport(data);
                break;
            case TCPConfig.MSG_STATE_REPORT://状态上报
                handStateReport(data);
                break;
            case TCPConfig.MSG_PA_ONOFF_ACK://小区功率开关设置回应
                handPAOnOffACK(data);
                break;
            case TCPConfig.MSG_CATCHING_ONOFF_ACK://侦码开关设置回应
                handCatchOnOffACK(data);
                break;
            case TCPConfig.MSG_GET_VERSION_ACK://版本查询上报回应
                handGetVersionACK(data);
                break;
            case TCPConfig.MSG_GET_DATETIME_ACK://获取设备时间响应
                handGetDateTimeACK();
                break;
            case TCPConfig.MSG_SET_DATETIME_ACK://设置设备时间响应
                handSetDateTimeACK();
                break;
        }
    }

    /**
     * 设置时间响应
     */
    private void handSetDateTimeACK() {

    }

    /**
     * 心跳请求处理
     */
    private void handHeartBeat() {
        mHeartBeatTime = System.currentTimeMillis();
        //在这里回应心跳请求
        TCPOutHelper.getInstance(context).msgHeartBatACK(
                eid);
        Log.e("TAG","回应心跳");
    }

    /**
     * 获取小区配置参数响应
     * @param data
     */
    private void handGetCellParaAck(byte[] data) {
        Log.e("TAG+++_____++++++++++++","获取小区参数数据体的十六进制字节"+ConvertCodeUtility.byteArrayToHex(data));
        //有效小区的个数
        int count = (int)data[4];
        Log.e("TAG","有效小区的个数："+count);
        for(int i = 0;i < count;i++){
            byte[] partData = ConvertCodeUtility.subByteArr(data,21*i + 5,21);
            //小区编号
            int cellId = partData[0];
            //预留
            byte[] reserve = ConvertCodeUtility.subByteArr(partData,1,2);
            //频点
            byte[] rfcn = ConvertCodeUtility.subByteArr(partData,3,4);
            //物理小区（PCI）
            byte[] pci = ConvertCodeUtility.subByteArr(partData,7,2);
            //位置区（TAC）
            byte[] tac = ConvertCodeUtility.subByteArr(partData,9,2);
            //TAC自增周期
            byte[] tac_increase = ConvertCodeUtility.subByteArr(partData,11,4);
            //频段
            byte band = partData[15];
            //运营商号
            byte operator = partData[16];
            //设备功率级别
            byte power = partData[17];
            //最小接入电平
            byte dianPing = partData[18];
            //国家码
            byte[] countryMark = ConvertCodeUtility.subByteArr(partData,19,2);
            if(cellId == 0){//联通
                sp_device_para.edit().putString(Constant.LT_FREQUENCY,ConvertCodeUtility.bytes2Int(rfcn)+"").commit();
                sp_device_para.edit().putString(Constant.LT_FREQUENCY1,ConvertCodeUtility.bytes2Int(rfcn)+"").commit();
                sp_device_para.edit().putString(Constant.LT_TAC,ConvertCodeUtility.bytes2Short(tac)+"").commit();
                sp_device_para.edit().putString(Constant.LT_TAC_PERIOD,ConvertCodeUtility.bytes2Int(tac_increase)+"").commit();
                sp_device_para.edit().putString(Constant.LT_BAND,(int)band+"").commit();
                sp_device_para.edit().putString(Constant.LT_DIANPING,(int)dianPing+"").commit();
                sp_device_para.edit().putInt(Constant.LT_DESCEND,20 - (int)power).commit();

            }else if(cellId == 1){//电信
                sp_device_para.edit().putString(Constant.DX_FREQUENCY,ConvertCodeUtility.bytes2Int(rfcn)+"").commit();
                sp_device_para.edit().putString(Constant.DX_FREQUENCY1,ConvertCodeUtility.bytes2Int(rfcn)+"").commit();
                sp_device_para.edit().putString(Constant.DX_TAC,ConvertCodeUtility.bytes2Short(tac)+"").commit();
                sp_device_para.edit().putString(Constant.DX_TAC_PERIOD,ConvertCodeUtility.bytes2Int(tac_increase)+"").commit();
                sp_device_para.edit().putString(Constant.DX_BAND,(int)band+"").commit();
                sp_device_para.edit().putString(Constant.DX_DIANPING,(int)dianPing+"").commit();
                sp_device_para.edit().putInt(Constant.DX_DESCEND,20 - (int)power).commit();

            }else if(cellId == 2){//移动
                sp_device_para.edit().putString(Constant.MOBILE_FREQUENCY,ConvertCodeUtility.bytes2Int(rfcn)+"").commit();
                sp_device_para.edit().putString(Constant.MOBILE_FREQUENCY1,ConvertCodeUtility.bytes2Int(rfcn)+"").commit();
                sp_device_para.edit().putString(Constant.MOBILE_TAC,ConvertCodeUtility.bytes2Short(tac)+"").commit();
                sp_device_para.edit().putString(Constant.MOBILE_TAC_PERIOD,ConvertCodeUtility.bytes2Int(tac_increase)+"").commit();
                sp_device_para.edit().putString(Constant.MOBILE_BAND,(int)band+"").commit();
                sp_device_para.edit().putString(Constant.MOBILE_DIANPING,(int)dianPing+"").commit();
                sp_device_para.edit().putInt(Constant.MOBILE_DESCEND,20 - (int)power).commit();

            }else if(cellId == 3){//移动1
                sp_device_para.edit().putString(Constant.MOBILE1_FREQUENCY,ConvertCodeUtility.bytes2Int(rfcn)+"").commit();
                sp_device_para.edit().putString(Constant.MOBILE1_FREQUENCY1,ConvertCodeUtility.bytes2Int(rfcn)+"").commit();
                sp_device_para.edit().putString(Constant.MOBILE1_TAC,ConvertCodeUtility.bytes2Short(tac)+"").commit();
                sp_device_para.edit().putString(Constant.MOBILE1_TAC_PERIOD,ConvertCodeUtility.bytes2Int(tac_increase)+"").commit();
                sp_device_para.edit().putString(Constant.MOBILE1_BAND,(int)band+"").commit();
                sp_device_para.edit().putString(Constant.MOBILE1_DIANPING,(int)dianPing+"").commit();
                sp_device_para.edit().putInt(Constant.MOBILE1_DESCEND,20 - (int)power).commit();
            }
        }
        Intent intent = new Intent(TCPAction.ACTION_PARA);
        context.sendBroadcast(intent);
    }
    /**
     * 设备上报状态
     *
     * @param data
     */
    private void handStateReport(byte[] data) {
        Log.e("得到上报状态消息体的十六进制字节",ConvertCodeUtility.byteArrayToHex(data));
        //有效小区的个数
        int count = (int)data[10];
        Log.e("TAG","状态上报中有效小区的个数："+count);
        //设备CPU使用率
        byte deviceCpu = data[0];
        sp_device_status.edit().putString(Constant.CPU_STATUS,(int)deviceCpu+"%").commit();
        //设备内存使用率
        byte deviceMemory = data[1];
        sp_device_status.edit().putString(Constant.MEMORY_STATUS,(int)deviceMemory+"%").commit();
        //设备温度
        byte deviceWenDu = data[3];
        sp_device_status.edit().putString(Constant.WENDU,(int)deviceWenDu+" 度").commit();
        //侦码开关状态
        byte catchStatus = data[11];
        sp_device_status.edit().putString(Constant.CATCH_STATUS,(int)catchStatus+"").commit();
        for(int i = 0;i < count;i++){
            byte[] partData = ConvertCodeUtility.subByteArr(data,10*i+16,10);
            //小区编号
            int cellId = partData[0];
            //小区状态
            byte cellStatus = partData[1];
            //功放状态
            byte cellPaStatus = partData[2];
            //功率衰减值
            byte cellPaDown = partData[3];
            //功率输出值
            byte cellPa = partData[4];
            if(cellId == 0){//联通
                sp_device_status.edit().putString(Constant.LT_CELL_STATUS,
                        (int)cellStatus == 0 ? "未就绪" : "就绪").commit();
                sp_device_status.edit().putString(Constant.LT_PA_STATUS,
                        (int)cellPaStatus == 0 ? "关" : "开").commit();
                sp_device_status.edit().putString(Constant.LT_PA_DOWN,(int)cellPaDown+"").commit();
                sp_device_status.edit().putString(Constant.LT_PA_POWER,(int)cellPa+"").commit();
            }else if(cellId == 1){//电信
                sp_device_status.edit().putString(Constant.DX_CELL_STATUS,
                        (int)cellStatus == 0 ? "未就绪" : "就绪").commit();
                sp_device_status.edit().putString(Constant.DX_PA_STATUS,
                        (int)cellPaStatus == 0 ? "关" : "开").commit();
                sp_device_status.edit().putString(Constant.DX_PA_DOWN,(int)cellPaDown+"").commit();
                sp_device_status.edit().putString(Constant.DX_PA_POWER,(int)cellPa+"").commit();
            }else if(cellId == 2){//移动
                sp_device_status.edit().putString(Constant.MOBILE_CELL_STATUS,
                        (int)cellStatus == 0 ? "未就绪" : "就绪").commit();
                sp_device_status.edit().putString(Constant.MOBILE_PA_STATUS,
                        (int)cellPaStatus == 0 ? "关" : "开").commit();
                sp_device_status.edit().putString(Constant.MOBILE_PA_DOWN,(int)cellPaDown+"").commit();
                sp_device_status.edit().putString(Constant.MOBILE_PA_POWER,(int)cellPa+"").commit();
            }else if(cellId == 3){//移动1
                sp_device_status.edit().putString(Constant.MOBILE1_CELL_STATUS,
                        (int)cellStatus == 0 ? "未就绪" : "就绪").commit();
                sp_device_status.edit().putString(Constant.MOBILE1_PA_STATUS,
                        (int)cellPaStatus == 0 ? "关" : "开").commit();
                sp_device_status.edit().putString(Constant.MOBILE1_PA_DOWN,(int)cellPaDown+"").commit();
                sp_device_status.edit().putString(Constant.MOBILE1_PA_POWER,(int)cellPa+"").commit();
            }
        }
        Intent intent = new Intent(TCPAction.ACTTON_STATUS);
        context.sendBroadcast(intent);
    }

    /**
     * 获取设备系统时间
     */
    private void handGetDateTimeACK() {

    }

    /**
     * 获取设备版本号
     */
    private void handGetVersionACK(byte[] data) {
        Log.e("TAG","获取版本号");
        Log.e("版本号数据体十六字节表示：",ConvertCodeUtility.byteArrayToHex(data));
        byte[] versionByte = ConvertCodeUtility.subByteArr(data,4,data.length-4);
        String version = new String(versionByte);
        Log.e("TAG","版本号："+version);
        Intent intent = new Intent(TCPAction.ACTTON_VERSION);
        intent.putExtra("VERSION",version);
        context.sendBroadcast(intent);
    }
    /**
     * 设备总开关侦码响应
     */
    private void handCatchOnOffACK(byte[] data) {

    }

    /**
     * 各个小区开关功放响应
     */
    private void handPAOnOffACK(byte[] data) {

    }

    /**
     * 设置小区配置参数响应
     */
    private void handSetCellParaAck(byte[] data) {

    }




    /**
     * 重启响应
     *
     * @param data
     */
    private void handResetAck(byte[] data) {

    }

    /**
     * 数据上报
     *
     * @param data
     */
    private void handUserReport(byte[] data) {
        ArrayList<UserBean> users = new ArrayList<>();
        int count = ConvertCodeUtility.bytes2Int(ConvertCodeUtility.subByteArr(data,0,4));
        Log.e("TAG","得到的数据个数："+count);
        for(int i = 0;i < count; i++){
            byte[] mData = ConvertCodeUtility.subByteArr(data,42*i+4,42);
            //序列号
            byte[] sequenceByte = ConvertCodeUtility.subByteArr(mData, 0, 4);
            //上号时间戳
            byte[] TimeStampByte = ConvertCodeUtility.subByteArr(mData, 4, 4);
            //临时身份
            byte[] tempTypeByte = ConvertCodeUtility.subByteArr(mData, 8, 1);
            //IMSI
            byte[] IMSIByte = ConvertCodeUtility.subByteArr(mData, 9, 8);
            //IMEI
            byte[] IMEIByte = ConvertCodeUtility.subByteArr(mData, 17, 8);
            //2G/3G数据域临时身份
            byte[] TMSIByte = ConvertCodeUtility.subByteArr(mData, 25, 4);
            //国家码
            byte[] MCCByte = ConvertCodeUtility.subByteArr(mData, 29, 2);
            //运营商码
            byte[] MNC = ConvertCodeUtility.subByteArr(mData, 31, 1);
            //MME组ID
            byte[] MMEIDByte = ConvertCodeUtility.subByteArr(mData, 32, 2);
            //MME代码
            byte[] MMECodeByte = ConvertCodeUtility.subByteArr(mData, 34, 1);
            //MME临时身份
            byte[] MMETypeByte = ConvertCodeUtility.subByteArr(mData, 35, 4);
            //TAC号码
            byte[] TACByte = ConvertCodeUtility.subByteArr(mData, 39, 2);
            //基站内小区编号
            byte[] CellIDByte = ConvertCodeUtility.subByteArr(mData, 41, 1);

            Log.e("TAG","IMSI的十六进制字节："+ConvertCodeUtility.byteArrayToHex(IMSIByte));
            Log.e("TAG","IMEI的十六进制字节："+ConvertCodeUtility.byteArrayToHex(IMEIByte));
            String IMSI = ConvertCodeUtility.bcd2String(IMSIByte).substring(1);
            String IMEI = ConvertCodeUtility.bcd2String(IMEIByte).substring(1);
            Log.e("TAG","IMSI："+IMSI);
            Log.e("TAG","IMEI："+IMEI);
            Date timeStamp = new Date(ConvertCodeUtility.bytes2Int(TimeStampByte));
            Log.e("得到的时间：",ConvertCodeUtility.getStrByDate(timeStamp));
            //运营商
            int MNCType = MNC[0];
            Log.e("此条数据的运营商的编号：",MNCType+"");
            //基站内小区的编号
            int cellId = CellIDByte[0];
            Log.e("此条数据所在小区的编号：",cellId+"");

            UserBean user = new UserBean();
            user.setImsi(IMSI);
            user.setImei(IMEI);
            user.setTimer(timeStamp);
            if(MNCType==0){
                user.setOprerator("中国移动");
            }else if(MNCType==1){
                user.setOprerator("中国联通");
            }else if(MNCType==11){
                user.setOprerator("中国电信");
            }
            users.add(user);
        }
        Intent intent = new Intent(TCPAction.ACTION_CATCH);
        intent.putExtra("USERS", users);
        context.sendBroadcast(intent);
    }

    /**
     * 通讯初始化请求
     *
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
        TCPOutHelper.getInstance(context).msgStartAck(eid, 0);
        BaseApplication.connect = true;
        //打开侦码开关和各个小区的功率
        TCPOutHelper.getInstance(context).msgSetAllCatch(BaseApplication.CurrentEid,(byte)1);
        TCPOutHelper.getInstance(context).msgSetPA(BaseApplication.CurrentEid,(byte)0,(byte)1);
        TCPOutHelper.getInstance(context).msgSetPA(BaseApplication.CurrentEid,(byte)1,(byte)1);
        TCPOutHelper.getInstance(context).msgSetPA(BaseApplication.CurrentEid,(byte)2,(byte)1);
        TCPOutHelper.getInstance(context).msgSetPA(BaseApplication.CurrentEid,(byte)3,(byte)1);

        sp_catch_setting.edit().putBoolean(Constant.ALLCATCH,true).commit();
        sp_catch_setting.edit().putBoolean(Constant.LT_CATCH,true).commit();
        sp_catch_setting.edit().putBoolean(Constant.DX_CATCH,true).commit();
        sp_catch_setting.edit().putBoolean(Constant.YD_CATCH,true).commit();
        sp_catch_setting.edit().putBoolean(Constant.YD1_CATCH,true).commit();

        Intent intent = new Intent(TCPAction.ACTTON_START);
        context.sendBroadcast(intent);
    }
    /**
     * 取消心跳检测
     */
    public void stopTimer() {

    }
}

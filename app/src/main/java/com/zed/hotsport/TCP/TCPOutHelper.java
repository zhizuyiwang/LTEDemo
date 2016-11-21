package com.zed.hotsport.TCP;

import android.content.Context;
import android.util.Log;

import com.zed.hotsport.base.BaseApplication;
import com.zed.hotsport.bean.CellBean;
import com.zed.hotsport.utils.ConvertCodeUtility;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Socket写入工具类
 * Created by Administrator on 2016/9/19.
 */
public class TCPOutHelper {
    private final String TAG = "TCPOutHelper";

    private Context context = null;

    private static TCPOutHelper mTCPOutHelper = null;//Socket写入工具类自身实例

    private BaseApplication mBaseApplication = null;//全局环境变量

    private TCPOutHelper(Context context){
        this.context = context;
        mBaseApplication = (BaseApplication) context.getApplicationContext();
    }

    public static TCPOutHelper getInstance(Context context){
        if(mTCPOutHelper==null){
            mTCPOutHelper = new TCPOutHelper(context);
        }
        return mTCPOutHelper;
    }

    /**发送Socket请求，创建消息头，并拼接成完整的消息数组发送至socket
     * @param client
     * @param msgType
     * @param eid
     * @param data
     * @return
     */
    private boolean sendMsg(TCPClient client,byte[] msgType,int eid,byte[] data){
        //先对client做非空判断
        if(client == null){
            return false;
        }
        try {
            byte[] sendData;
            if (data != null) {
                sendData = new byte[data.length + 8];
                System.arraycopy(
                        ConvertCodeUtility.short2Bytes((short) sendData.length),
                        0, sendData, 0, 2);
                System.arraycopy(data, 0, sendData, 8, data.length);
            } else {
                // 当为心跳响应时，data为null，仅包含消息头,还包括查询设备时间
                sendData = new byte[8];
                System.arraycopy(ConvertCodeUtility.short2Bytes((short) 8), 0,
                        sendData, 0, 2);
            }
            System.arraycopy(msgType,0,sendData,2,2);
            System.arraycopy(ConvertCodeUtility.int2Bytes(eid),0,sendData,4,4);

            Socket socket = client.getSocket();
            OutputStream os ;

            os = socket.getOutputStream();
            os.write(sendData);
            os.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "发送Socket写入出错！");
            return false;
        }
    }
    /**
     * 通信初始化类型MSG_START_ACK消息
     *
     * @param eid
     *            建链设备ID
     * @param flag
     *            响应标识
     * @return 结果状态
     */
    public boolean msgStartAck(int eid, int flag) {
        byte[] data = new byte[4];
        System.arraycopy(ConvertCodeUtility.int2Bytes(flag),0,data,0,4);
        return sendMsg(mBaseApplication.getTCPClient(TCPConfig.SOCKET_CONFIG_PORT_3G),
                ConvertCodeUtility.short2Bytes(TCPConfig.MSG_START_ACK), eid, data);
    }

    /**
     * 发送开关功放请求MSG_PA_ONOFF_REQ类型
     * @param eid
     *      建链设备ID
     * @param cellId
     *          小区ID
     * @param flag
     *          开关功放的标志位
     * @return
     */
    public boolean msgSetPA(int eid, byte cellId ,byte flag) {
        byte[] data = new byte[2];
        data[0] = cellId;
        data[1] = flag;

        return sendMsg(mBaseApplication.getTCPClient(TCPConfig.SOCKET_CONFIG_PORT_3G),
                ConvertCodeUtility.short2Bytes(TCPConfig.MSG_PA_ONOFF_REQ), eid, data);
    }

    /**
     * 发送开关侦码请求类型MSG_CATCHING_ONOFF_REQ
     * @param eid
     * @param flag
     *      开关侦码标志位
     * @return
     */
    public boolean msgSetAllCatch(int eid,byte flag){
        byte[] data = new byte[1];
        data[0] = flag;

        return sendMsg(mBaseApplication.getTCPClient(TCPConfig.SOCKET_CONFIG_PORT_3G),
                ConvertCodeUtility.short2Bytes(TCPConfig.MSG_CATCHING_ONOFF_REQ),eid,data);
    }

    /**
     * 设置/同步设备时间请求类型MSG_SET_DATETIME
     * @param eid
     * @return
     */
    public boolean  msgSetDateTime(int eid){
        byte[] data = new byte[14];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss",
                Locale.CHINA);
        String strDate = sdf.format(new Date());
        String date1 = strDate.substring(0,4);
        String date2 = strDate.substring(4,8);
        String time1 = strDate.substring(8,10);
        String time2 = strDate.substring(10,12);
        String time3 = strDate.substring(12,14);

        System.arraycopy(ConvertCodeUtility.int2Bytes(Integer.parseInt(date1)),0,data,0,4);
        System.arraycopy(ConvertCodeUtility.int2Bytes(Integer.parseInt(date2)),0,data,4,4);
        System.arraycopy(ConvertCodeUtility.short2Bytes((short) Integer.parseInt(time1)),0,data,8,2);
        System.arraycopy(ConvertCodeUtility.short2Bytes((short) Integer.parseInt(time2)),0,data,10,2);
        System.arraycopy(ConvertCodeUtility.short2Bytes((short) Integer.parseInt(time3)),0,data,12,14);

        return sendMsg(mBaseApplication.getTCPClient(TCPConfig.SOCKET_CONFIG_PORT_3G),
                ConvertCodeUtility.short2Bytes(TCPConfig.MSG_SET_DATETIME),eid,data);
    }

    /**
     * 获取设备的时间类型MSG_GET_DATETIME，只有消息头
     * @param eid
     * @return
     */
    public boolean msgGetDateTime(int eid){

        return sendMsg(mBaseApplication.getTCPClient(TCPConfig.SOCKET_CONFIG_PORT_3G),
                ConvertCodeUtility.short2Bytes(TCPConfig.MSG_GET_DATETIME),eid,null);
    }
    /**
     * 获取设备版本号类型MSG_GET_VERSION，只有消息头
     * @param eid
     * @return
     */
    public boolean msgGetVersion(int eid){
        return sendMsg(mBaseApplication.getTCPClient(TCPConfig.SOCKET_CONFIG_PORT_3G),
                ConvertCodeUtility.short2Bytes(TCPConfig.MSG_GET_VERSION),eid,null);
    }

    /**
     * 重启设备请求类型MSG_RESET_REQ
     * @param eid
     * @param flag
     *          重启范围的标志位
     * @return
     */
    public boolean msgReset(int eid, byte flag){
        byte[] data = new byte[1];
        data[0] = flag;
        return sendMsg(mBaseApplication.getTCPClient(TCPConfig.SOCKET_CONFIG_PORT_3G),
                ConvertCodeUtility.short2Bytes(TCPConfig.MSG_RESET_REQ), eid, data);
    }

    /**
     * 获取小区参数请求类型MSG_GET_CELL_PARA_REQ,只有消息头
     * @param eid
     * @return
     */
    public boolean msgGetCellPara(int eid){
        return sendMsg(mBaseApplication.getTCPClient(TCPConfig.SOCKET_CONFIG_PORT_3G),
                ConvertCodeUtility.short2Bytes(TCPConfig.MSG_GET_CELL_PARA_REQ),eid,null);
    }

    /**
     * 心跳回应类型MSG_HEARTBAT_ACK，只有消息头
     * @param eid
     * @return
     */
    public boolean msgHeartBatACK(int eid){
        return sendMsg(mBaseApplication.getTCPClient(TCPConfig.SOCKET_CONFIG_PORT_3G),
                ConvertCodeUtility.short2Bytes(TCPConfig.MSG_HEARTBAT_ACK),eid,null);
    }


    /**
     * 设置小区的参数
     * @return
     */
    public boolean msgSetCellPara(int eid, CellBean cellBean,int flag,int type){
        int rfcn = Integer.parseInt(cellBean.getFrequency());//频点
        short tac = Short.parseShort(cellBean.getTac());//位置区
        int tac_increase = Integer.parseInt(cellBean.getLac_period());//位置区更新周期
        byte band = (byte) Integer.parseInt(cellBean.getBand());//频段
        byte dianPing = (byte) Integer.parseInt(cellBean.getDianPing());//最小接入电平
        byte power_rand = (byte) (20 - cellBean.getPower_down());//功率等级

        byte[] data = new byte[22];
        data[0] = 1;
        data[1] = (byte) flag;
        System.arraycopy(new byte[]{0,0},0,data,2,2);
        System.arraycopy(ConvertCodeUtility.int2Bytes(rfcn),0,data,4,4);
        System.arraycopy(ConvertCodeUtility.short2Bytes((short) 6),0,data,8,2);
        System.arraycopy(ConvertCodeUtility.short2Bytes(tac),0,data,10,2);
        System.arraycopy(ConvertCodeUtility.int2Bytes(tac_increase),0,data,12,4);
        data[16] = band;
        data[17] = (byte) type;
        data[18] = power_rand;
        data[19] = dianPing;
        System.arraycopy(ConvertCodeUtility.short2Bytes((short) 460),0,data,20,2);
        return sendMsg(mBaseApplication.getTCPClient(TCPConfig.SOCKET_CONFIG_PORT_3G),
                ConvertCodeUtility.short2Bytes(TCPConfig.MSG_SET_CELL_RFPARA_REQ),eid,data);
    }
}

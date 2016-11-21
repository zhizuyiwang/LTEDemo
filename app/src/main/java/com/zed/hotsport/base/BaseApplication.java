package com.zed.hotsport.base;

import android.app.Application;
import android.util.Log;

import com.zed.hotsport.TCP.TCPClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/19.
 */
public final class BaseApplication extends Application{

    public  Map<Integer,TCPClient> mTCPClients = null;
    /** 当前连接设备ID **/
    public static int CurrentEid = 0;
    public static boolean connect = false;

    /**
     * 存储已连接的对象
     * @param port 连接端口号
     * @param client 已连接的对象
     */
    public  void setTCPClient(int port,TCPClient client){
        if(mTCPClients==null){
            Log.e("TAG","创建连接对象map集合");
            mTCPClients = new HashMap<Integer, TCPClient>();
        }
        mTCPClients.put(port,client);
    }

    /**
     * 获取已连接的对象
     * @param port 连接端口号
     * @return 连接对象
     */
    public  TCPClient getTCPClient(int port){
        TCPClient client = null;
        if(mTCPClients!=null){
           client = mTCPClients.get(port);
        }
        return client;
    }

    /**
     * 移除连接客户端并关闭socket
     *  @return 移除连接对象端口
     */
    public void removeClient(int port){
        if(mTCPClients!=null){
            Log.e("TAG","从全局类中得到连接客户端");
            TCPClient client = mTCPClients.get(port);
            if(client!=null){
                Log.e("TAG","从全局类中得到的连接客户端不为空");
                client.closeSocket(client);
                mTCPClients.remove(port);
            }
        }else{
            Log.e("TAG","连接对象为空");
        }
    }

    public void setCurrentEid(int currentEid){
        CurrentEid = currentEid;
    }

    public int getCurrentEid(){
        return CurrentEid;
    }

}

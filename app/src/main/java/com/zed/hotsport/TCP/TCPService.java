package com.zed.hotsport.TCP;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.zed.hotsport.base.BaseApplication;

import java.net.Socket;

public class TCPService extends Service {
    private BaseApplication mBaseApplication = null;//全局环境变量

    private TCPClient client = null;//TCP客户端

    private Socket mSocket = null;//连接通道

    private boolean isConnect = true;//连接标志

    private int mTargetPort = TCPConfig.SOCKET_CONFIG_PORT_3G;//监听端口

    private TCPInputHelper mInputHelper = null;//Socket读取工具类


    public TCPService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(this.mTargetPort==0){
           this. mTargetPort = intent.getIntExtra("targetPort",0);
        }
        if(mBaseApplication==null){
            mBaseApplication = (BaseApplication) getApplication();
        }

        mInputHelper = TCPInputHelper.getInstance(getApplicationContext(),this.mTargetPort);

        if(mTargetPort!=0){
            new Thread(){
                @Override
                public void run() {
                    client = mBaseApplication.getTCPClient(mTargetPort);

                    if(client==null){
                        //每次重新获取，断链时可以清除全局变量
                        Log.e("TAG","从全局类中得到的Client为空");
                        client = new TCPClient(mTargetPort,getBaseContext());
                        client.listen();//获取socket
                        mBaseApplication.setTCPClient(mTargetPort,client);
                        new Thread(){
                            @Override
                            public void run() {
                                if(client != null){
                                    mSocket = client.getSocket();
                                    while (isConnect && mSocket != null && mSocket.isConnected()){
                                        mInputHelper.readSocket(mSocket);
                                        SystemClock.sleep(300);
                                    }
                                }
                            }
                        }.start();
                    }
                    SystemClock.sleep(1000);
                }
            }.start();
        }

       /* SystemClock.sleep(1000);
        //开启读取线程
        new Thread(){
            @Override
            public void run() {
                if(client != null){
                    mSocket = client.getSocket();
                    while (isConnect && mSocket != null && mSocket.isConnected()){
                        mInputHelper.readSocket(mSocket);
                        //mInputHelper.readSocket1(mSocket);
                        SystemClock.sleep(300);
                    }
                }
            }
        }.start();*/

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        this.isConnect = false;
        disConnected();
        super.onDestroy();
    }

    /**
     * 断链操作方法
     */
    public void disConnected(){
        this.client = null;
        mBaseApplication.removeClient(TCPConfig.SOCKET_CONFIG_PORT_3G);
        mBaseApplication.setCurrentEid(0);

        //取消心跳检测
        if(mInputHelper != null){
            mInputHelper.stopTimer();
        }

    }
}

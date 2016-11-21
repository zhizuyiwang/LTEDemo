package com.zed.hotsport.TCP;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zed.hotsport.utils.ToastUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2016/9/18.
 */
public class TCPClient{

    private ServerSocket mServerSocket = null;//连接服务对象
    private Socket mSocket = null;//连接对象
    private int mIntTargetPort = 0;//目标端口
    private Context context;

    /**
     * 带参构造方法
     *
     * @param targetPort
     *            目标端口。 参数为：SocketConfig.SOCKET_CONFIG_PORT_XXX
     */
    public TCPClient(int targetPort, Context context){
        this.mIntTargetPort = targetPort;
        this.context = context;
        try {
            this.mServerSocket = new ServerSocket(mIntTargetPort);
        } catch (IOException e) {
            Log.e("ServerSocket创建失败","ServerSocket创建失败");
            ToastUtils.showToast(context,"ServerSocket创建失败",0);
            e.printStackTrace();
        }
    }

    /**
     * 监听连接,得到Socket实例
     */
    public void listen(){

        try {
            if(this.mServerSocket==null){
                return;
            }
            Log.e("TAG","监听连接,准备得到Socket实例");
            setSocket(mServerSocket.accept());
            Intent intent = new Intent(TCPAction.ACTTON_CESHI);
            context.sendBroadcast(intent);

            Log.e("TAG","监听连接,已经得到Socket实例");
        } catch (IOException e) {
            Log.e("TAG","TCPClient对象构建失败");
            e.printStackTrace();
        }
    }
    public void setServerSocket(ServerSocket serverSocket){
        this.mServerSocket = serverSocket;
    }
    public ServerSocket getServerSocket(){
        return this.mServerSocket;
    }

    /**
     * 设置Socket对象
     *
     * @param socket
     *            通信实例
     */
    public void setSocket(Socket socket){
        this.mSocket = socket;
    }
    /**
     * 返回当前连接
     *
     * @return Socket 当前连接
     */
    public Socket getSocket(){
        return this.mSocket;
    }
    /**
     * 获取当前建链本地端口
     *
     * @return 本地端口号
     */
    public int getTargetPort() {
        return this.mIntTargetPort;
    }
    /**
     * 关闭Socket连接
     */
    public void closeSocket(TCPClient client){
        try {
            if(client.getSocket()!=null){
                client.getSocket().close();
            }
            if(client.getServerSocket()!=null){
                client.getServerSocket().close();
                Log.e("TAG","连接服务对象关闭");
            }
        } catch (IOException e) {
            Log.e("TAG","Socket关闭失败");
            e.printStackTrace();
        }
    }
}

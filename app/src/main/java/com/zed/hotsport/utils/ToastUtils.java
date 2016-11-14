package com.zed.hotsport.utils;


import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class ToastUtils {
	private static Toast mToast;
	public static void showToast(Context context ,String msg,int flag){
		if(mToast != null){
			mToast.setText(msg);
		}else{
			/*
		     * 第一个参数：当前的上下文环境，用this或getApplicationContext()表示。
		     * 第二个参数：显示的字符串，用R.string表示。
		     * 第三个参数：显示的时间长短。用LENGTH_LONG(长)或LENGTH_SHORT(短)表示，也可以用毫秒。
		     */
			mToast = Toast.makeText(context, msg, flag);
		}
		mToast.show();//显示toast信息
	}
   /**
    * 一个确定按钮的对话框，只用于提示，点击确定，对话框消失
    * @param context
    * @param title
    * @param msg
    */
	public static void showDialog1Btn(Context context,String title,String msg){
		Builder builder=new Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setCancelable(false);
		builder.create();
		builder.show();
	}
	public static void showDialog2Btn(Context context,String title,String msg,OnClickListener listener){
		Builder builder=new Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton("确定", listener);
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setCancelable(false);
		builder.create();
		builder.show();
	}

}

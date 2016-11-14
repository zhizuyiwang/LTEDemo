package com.zed.ceshi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        byte[] ss = new byte[]{3, -23};
        Log.e("TAG",bytes2Int(ss,0,2)+"");
        String dd = ConvertCodeUtility.getCurrentTime();
        Log.e("TAG","dd"+dd+","+dd.length());


        byte[] data = new byte[14];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss",
                Locale.CHINA);
        String strDate = sdf.format(new Date());
        String date = strDate.substring(0,8);
        String time = strDate.substring(8,14);

        Log.e("TAGTAGTAG","Date:"+Integer.parseInt(date)+",Time:"+Integer.parseInt(time));


    }
    public  int bytes2Int(byte[] b, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = ((int)b[i]) & 0xff;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }
    public  byte[] int2Bytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte)((value >> 8 * i) & 0xff);
        }
        return b;
    }
    /**
     * 将网络传输byte[2]转换为int
     *
     * @param data
     *            网络传输byte[2]
     * @return short 值
     */
    public static int bytes2Int2(byte[] data) {
        int value = (data[1] & 0xFF) | (data[0] & 0xFF) << 8;
        return value;
    }



}

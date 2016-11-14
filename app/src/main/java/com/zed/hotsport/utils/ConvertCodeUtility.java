package com.zed.hotsport.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/18.
 */
public class ConvertCodeUtility {

    /**
     * 截取字节数组方法
     *
     * @param data
     *            源字节数组
     * @param start
     *            截取位置索引
     * @param length
     *            截取长度
     * @return byte[] 目标字节数组
     */
    public static byte[] subByteArr(byte[] data, int start, int length) {
        byte[] value = new byte[length];
        if (data.length - start >= length) {
            System.arraycopy(data, start, value, 0, length);
        }
        return value;
    }

    /**
     * 将网络传输byte[]转换为short（大端模式，高字节在低地址）
     *
     * @param data
     *            网络传输byte[]
     * @return short 值
     */
    public static short bytes2Short(byte[] data) {
        short value = (short) ((data[1] & 0xFF) | ((data[0] & 0xFF) << 8));
        return value;
    }
    /**
     * 将网络传输byte[]转换为short（小端模式，高字节在高地址）
     *
     * @param data
     *            网络传输byte[]
     * @return short 值
     */
    public static short bytes2Short1(byte[] data) {
        short value = (short) ((data[0] & 0xFF) | ((data[1] & 0xFF) << 8));
        return value;
    }

    /**
     * 将short转换为网络字节序byte[](大端模式，高字节在低地址)
     *
     * @param value
     *            值
     * @return 网络字节序byte[]
     */
    public static byte[] short2Bytes(short value) {
        byte[] data = new byte[2];
        data[0] = (byte) (value >> 8);
        data[1] = (byte) (value & 0xFF);
        return data;
    }
    /**
     * 将short转换为网络字节序byte[](小端模式，高字节在高地址)
     *
     * @param value
     *            值
     * @return 网络字节序byte[]
     */
    public static byte[] short2Bytes1(short value) {
        byte[] data = new byte[2];
        data[1] = (byte) (value >> 8);
        data[0] = (byte) (value & 0xFF);
        return data;
    }



    /**
     * 将网络传输byte[]转换为int(大端模式)
     *
     * @param data
     *            网络传输byte[]
     * @return 值
     */
    public static int bytes2Int(byte[] data) {
        int value = (data[3] & 0xff) | ((data[2] & 0xff) << 8) | ((data[1] & 0xff) << 16) | ((data[0] & 0xff) << 24);
        return value;
    }
    /**
     * 将网络传输byte[]转换为int(小端模式)
     *
     * @param data
     *            网络传输byte[]
     * @return 值
     */
    public static int bytes2Int1(byte[] data) {
        int value = (data[0] & 0xff) | ((data[1] & 0xff) << 8) | ((data[2] & 0xff) << 16) | ((data[3] & 0xff) << 24);
        return value;
    }


    /**
     * 将int转换为网络字节序byte[](大端模式)
     *
     * @param value
     *            值
     * @return 网络字节序byte[]
     */
    public static byte[] int2Bytes(int value) {
        byte[] data = new byte[4];
        data[0] = (byte) (value >> 24);
        data[1] = (byte) (value >> 16 & 0xFF);
        data[2] = (byte) (value >> 8 & 0xFF);
        data[3] = (byte) (value & 0xFF);
        return data;
    }
    /**
     * 将int转换为网络字节序byte[](小端模式)
     *
     * @param value
     *            值
     * @return 网络字节序byte[]
     */
    public static byte[] int2Bytes1(int value) {
        byte[] data = new byte[4];
        data[3] = (byte) (value >> 24);
        data[2] = (byte) (value >> 16 & 0xFF);
        data[1] = (byte) (value >> 8 & 0xFF);
        data[0] = (byte) (value & 0xFF);
        return data;
    }


    /**
     * long整数转换为8字节的byte数组
     *
     * @param value
     *            long整数
     * @return byte数组
     */
    public static byte[] longToByte8(long value) {
        byte[] targets = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return targets;
    }

    /**
     * BCD转换成String
     *
     * @param b
     * @return
     */
    public static String bcd2String(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            int l = (b[i] & 0x0f) + 48;
            sb.append((char) l);
            int h = ((b[i] & 0xff) >> 4) + 48;
            sb.append((char) h);

        }
        return sb.toString();
    }

    /**
     * 把日期类型转化为字符串型数据
     * @param date
     * @return
     */
    public static String getStrByDate(Date date) {
        String str = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            str = simpleDateFormat.format(date);
        }
        return str;
    }

    /**
     * @brief 得到今天的日期
     * @return 今天的日期如:2016-03-30 10:30:45
     */
    public static String getCurrentTime() {
        long currtime = System.currentTimeMillis();
        Date date = new Date(currtime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String str = simpleDateFormat.format(date);
        return str;
    }

}

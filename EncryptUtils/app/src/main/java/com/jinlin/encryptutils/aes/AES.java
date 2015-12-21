package com.jinlin.encryptutils.aes;

/**
 * Created by J!nl!n on 15/12/21.
 * Copyright © 1990-2015 J!nl!n™ Inc. All rights reserved.
 * <p/>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 */
public class AES {

    static {
        System.loadLibrary("encrypt");
    }

    public static final int ENCRYPT = 0;

    public static final int DECRYPT = 1;

    public native static byte[] crypt(byte[] data, long time, int mode);

    public native static byte[] read(String path, long time);

    /**
     * 将16进制转换为二进制(服务端)
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexStr2Bytes(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String bytes2HexStr(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte aData : data) {
            if (((int) aData & 0xff) < 0x10) { /* & 0xff转换无符号整型 */
                buf.append("0");
            }
            buf.append(Long.toHexString((int) aData & 0xff)); /* 转换16进制,下方法同 */
        }
        return buf.toString();
    }

}
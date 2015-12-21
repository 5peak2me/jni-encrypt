package com.jinlin.encryptutils.aes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jinlin.encryptutils.R;

import java.io.UnsupportedEncodingException;

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
public class AESActivity extends AppCompatActivity {
    public static final String TESTDATA = "Jinlin";
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    private void assignViews() {
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes);

        assignViews();

        try {
            System.out.println("======jni-crypt-test======");
            byte[] data = TESTDATA.getBytes("UTF-8");
            data = AES.crypt(data, System.currentTimeMillis(), AES.ENCRYPT);
            String hexStr = AES.bytes2HexStr(data);
            System.out.println("encrypt:" + hexStr);
            tv1.setText(String.format("jni encrypt:%s", hexStr));

            data = AES.hexStr2Bytes(hexStr);
            data = AES.crypt(data, System.currentTimeMillis(), AES.DECRYPT);
            System.out.println("decrypt:" + new String(data, "UTF-8"));
            tv2.setText(String.format("jni decrypt:%s", new String(data, "UTF-8")));

            System.out.println("======java-crypt-test======");
            data = TESTDATA.getBytes("UTF-8");
            data = JavaAESCryptor.encrypt(data);
            hexStr = AES.bytes2HexStr(data);
            System.out.println("encrypt:" + hexStr);
            tv3.setText(String.format("java encrypt:%s", hexStr));

            data = AES.hexStr2Bytes(hexStr);
            data = JavaAESCryptor.decrypt(data);
            System.out.println("decrypt:" + new String(data, "UTF-8"));
            tv4.setText(String.format("java decrypt:%s", new String(data, "UTF-8")));

            System.out.println("======jni-file-test======");
            data = AES.read("/mnt/sdcard/test.txt", System.currentTimeMillis());
            if (data != null) {
                System.out.println("read:" + new String(data, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
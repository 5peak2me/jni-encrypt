package com.jinlin.encryptutils.des;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.TextView;

import com.jinlin.encryptutils.R;

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
public class DESActivity extends AppCompatActivity {
    String data = "Jinlin";
    String key = "12345678";
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
        setContentView(R.layout.activity_des);
        assignViews();
        try {
            byte b[] = DES.desCrypt(data.getBytes(), key.getBytes(), 1);
            System.out.println("encrypt==>" + Base64.encodeToString(b, Base64.DEFAULT));
            tv1.setText(String.format("ndk encrypt==>%s", Base64.encodeToString(b, Base64.DEFAULT)));

            b = DES.desCrypt(b, key.getBytes(), 0);
            System.out.println("decrypt==>" + new String(b));
            tv2.setText(String.format("ndk decrypt==>%s", new String(b)));

            byte jb[] = DES.encrypt(key.getBytes(), data);
            System.out.println("java encrypt==>" + Base64.encodeToString(jb, Base64.DEFAULT));
            tv3.setText(String.format("java encrypt==>%s", Base64.encodeToString(jb, Base64.DEFAULT)));

            b = DES.decrypt(jb, key);
            tv4.setText(String.format("java decrypt==>%s", new String(b)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void quickSort(int array[], int start, int end) {
        if (start < end) {
            int i = start, j = end, x = array[start];
            while (i < j) {
                while (i < j && array[j] > x) {
                    --j;
                }
                if (i < j) {
                    array[i++] = array[j];
                }
                while (i < j && array[i] < x) {
                    ++i;
                }
                if (i < j) {
                    array[j--] = array[i];
                }
            }
            array[i] = x;
            quickSort(array, start, i - 1);
            quickSort(array, i + 1, end);
        }
    }
}

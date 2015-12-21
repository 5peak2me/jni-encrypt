package com.jinlin.encryptutils.base64;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.jinlin.encryptutils.R;

public class Base64Activity extends AppCompatActivity {
    private TextView et;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    private void assignViews() {
        et = (TextView) findViewById(R.id.et);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        // Base64 编码：
        byte [] encode = Base64.encode(et.getText().toString().getBytes(), Base64.DEFAULT);
        String enc = new String(encode);
        Log.d("Jinlin", "base 64 encode = " + enc);
        tv1.setText(String.format("java encode:%s", enc));

        // Base64 解码：
        byte [] result = Base64.decode(enc, Base64.DEFAULT);
        String res = new String(result);
        Log.d("Jinlin", "base 64 result = " + res);
        tv2.setText(String.format("java decode:%s", res));

        String s = com.jinlin.encryptutils.base64.Base64.encode(et.getText().toString());
        tv3.setText(String.format("NDK encode:%s", s));
        tv4.setText(String.format("NDK decode:%s", com.jinlin.encryptutils.base64.Base64.decode(s)));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base64);
        assignViews();
    }
}

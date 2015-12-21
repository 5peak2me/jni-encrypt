package com.jinlin.encryptutils.md5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.jinlin.encryptutils.R;

public class MD5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md5);

        // 待加密
        String strText = "J!nl!n";
        EditText et = (EditText) findViewById(R.id.et);
        et.setText(strText);

        // java
        final TextView tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setText(String.format("java:   %s", MD5.getMD5(strText)));
        // ndk c
        final TextView tv3 = (TextView) findViewById(R.id.tv3);
        tv3.setText(String.format("ndk c: %s", MD5.encryptByMD5(strText)));

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv2.setText(String.format("java:   %s", MD5.getMD5(s.toString())));
                tv3.setText(String.format("ndk c: %s", MD5.encryptByMD5(s.toString())));
            }
        });
    }
}


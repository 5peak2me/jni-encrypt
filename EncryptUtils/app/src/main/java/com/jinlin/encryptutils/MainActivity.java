package com.jinlin.encryptutils;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jinlin.encryptutils.aes.AESActivity;
import com.jinlin.encryptutils.base64.Base64Activity;
import com.jinlin.encryptutils.des.DESActivity;
import com.jinlin.encryptutils.md5.MD5Activity;

public class MainActivity extends ListActivity {
    private String[] mDatas = {"AES", "Base64", "DES", "MD5"};

    private Class[] mActivities = {AESActivity.class,Base64Activity.class,DESActivity.class, MD5Activity.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, mDatas);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(new Intent(this, mActivities[position]));
    }
}

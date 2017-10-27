package com.xxx.skynet.hellojni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.xxx.skynet.hellojni.ndk.HelloJni;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txt = (TextView)findViewById(R.id.text);
        txt.setText(HelloJni.getTxt()+HelloJni.getTotal());
    }
}

package com.airun.bigboss.kangservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegistrasiKangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registrasikang);
    }

    public void regis(View view) {
        Intent intent = new Intent(RegistrasiKangActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

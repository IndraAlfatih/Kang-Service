package com.airun.bigboss.kangservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginKangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loginkang);
    }

    public void login(View view) {
        Intent intent = new Intent(LoginKangActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void registext(View view) {
        Intent intent = new Intent(LoginKangActivity.this, RegistrasiActivity.class);
        startActivity(intent);
    }
}

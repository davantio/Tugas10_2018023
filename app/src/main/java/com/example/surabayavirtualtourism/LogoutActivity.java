package com.example.surabayavirtualtourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class LogoutActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("AndroidHiveLogin", 0);
        editor = preferences.edit();
        session = new SessionManager(getApplicationContext());

        Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
        session.setLogin(false);
        startActivity(intent);
        finish();
    }
}
package com.example.surabayavirtualtourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RekomPecel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekom_pecel);

        ImageView iv_kuliner = findViewById(R.id.iv_kuliner);
        iv_kuliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(RekomPecel.this, Kuliner.class);
                startActivity(a);
            }
        });
    }
}
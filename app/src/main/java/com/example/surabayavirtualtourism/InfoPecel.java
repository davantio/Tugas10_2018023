package com.example.surabayavirtualtourism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class InfoPecel extends AppCompatActivity {
    RecyclerView recyclerView;

    String s1[], s2[];
    int images[] =
            {R.drawable.rujak,R.drawable.rawon,R.drawable.tahutek,R.drawable.ltg_balap,R.drawable.ltg_kupang,R.drawable.pecel};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pecel);

        recyclerView = findViewById(R.id.rv_listkuliner);

        s1 = getResources().getStringArray(R.array.nama_kuliner);
        s2 = getResources().getStringArray(R.array.deskripsi_kuliner);
        KulinerAdapter appAdapter = new KulinerAdapter(this,s1,s2,images);
        recyclerView.setAdapter(appAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((this), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ImageView iv_kuliner = findViewById(R.id.iv_kuliner);
        iv_kuliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(InfoPecel.this, Kuliner.class);
                startActivity(a);
            }
        });
    }
}
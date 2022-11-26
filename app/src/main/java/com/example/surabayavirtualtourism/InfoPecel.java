package com.example.surabayavirtualtourism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.surabayavirtualtourism.databinding.ActivityInfoPecelBinding;

public class InfoPecel extends AppCompatActivity {
    private ActivityInfoPecelBinding binding;
    RecyclerView recyclerView;

    String s1[], s2[];
    int images[] =
            {R.drawable.rujak,R.drawable.rawon,R.drawable.tahutek,R.drawable.ltg_balap,R.drawable.ltg_kupang,R.drawable.pecel};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoPecelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();

        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueueUniqueWork(
                        "Notifikasi", ExistingWorkPolicy.REPLACE, request);
            }
        });

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
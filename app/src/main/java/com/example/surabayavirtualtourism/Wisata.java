package com.example.surabayavirtualtourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class Wisata extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata);
        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_wisata) {
                    Intent a = new Intent(Wisata.this,
                            Wisata.class);
                    startActivity(a);
                } else if (id == R.id.nav_kuliner) {
                    Intent a = new Intent(Wisata.this,
                            Kuliner.class);
                    startActivity(a);
                } else if (id == R.id.nav_penginapan) {
                    Intent a = new Intent(Wisata.this,
                            Penginapan.class);
                    startActivity(a);
                } else if (id == R.id.nav_alarm) {
                    Intent a = new Intent(Wisata.this,
                            MainActivity.class);
                    startActivity(a);
                }
                dl.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }
    //on back press behavior
    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}

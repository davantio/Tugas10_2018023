package com.example.surabayavirtualtourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.surabayavirtualtourism.databinding.ActivityQuotesBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Quotes extends AppCompatActivity implements View.OnClickListener{
    //declaration variable
    String index;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private ActivityQuotesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fetchButton.setOnClickListener(this);

        //drawer
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
                    Intent a = new Intent(Quotes.this,
                            Wisata.class);
                    startActivity(a);
                } else if (id == R.id.nav_kuliner) {
                    Intent a = new Intent(Quotes.this,
                            Kuliner.class);
                    startActivity(a);
                } else if (id == R.id.nav_penginapan) {
                    Intent a = new Intent(Quotes.this,
                            Penginapan.class);
                    startActivity(a);
                } else if (id == R.id.nav_alarm) {
                    Intent a = new Intent(Quotes.this,
                            MainActivity.class);
                    startActivity(a);
                }else if (id == R.id.nav_data) {
                    Intent a = new Intent(Quotes.this,
                            InputHotel.class);
                    startActivity(a);
                }else if (id == R.id.nav_quotes) {
                    Intent a = new Intent(Quotes.this,
                            Quotes.class);
                    startActivity(a);
                }else if (id == R.id.nav_logout) {
                    Intent a = new Intent(Quotes.this,
                            LogoutActivity.class);
                    startActivity(a);
                }
                dl.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    //onclik button fetch
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fetch_button){
            index = binding.inputId.getText().toString();
            try {
                getData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    //get data using api link
    public void getData() throws MalformedURLException {
        Uri uri = Uri.parse("https://dummyjson.com/quotes").buildUpon().build();
        URL url = new URL(uri.toString());
        new DOTask().execute(url);
    }

    class DOTask extends AsyncTask<URL, Void, String> {
        //connection request
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls [0];
            String data = null;
            try {
                data = NetworkUtils.makeHTTPRequest(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(String s){
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //get data json
        public void parseJson(String data) throws JSONException{
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(data);
            }catch (JSONException e){
                e.printStackTrace();
            }
            JSONArray cityArray = jsonObject.getJSONArray("quotes");
            for (int i =0; i <cityArray.length(); i++){
                JSONObject obj = cityArray.getJSONObject(i);
                String Sobj = obj.get("id").toString();
                if (Sobj.equals(index)){
                    String myid = obj.get("id").toString();
                    binding.resultId.setText(myid);
                    String myquote = obj.get("quote").toString();
                    binding.resultQuote.setText(myquote);
                    String myauthor = obj.get("author").toString();
                    binding.resultAuthor.setText(myauthor);
                    break;
                }
                else{
                    binding.resultId.setText("Not Found");
                    binding.resultQuote.setText("Not Found");
                    binding.resultAuthor.setText("Not Found");
                }
            }
        }
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

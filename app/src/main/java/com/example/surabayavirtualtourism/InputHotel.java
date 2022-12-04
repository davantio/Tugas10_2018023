package com.example.surabayavirtualtourism;

import static com.example.surabayavirtualtourism.DBMain.TABLENAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.example.surabayavirtualtourism.databinding.ActivityInputHotelBinding;
import java.io.ByteArrayOutputStream;

public class InputHotel extends AppCompatActivity {
    //drawer
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    //SQL
    private ActivityInputHotelBinding binding;
    DBMain dBmain;
    SQLiteDatabase sqLiteDatabase;
    int id = 0;

    public static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final int MY_STORAGE_REQUEST_CODE = 101;

    String cameraPermission[];
    String storagePermission[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                    Intent a = new Intent(InputHotel.this,
                            Wisata.class);
                    startActivity(a);
                } else if (id == R.id.nav_kuliner) {
                    Intent a = new Intent(InputHotel.this,
                            Kuliner.class);
                    startActivity(a);
                } else if (id == R.id.nav_penginapan) {
                    Intent a = new Intent(InputHotel.this,
                            Penginapan.class);
                    startActivity(a);
                } else if (id == R.id.nav_alarm) {
                    Intent a = new Intent(InputHotel.this,
                            MainActivity.class);
                    startActivity(a);
                }else if (id == R.id.nav_data) {
                    Intent a = new Intent(InputHotel.this,
                            InputHotel.class);
                    startActivity(a);
                }
                dl.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //SQLite
        dBmain = new DBMain(this);
        //findid();
        insertData();
        editData();

        binding.editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int avatar = 0;
                if (avatar == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromGallery();
                    }
                } else if (avatar == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
    }

    private void editData() {
        if (getIntent().getBundleExtra("userdata") != null) {
            Bundle bundle = getIntent().getBundleExtra("userdata");
            id = bundle.getInt("id");
            //for set name
            binding.editname.setText(bundle.getString("name"));
            binding.editstar.setText(bundle.getString("star"));
            binding.editlocation.setText(bundle.getString("location"));
            binding.editprice.setText(bundle.getString("price"));
            //for image
            byte[] bytes = bundle.getByteArray("avatar");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            binding.editimage.setImageBitmap(bitmap);
            //visible edit button and hide submit button
            binding.btnSubmit.setVisibility(View.GONE);
            binding.btnEdit.setVisibility(View.VISIBLE);
        }
    }

    private void requestStoragePermission() {
        requestPermissions(storagePermission, MY_STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    private void requestCameraPermission() {
        requestPermissions(cameraPermission, MY_CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void insertData() {
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("name", binding.editname.getText().toString());
                cv.put("star", binding.editstar.getText().toString());
                cv.put("avatar", imageViewToBy(binding.editimage));
                cv.put("location", binding.editlocation.getText().toString());
                cv.put("price", binding.editprice.getText().toString());

                sqLiteDatabase = dBmain.getWritableDatabase();
                Long rec = sqLiteDatabase.insert("hotel", null, cv);
                if (rec != null) {
                    Toast.makeText(InputHotel.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    binding.editname.setText("");
                    binding.editimage.setImageResource(R.mipmap.ic_launcher);
                    binding.editstar.setText("");
                    binding.editlocation.setText("");
                    binding.editprice.setText("");
                } else {
                    Toast.makeText(InputHotel.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //for view display
        binding.btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( InputHotel.this, DisplayData.class));
            }
        });

        //for storing new data or update data
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("name",binding.editname.getText().toString());
                cv.put("star",binding.editstar.getText().toString());
                cv.put("location",binding.editlocation.getText().toString());
                cv.put("price",binding.editprice.getText().toString());
                cv.put("avatar", imageViewToBy(binding.editimage));

                sqLiteDatabase = dBmain.getWritableDatabase();
                long recedit = sqLiteDatabase.update(TABLENAME,cv,"id="+id, null);
                if(recedit != -1){
                    Toast.makeText(InputHotel.this, "Update Succesfully", Toast.LENGTH_SHORT).show();
                    //clear data adfte submit
                    binding.editname.setText("");
                    binding.editstar.setText("");
                    binding.editlocation.setText("");
                    binding.editprice.setText("");
                    binding.editimage.setImageResource(R.mipmap.ic_launcher);

                    //edit hide and submit visible
                    binding.btnEdit.setVisibility(View.GONE);
                    binding.btnSubmit.setVisibility(View.VISIBLE);
                    Intent a = new Intent(InputHotel.this, DisplayData.class);
                    startActivity(a);
                }
            }
        });
    }

    public static byte[] imageViewToBy(ImageView avatar) {
        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0]   == PackageManager.PERMISSION_GRANTED;
                    boolean storage_accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accepted && storage_accepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "enable camera and storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case MY_STORAGE_REQUEST_CODE: {
                boolean storage_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (storage_accepted) {
                    pickFromGallery();
                } else {
                    Toast.makeText(this, "please enable storage permission", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(binding.editimage);
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
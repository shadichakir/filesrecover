package justforyou.dev.com.filesrecover;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;


public class VideoFolder extends AppCompatActivity {
    private InterstitialAd interstitial;
    Adapter_VideoFolder obj_adapter;
    ArrayList al_video = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private static final int REQUEST_PERMISSIONS = 100;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_video_folder);
            init();
            //Admob

            AdRequest adRequest = new AdRequest.Builder().build();
            // Prepare the Interstitial Ad
            AdView mAdView = findViewById(R.id.ad_view);
            mAdView.loadAd(adRequest);
            interstitial = new InterstitialAd(VideoFolder.this);
            interstitial.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
            interstitial.loadAd(adRequest);
            interstitial.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    displayInterstitial();
                }
            });

            if (ContextCompat.checkSelfPermission(VideoFolder.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            } else {
                Intent i = new Intent(VideoFolder.this,MainActivity.class);
                startActivity(i);
            }
            try {
                btn1 = findViewById(R.id.btn1);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(),LoadingActivity.class);
                        startActivity(i);
                    }
                });
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    private void init(){

        try {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
            recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);
            fn_checkpermission();
        } catch (Exception e) {
        }
    }
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
    private void fn_checkpermission(){
        /*RUN TIME PERMISSIONS*/

        try {
            if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                if ((ActivityCompat.shouldShowRequestPermissionRationale(VideoFolder.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(VideoFolder.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE))) {

                } else {
                    ActivityCompat.requestPermissions(VideoFolder.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }
            }else {
                Log.e("Else","Else");
                fn_video();
            }
        } catch (Exception e) {
        }
    }




    public void fn_video() {

        try {
            int int_position = 0;
            Uri uri;
            Cursor cursor;
            int column_index_data, column_index_folder_name,column_id,thum;

            String absolutePathOfImage = null;
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,MediaStore.Video.Media._ID,MediaStore.Video.Thumbnails.DATA};

            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
            cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
            column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);
                Log.e("Column", absolutePathOfImage);
                Log.e("Folder", cursor.getString(column_index_folder_name));
                Log.e("column_id", cursor.getString(column_id));
                Log.e("thum", cursor.getString(thum));

                Model_Video obj_model = new Model_Video();
                obj_model.setBoolean_selected(false);
                obj_model.setStr_path(absolutePathOfImage);
                obj_model.setStr_thumb(cursor.getString(thum));

                al_video.add(obj_model);

            }


            obj_adapter = new Adapter_VideoFolder(getApplicationContext(),al_video,VideoFolder.this);
            recyclerView.setAdapter(obj_adapter);
        } catch (IllegalArgumentException e) {
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        try {
            switch (requestCode) {
                case REQUEST_PERMISSIONS: {
                    for (int i = 0; i < grantResults.length; i++) {                     if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_video();
                    } else {
                        Toast.makeText(VideoFolder.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                    }
                }
            }
        } catch (Exception e) {

        }
    }
}
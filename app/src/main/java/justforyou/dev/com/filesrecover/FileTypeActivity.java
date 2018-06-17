package justforyou.dev.com.filesrecover;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.FileObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class FileTypeActivity extends AppCompatActivity {
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    Button btn1, btn2;
    Button btn3, btn4,btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_type);
        //Admob

        AdRequest adRequest = new AdRequest.Builder().build();
        // Prepare the Interstitial Ad
        AdView mAdView = findViewById(R.id.ad_view);
        mAdView.loadAd(adRequest);
        interstitial = new InterstitialAd(FileTypeActivity.this);
        interstitial.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                displayInterstitial();
            }
        });
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(FileTypeActivity.this,PhotosActivity.class);
               startActivity(i);
            }
        });
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FileTypeActivity.this,VideoFolder.class);
                startActivity(i);
            }
        });
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FileTypeActivity.this,LoadingActivity.class);
                startActivity(i);
            }
        });
        btn4= findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FileTypeActivity.this,LoadingActivity.class);
                startActivity(i);
            }
        });
        btn5= findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FileTypeActivity.this,LoadingActivity.class);
                startActivity(i);
            }
        });
        btn1.startAnimation(myAnim);
        btn2.startAnimation(myAnim);
        btn3.startAnimation(myAnim);
        btn4.startAnimation(myAnim);
        btn5.startAnimation(myAnim);
    }

    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
}

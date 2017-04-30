package com.example.convergeconsult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBDefaultValues.initializeValues(this);

        Button dipButton = (Button) findViewById(R.id.dipSelectorButton);
        Button densityButton = (Button) findViewById(R.id.densitySelectorButton);
        dipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DipActivity.class);
                startActivity(intent);
            }
        });
        densityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DensityActivity.class);
                startActivity(intent);
            }
        });
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5276594353990169~2024613538");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}

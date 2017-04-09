package com.example.convergeconsult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}

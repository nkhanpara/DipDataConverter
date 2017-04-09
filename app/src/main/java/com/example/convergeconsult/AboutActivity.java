package com.example.convergeconsult;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by khanpar on 3/18/17.
 */

public class AboutActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        TextView textView = (TextView) findViewById(R.id.textView5);
        textView.setText(R.string.detail);
    }
}
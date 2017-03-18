package com.example.convergeconsult;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by khanpar on 3/18/17.
 */


public class DisplayDataActivity extends AppCompatActivity {
    private TextView dipDataTextview;
    private String kl10data = "";
    private String kl15data = "";
    private String kl20data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        for (Map.Entry<String, String> entry : DBDefaultValues.dipData_10KLmap.entrySet()) {
            kl10data += "\n" + entry.getKey() + " - " + entry.getValue();
        }
        for (Map.Entry<String, String> entry : DBDefaultValues.dipData_15KLmap.entrySet()) {
            kl15data += "\n" + entry.getKey() + " - " + entry.getValue();
        }
        for (Map.Entry<String, String> entry : DBDefaultValues.dipData_20KLmap.entrySet()) {
            kl20data += "\n" + entry.getKey() + " - " + entry.getValue();
        }
        dipDataTextview = (TextView) findViewById(R.id.dipDataTextview);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.KL_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        dipDataTextview.setText(kl10data);
                        break;
                    case 1:
                        dipDataTextview.setText(kl15data);
                        break;
                    case 2:
                        dipDataTextview.setText(kl20data);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dipDataTextview.setText("Please select the KL data");
            }
        });
    }
}

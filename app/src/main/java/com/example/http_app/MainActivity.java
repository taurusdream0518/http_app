package com.example.http_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Button buttonThongSpeak;
    private Button buttonPhp;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        buttonThongSpeak = (Button) findViewById(R.id.button_thingSpeak);
        buttonPhp = (Button) findViewById(R.id.button_PHP);
        buttonThongSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, ThingSpeakActivity.class);
                startActivity(intent);
            }
        });

        buttonPhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, PHPActivity.class);
                startActivity(intent);
            }
        });



    }
}
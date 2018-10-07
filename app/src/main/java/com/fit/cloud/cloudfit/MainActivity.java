package com.fit.cloud.cloudfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button walking = (Button) findViewById(R.id.walking);
        Button jogging = (Button) findViewById(R.id.jogging);
        Button swimming = (Button) findViewById(R.id.swimming);
        Button cycling = (Button) findViewById(R.id.cycling);

        Button diary = (Button) findViewById(R.id.diary);

        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Diary.class));
            }
        });
        walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Walking.class));
            }
        });
        jogging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Jogging.class));
            }
        });
        cycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Cycling.class));
            }
        });
        swimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Swimming.class));
            }
        });





    }
}

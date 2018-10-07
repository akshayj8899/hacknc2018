package com.fit.cloud.cloudfit;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity {

    public enum Workout {
        JOGGING, CYCLING, SWIMMING, WALKING;
    }

    private void createWorkout(final String username, final int weight, final int time, final int distance, final Workout work,
                                  final Consumer<Boolean> callback) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://silicon-alchemy-218616.appspot.com/db";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                   @RequiresApi(api = Build.VERSION_CODES.N)
                   @Override
                    public void onResponse(String response) {
                       callback.accept(response.equals("true"));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "insertWorkout");
                Map<String, Object> workout = new HashMap<>();
                double speed = (distance / time);
                double calorieFactor;
                switch (work) {
                    case CYCLING:
                        calorieFactor = speed * 2;
                        break;
                    case JOGGING:
                        calorieFactor = speed * 1.5;
                        break;
                    case SWIMMING:
                        calorieFactor = speed * 2.1;
                        break;
                    case WALKING:
                        calorieFactor = speed * 1.2;
                        break;
                    default:
                        calorieFactor = 0;
                }
                workout.put("username", username);
                workout.put("time", time);
                workout.put("activity", work.name());
                workout.put("calories", (int) (weight * calorieFactor));
                workout.put("distance", distance);

                SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
                workout.put("date", format.format(new Date()));

                params.put("info", new JSONObject(workout).toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }

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


        add.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Log.e("ERRO", "IN ON CLICK");
                createWorkout("aksja6", 115, 120, 20, Workout.JOGGING, new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(success ? "Succeeded" : "Failed");
                        builder.create().show();
                    };
                });
            }
        });


    }
}

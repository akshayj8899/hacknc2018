package com.fit.cloud.cloudfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Diary extends AppCompatActivity {

    final String user = "aksja6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);



        //Working Get Request
        final TextView mTextView = (TextView) findViewById(R.id.text);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://silicon-alchemy-218616.appspot.com/db?user=aksja6";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            try {
                                JSONParser parser = new JSONParser();
                                JSONObject mainObject = new JSONObject(response);
                                JSONObject uniObject = mainObject.getJSONObject("aksja6");
                                String firstname = uniObject.getString("firstname");
                                String lastname = uniObject.getString("lastname");
                                String weight = uniObject.getString("lastname");
                                String height = uniObject.getString("height");
                                JSONObject workouts = uniObject.getJSONObject("workouts");
                                

                                //JSONObject jObject = (JSONObject) parser.parse(response);
                                mTextView.setText(aJsonString);
                                //mTextView.setText(aJsonString);
                            }
                            catch(Exception e)
                            {
                                e.getStackTrace();
                                mTextView.setText("RIP");
                            }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
}

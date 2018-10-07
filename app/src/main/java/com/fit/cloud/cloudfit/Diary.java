package com.fit.cloud.cloudfit;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Diary extends AppCompatActivity {

    final String user = "aksja6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);



        Button back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Diary.this, MainActivity.class));
            }
        });

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
                                String weight = uniObject.getString("weight");
                                String height = uniObject.getString("height");
                                JSONObject workouts = uniObject.getJSONObject("workouts");

                                HashMap<String, String> parsedworkouts = new HashMap<>();

                                for (int i = 0; i < workouts.length(); i++) {
                                    JSONObject workout = workouts.getJSONObject("" + i);
                                    String date = workout.getString("date");
                                    String activity = workout.getString("activity");
                                    String distance = workout.getString("distance");
                                    String time = workout.getString("time");
                                    String calories = workout.getString("calories");

                                    if(activity.equals("jogging"))
                                    {
                                        activity = "Jogging";
                                    }
                                    if(activity.equals("cycling"))
                                    {
                                        activity = "Cycling";
                                    }
                                    if(activity.equals("walking"))
                                    {
                                        activity = "Walking";
                                    }
                                    if(activity.equals("swimming"))
                                    {
                                        activity = "Swimming";
                                    }
                                    parsedworkouts.put(activity, "Distance: " + distance + " miles, Time: " + time + " min, Calories: " + calories);
                                }


                                ListView listView = (ListView) findViewById(R.id.results_listview);


                                List<HashMap<String, String>> listItems = new ArrayList<>();
                                SimpleAdapter adapter = new SimpleAdapter(Diary.this, listItems, R.layout.list_item,
                                        new String[]{"First Line", "Second Line"},
                                        new int[]{R.id.text1, R.id.text2});


                                Iterator it = parsedworkouts.entrySet().iterator();
                                while (it.hasNext())
                                {
                                    HashMap<String, String> resultsMap = new HashMap<>();
                                    Map.Entry pair = (Map.Entry)it.next();
                                    resultsMap.put("First Line", pair.getKey().toString());
                                    resultsMap.put("Second Line", pair.getValue().toString());
                                    listItems.add(resultsMap);
                                }

                                listView.setAdapter(adapter);





                                //JSONObject jObject = (JSONObject) parser.parse(response);
                                //mTextView.setText("First Name: " + firstname + "\nLast Name: " + lastname + "\nWeight: " + weight + "\nHeight: " + height + "\n");

                                //mTextView.setText(aJsonString);
                            }
                            catch(Exception e)
                            {
                                e.getStackTrace();
                                //mTextView.setText("RIP");
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

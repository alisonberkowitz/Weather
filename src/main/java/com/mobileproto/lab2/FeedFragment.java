package com.mobileproto.lab2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

/**
 * Created by amclaughlin on 10/14/13.
 */
//@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FeedFragment extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.activity_main, null);


        AsyncTask utahraptor = new AsyncTask<Void, Void, ArrayList<FeedItem>>(){
            protected ArrayList<FeedItem> doInBackground(Void... voids){

                ArrayList<FeedItem> twits = new ArrayList<FeedItem>();
                ArrayList<FeedItem> middle = new ArrayList<FeedItem>();
                String gatsby = "";

                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
//        HttpGet pull = new HttpGet("http://twitterproto.herokuapp.com/tweets");
                HttpGet pull = new HttpGet("http://api.wunderground.com/api/dd14b81695901028/hourly/q/MA/Needham.json");
                pull.setHeader("Content-type","application/json");

                try{
                    HttpResponse httpResponse = httpClient.execute(pull);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    InputStream is = httpEntity.getContent();

                    BufferedReader read = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
                    StringBuilder build = new StringBuilder();

                    String line;
                    String nl = System.getProperty("line.separator");
                    while ((line = read.readLine()) != null) {
                        build.append(line + nl);
                    }

                    gatsby = build.toString();

                }



                catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject ntweet = new JSONObject(gatsby);
                    JSONArray list = ntweet.getJSONArray("hourly_forecast");

                    for (int i=0; i<list.length(); i++){
                        FeedItem swag = new FeedItem("5 pm feels like " + list.getJSONObject(i).getJSONObject("feelslike").getString("english"), list.getJSONObject(i).getString("condition"));
                        Log.d(swag.toString(), "hello");
                        if (list.getJSONObject(i).getJSONObject("FCTTIME").getString("pretty").contains("5:00 PM")){
                            twits.add(swag);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return twits;

            }

            protected void onPostExecute(ArrayList<FeedItem> twits) {
                Log.d("couldbehere", "yes");
                FeedListAdapter feedListAdapter = new FeedListAdapter(getApplicationContext(), twits);
                ListView wList = (ListView) findViewById(R.id.weatherList);
                wList.setAdapter(feedListAdapter);
                Log.d("itishere","yes");
            }

        }.execute();

         /*
         * Creating some sample test data to see what the layout looks like.
         * You should eventually delete this.
         */
//        FeedItem item1 = new FeedItem("@TimRyan", "Dear reader, you are reading.");
//        FeedItem item2 = new FeedItem("@EvanSimpson", "Hey @TimRyan");
//        FeedItem item3 = new FeedItem("@JulianaNazare", "Everything happens so much.");
//        FeedItem item4 = new FeedItem("@reyner", "dGhlIGNvb2wgbmV3IHRoaW5nIHRvIGRvIGlzIGJhc2U2NCBlY29kZSB5b3VyIHR3ZWV0cw==");
//        List<FeedItem> sampleData = new ArrayList<FeedItem>();
//        sampleData.add(item1);
//        sampleData.add(item2);
//        sampleData.add(item3);
//        sampleData.add(item4);
//
//        // Set up the ArrayAdapter for the feedList
//        FeedListAdapter feedListAdapter = new FeedListAdapter(this.getActivity(), sampleData);
//        ListView feedList = (ListView) v.findViewById(R.id.feedList);
//        feedList.setAdapter(feedListAdapter);


        return v;

    }
}

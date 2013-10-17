package com.mobileproto.lab2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    public NotesDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new NotesDbHelper(this);

        final TextView notifyHour = (TextView) findViewById(R.id.notifyFieldHour);

        final TextView notifyMinutes = (TextView) findViewById(R.id.notifyFieldMinutes);

        final TextView startHour = (TextView) findViewById((R.id.startFieldHour));

        final TextView startMinutes = (TextView) findViewById((R.id.startFieldMinutes));

        final TextView endHour = (TextView) findViewById(R.id.endFieldHour);

        final TextView endMinutes = (TextView) findViewById(R.id.endFieldMinutes);


        List<String> files = new ArrayList<String>(Arrays.asList(fileList()));

        final NoteListAdapter aa = new NoteListAdapter(this, android.R.layout.simple_list_item_1, files);

        //final ListView notes = (ListView) findViewById(R.id.noteList);

        //notes.setAdapter(aa);

        final Button showWeather = (Button) findViewById(R.id.showWeather);
        //Button save = (Button)findViewById(R.id.saveButton);

        showWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                FeedItem swag = new FeedItem(startHour.getText()+" pm feels like " + list.getJSONObject(i).getJSONObject("feelslike").getString("english"), list.getJSONObject(i).getString("condition"));
                                Log.d(swag.toString(), "hello");
                                int cut = startHour.toString().indexOf(":");
                                int dHour = list.getJSONObject(i).getJSONObject("feelslike").getString("english").indexOf(":");
                                if (list.getJSONObject(i).getJSONObject("FCTTIME").getString("pretty").contains(startHour.getText()+":00 PM")){
                                    if (dHour == cut) {
                                        twits.add(swag);
                                    }
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
            }
        });

        final Button notifyButton = (Button) findViewById(R.id.notifyButton);

        notifyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                notification();
            }
        });
/*

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer notifyHourTime = Integer.parseInt(notifyHour.getText().toString());
                Integer notifyMinutesTime = Integer.parseInt(notifyMinutes.getText().toString());
                Integer startHourTime = Integer.parseInt(startHour.getText().toString());
                Integer startMinutesTime = Integer.parseInt(startMinutes.getText().toString());
                Integer endHourTime = Integer.parseInt(endHour.getText().toString());
                Integer endMinutesTime = Integer.parseInt(endMinutes.getText().toString());
                if (notifyHourTime != null && startHourTime != null && endHourTime != null){
                    */
/*try{
                        FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                        fos.write(noteText.getBytes());
                        fos.close();
                        title.setText("");
                        note.setText("");
                        aa.insert(fileName,0);
                        aa.notifyDataSetChanged();*//*


                        // Gets the data repository in write mode
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
                        ContentValues values = new ContentValues();
                        values.put(NotesDbHelper.FeedEntry.COLUMN_NAME_NOTIFY_HOUR, notifyHourTime);
                        values.put(NotesDbHelper.FeedEntry.COLUMN_NAME_NOTIFY_MINUTES, notifyMinutesTime);
                        values.put(NotesDbHelper.FeedEntry.COLUMN_NAME_START_HOUR, startHourTime);
                        values.put(NotesDbHelper.FeedEntry.COLUMN_NAME_START_MINUTES, startMinutesTime);
                        values.put(NotesDbHelper.FeedEntry.COLUMN_NAME_END_HOUR, endHourTime);
                        values.put(NotesDbHelper.FeedEntry.COLUMN_NAME_END_MINUTES, endMinutesTime);

// Insert the new row, returning the primary key value of the new row
                        long newRowId;
                        newRowId = db.insert(
                                NotesDbHelper.FeedEntry.TABLE_NAME,
                                null,
                                values);

                    //aa.insert(fileName,0);
                    //aa.notifyDataSetChanged();

*/
/*                    }catch (IOException e){
                        Log.e("IOException", e.getMessage());*//*


                }
            }
        });



        save.setFocusable(false);
*/

/*        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final TextView title = (TextView) view.findViewById(R.id.titleTextView);
                String fileName = title.getText().toString();
                Intent in = new Intent(getApplicationContext(), NoteDetailActivity.class);
                in.putExtra("file", fileName);
                startActivity(in);

            }

        });*/
    }
    public void notification() {
        Notification.Builder mBuilder = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.raincheck_logo)
                        .setContentTitle("Rain Check")
                        .setContentText("Go check the weather!")
                        .setAutoCancel(true);
                         // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);


        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



}

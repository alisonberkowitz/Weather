package com.mobileproto.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
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


        Button save = (Button)findViewById(R.id.saveButton);

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
                    /*try{
                        FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                        fos.write(noteText.getBytes());
                        fos.close();
                        title.setText("");
                        note.setText("");
                        aa.insert(fileName,0);
                        aa.notifyDataSetChanged();*/

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

/*                    }catch (IOException e){
                        Log.e("IOException", e.getMessage());*/

                }
            }
        });

        save.setFocusable(false);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}

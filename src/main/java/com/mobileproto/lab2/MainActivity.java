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

        final TextView notify = (TextView) findViewById(R.id.notifyField);

        final TextView start = (TextView) findViewById((R.id.startField));

        final TextView end = (TextView) findViewById(R.id.endField);


        List<String> files = new ArrayList<String>(Arrays.asList(fileList()));

        final NoteListAdapter aa = new NoteListAdapter(this, android.R.layout.simple_list_item_1, files);

        //final ListView notes = (ListView) findViewById(R.id.noteList);

        //notes.setAdapter(aa);


        Button save = (Button)findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer notifyTime = Integer.parseInt(notify.getText().toString());
                Integer startTime = Integer.parseInt(start.getText().toString());
                Integer endTime = Integer.parseInt(end.getText().toString());
                if (notifyTime != null && startTime != null && endTime != null){
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
                        values.put(NotesDbHelper.FeedEntry.COLUMN_NAME_NOTIFY, notifyTime);
                        values.put(NotesDbHelper.FeedEntry.COLUMN_NAME_START, startTime);
                        values.put(NotesDbHelper.FeedEntry.COLUMN_NAME_END, endTime);

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

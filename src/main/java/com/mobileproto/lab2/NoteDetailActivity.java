package com.mobileproto.lab2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by evan on 9/15/13.
 */
public class NoteDetailActivity extends Activity {

    public SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        Intent intent = getIntent();

        String fileName = intent.getStringExtra("file");

        TextView title = (TextView) findViewById(R.id.noteTitle);
        TextView noteText = (TextView) findViewById(R.id.noteText);

        title.setText(fileName);

        //replace this with database stuff. when you click on a note that you already made
        StringBuilder fileText = new StringBuilder();
        try{
            //opening the database
            db = openOrCreateDatabase("notes.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);

            //defining cursor and select all rows from table named "notes"
            Cursor c = db.rawQuery("SELECT * FROM notes WHERE title=\"" + fileName +"\"",null);

            c.moveToFirst();
             String item = c.getString(3);
                    c.getColumnIndexOrThrow(NotesDbHelper.FeedEntry._ID)
            ;

            fileText.append(item);

/*            FileInputStream fis = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null){
                fileText.append(line);
                fileText.append('\n');
            }*/

        }catch (SQLException e){
            Log.e("SQLException", e.getMessage());
        }

        noteText.setText(fileText.toString());

    }
}

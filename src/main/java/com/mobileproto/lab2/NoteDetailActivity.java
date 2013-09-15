package com.mobileproto.lab2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by evan on 9/15/13.
 */
public class NoteDetailActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail_view_layout);

        Intent intent = getIntent();

        String fileName = intent.getStringExtra("file");

        TextView title = (TextView) findViewById(R.id.titleText);
        TextView noteText = (TextView) findViewById(R.id.noteText);

        title.setText(fileName);

        try{
            FileInputStream fis = openFileInput(fileName);

        }catch (IOException e){
            Log.e("IOException", e.getMessage());
        }

    }
}

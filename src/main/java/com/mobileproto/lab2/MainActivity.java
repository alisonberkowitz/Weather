package com.mobileproto.lab2;

import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView title = (TextView) findViewById(R.id.titleField);


        final TextView note = (TextView) findViewById(R.id.noteField);


        List<String> files = new ArrayList<String>(Arrays.asList(fileList()));

        final NoteListAdapter aa = new NoteListAdapter(this, android.R.layout.simple_list_item_1, files);

        final ListView notes = (ListView) findViewById(R.id.noteList);

        notes.setAdapter(aa);



        Button save = (Button)findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = title.getText().toString();
                String noteText = note.getText().toString();
                if (fileName != null && noteText != null){
                    try{
                        FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                        fos.write(noteText.getBytes());
                        fos.close();
                        title.setText("");
                        note.setText("");
                        aa.insert(fileName,0);
                        aa.notifyDataSetChanged();
                    }catch (IOException e){
                        Log.e("IOException", e.getMessage());
                    }
                }
            }
        });

        save.setFocusable(false);

        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final TextView title = (TextView) view.findViewById(R.id.titleTextView);
                String fileName = title.getText().toString();
                Intent in = new Intent(getApplicationContext(), NoteDetailActivity.class);
                in.putExtra("file", fileName);
                startActivity(in);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

package com.mobileproto.lab2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by evan on 9/15/13.
 */
public class NoteListAdapter extends ArrayAdapter {

    private List<String> data;
    private Activity activity;

    public NoteListAdapter(Activity a, int viewResourceId, List<String> data){
        super(a, viewResourceId, data);
        this.data = data;
        this.activity = a;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v==null){
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.note_list_item, null);
        }

        ImageButton del = (ImageButton) v.findViewById(R.id.deleteButton);
        final TextView name = (TextView) v.findViewById(R.id.titleTextView);
        name.setText(data.get(position));

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = name.getText().toString();
                activity.deleteFile(fileName);
                data.remove(position);
                NoteListAdapter.this.notifyDataSetChanged();
            }
        });

        return v;
    }
}

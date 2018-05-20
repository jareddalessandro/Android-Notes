package edu.pima.jdalessandro.notes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, int resource, ArrayList<Note> notes) {
        super(context, resource, notes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // return super.getView(position, convertView, parent);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, null);

        }

        Note note = getItem(position);

        //Choosing to not write date, but could by using setText()
        if(note != null) {
            TextView title = (TextView) convertView.findViewById(R.id.list_note_title);
            //TextView date = (TextView) convertView.findViewById(R.id.list_note_date);
            TextView content = (TextView) convertView.findViewById(R.id.list_note_content);

            // Set the title for the listview item
            title.setText(note.getTitle());

            // Set the content, if it's long only take part of it

            if(note.getContent().trim().length() > 10) {
                content.setText(note.getContent().trim().substring(0, 10) + "...");
            }
            else {
                content.setText(note.getContent());
            }

        }

        return convertView;
    }
}

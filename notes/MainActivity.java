package edu.pima.jdalessandro.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AdView adView;
    ListView notesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesListView = (ListView) findViewById(R.id.notesListView);

        // ADS
        adView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_create_new:
                startActivity(new Intent(this, createNoteActivity.class));
                break;
        }
        return true;
    }

    @Override
    // May be wrong method
    protected void onResume() {
        super.onResume();
        notesListView.setAdapter(null);

        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);

        if(notes == null || notes.size() == 0) {
            Toast.makeText(this, "No Saved Notes", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            NoteAdapter na = new NoteAdapter(this, R.layout.item_note, notes);
            notesListView.setAdapter(na);

            notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //run the NoteActivity in view/edit mode
                    String fileName = ((Note) notesListView.getItemAtPosition(position)).getDateTime()
                            + Utilities.FILE_EXTENSION;
                    Intent viewNoteIntent = new Intent(getApplicationContext(), createNoteActivity.class);
                    viewNoteIntent.putExtra(Utilities.EXTRAS_NOTE_FILENAME, fileName);
                    startActivity(viewNoteIntent);
                }
            });

        }
    }
}

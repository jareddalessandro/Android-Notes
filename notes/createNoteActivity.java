package edu.pima.jdalessandro.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class createNoteActivity extends AppCompatActivity {

    private AdView adView;

    private EditText noteTitle;
    private EditText noteContent;
    private String noteFileName;
    private Note loadedNote;
    private long noteCreationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        // ADS
        adView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        noteTitle = (EditText) findViewById(R.id.noteTitle);
        noteContent = (EditText) findViewById(R.id.noteContent);

        noteFileName = getIntent().getStringExtra("EXTRAS_NOTE_FILENAME");
        if(noteFileName != null && !noteFileName.isEmpty() && noteFileName.endsWith(Utilities.FILE_EXTENSION)) {
            loadedNote = Utilities.getNoteByName(getApplicationContext(), noteFileName);

            if(loadedNote != null) {
                noteTitle.setText(loadedNote.getTitle());
                noteContent.setText(loadedNote.getContent());
                noteCreationTime = loadedNote.getDateTime();
            }
            else {
                noteCreationTime = System.currentTimeMillis();
            }
        }

    }

    // ******************* ADD THE MENU ITEMS *************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_create_save:
                saveNote();
                break;

            case R.id.action_create_delete:
                deleteNote();
                break;
        }
        return true;
    }

// *********************** METHODS *******************************************

    private void saveNote(){
        Note note;
        String title = noteTitle.getText().toString();
        String content = noteContent.getText().toString();

        if(title.trim().isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please enter Title & Content", Toast.LENGTH_SHORT).show();
            return;
        }

        if(loadedNote != null) {
            noteCreationTime = loadedNote.getDateTime();
        }
        else {
            noteCreationTime = System.currentTimeMillis();
        }

        if(Utilities.saveNote(this, new Note(noteCreationTime, title, content))) {
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void deleteNote() {
        Note note;
        if(loadedNote == null) {
            finish();
        }
        else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete?")
                    .setMessage("About to delete: " + noteTitle.getText().toString() + ": are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utilities.deleteNote(getApplicationContext(),
                                    loadedNote.getDateTime() + Utilities.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext(),
                                    noteTitle.getText().toString() + " is deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setCancelable(false);

            dialog.show();

        }

    }
}

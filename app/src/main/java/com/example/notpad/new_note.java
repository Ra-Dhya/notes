package com.example.notpad;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class new_note extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton nav, save;
    private EditText title_edittext, description_edittext;
    private notes_DB notesDb;
    private boolean isEdit = false;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        // Initialize Views and database
        toolbar = findViewById(R.id.toolbar);
        save = findViewById(R.id.save);
        title_edittext = findViewById(R.id.title);
        description_edittext = findViewById(R.id.description);
        nav = findViewById(R.id.navigate);
        notesDb = new notes_DB(this);

        //set toolbar through support action bar
        setSupportActionBar(toolbar);



        // set navigation button to click listener
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        // check if operation is edit or not
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            isEdit = true;
            id = intent.getIntExtra("id", -1);
            String title = intent.getStringExtra("title_view");
            String description = intent.getStringExtra("description_view");
            title_edittext.setText(title);
            description_edittext.setText(description);
        }

        // save data on click listener
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit) {
                    // save note if not in edit mode
                    saveNote();
                } else {
                    // save note if  in edit mode
                    update();
                    Toast.makeText(new_note.this, "Edit successful", Toast.LENGTH_SHORT).show();
                }
                setResult(Activity.RESULT_OK);
                finish();
            }
        });


    }


    private void saveNote() {

        String title = title_edittext.getText().toString().trim();
        String description = description_edittext.getText().toString().trim();


        if (!title.isEmpty() && !description.isEmpty()) {
            notes_model note = new notes_model();
            note.setTitle(title);
            note.setDescription(description);
            if (notesDb != null) {
                notesDb.addNote(note);
            }
            finish();
        } else {
            Toast.makeText(this, "plz enter data", Toast.LENGTH_SHORT).show();
        }
    }

    private void update() {

        String title = title_edittext.getText().toString().trim();
        String description = description_edittext.getText().toString().trim();

        if (!title.isEmpty() && !description.isEmpty()) {
            notes_model note = new notes_model();
            note.setTitle(title);
            note.setId(id);
            note.setDescription(description);
            if (notesDb != null) {
                notesDb.updateNote(note);
            }
            finish();
        } else {
            Toast.makeText(this, "plz enter data", Toast.LENGTH_SHORT).show();
        }
    }

}
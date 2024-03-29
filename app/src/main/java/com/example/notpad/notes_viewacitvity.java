package com.example.notpad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class notes_viewacitvity extends AppCompatActivity {

    private TextView title_textview, description_textview;
    private ImageButton navigate, edit, delete;
    private AlertDialog.Builder alertDialog;
    int id;
    notes_DB notesDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_viewacitvity);

        //Initialize
        title_textview = findViewById(R.id.title_textview);
        description_textview = findViewById(R.id.descripiton_textview);
        navigate = findViewById(R.id.navigation_viewnote);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);






        //get data through intent
        Intent getdata = getIntent();
         id = getdata.getIntExtra("id", 0);
        String title = getdata.getStringExtra("title");
        String Description = getdata.getStringExtra("Description");
        title_textview.setText(title);
        description_textview.setText(Description);

        // set click on listener on navigate
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // set click on listener on navigate
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), new_note.class);
                intent.putExtra("title_view", title);
                intent.putExtra("id", id);
                intent.putExtra("description_view", Description);
                startActivity(intent);
                finish();
            }
        });

        //set click on listener on navigate
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 notesDb = new notes_DB(getApplication());
                 Alert();
                alertDialog.create();
               alertDialog.show();

            }
        });

    }

    public void Alert() {
        alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle(" ! Delete notes")
                .setMessage("sure you want to delete nots?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notesDb.deleteNote(id);
                        onBackPressed();
                        Toast.makeText(notes_viewacitvity.this, "notes delete successful", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false);

    }


}
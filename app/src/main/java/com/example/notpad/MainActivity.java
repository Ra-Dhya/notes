package com.example.notpad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements nots_recycler_clickListener {

    FloatingActionButton floatingActionButton;
    Recycler_view_Adapter recyclerViewAdapter;
    RecyclerView recyclerView;
    notes_DB notesDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise  views and database
        floatingActionButton = findViewById(R.id.newitem_floating);
        recyclerView = findViewById(R.id.Recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesDb = new notes_DB(this);

        //set click listener on floating button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, new_note.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //load notes when activity resume
        loadNotes();
    }

    //load notes from database and display inside of recycler view
    private void loadNotes() {
        List<notes_model> notes = notesDb.getAllNotes();
        recyclerViewAdapter = new Recycler_view_Adapter(this, notes, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    // It method is called when click on item of recycler and perform action
    @Override
    public void onItemClick(notes_model item) {

       int  id = item.getId();
        Intent intent = new Intent(this, notes_viewacitvity.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("id", id);
        intent.putExtra("Description", item.getDescription());
        startActivity(intent);
    }


}
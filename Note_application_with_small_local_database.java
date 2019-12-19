package com.arashad96.andoriddeveloperadvancedkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Note_application_with_small_local_database extends AppCompatActivity {
    Button github;
    Button info;
    ListView notes;
    static ArrayList<String> arrayListnote;
    static ArrayAdapter<String> adapter;
    static boolean flag ;
    static int pos;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.note:
                //do action
                flag = false;
                Intent i = new Intent(Note_application_with_small_local_database.this, note.class);
                startActivity(i);
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_note_application_with_small_local_database);

        notes = findViewById(R.id.notes);
        arrayListnote = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, arrayListnote);
        notes.setAdapter(adapter);
        //load saved notes
        loadArray(this);
        //arrayListnote.add("test");
        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
                flag = true;
                Intent x = new Intent(getApplicationContext(), note.class);
                x.putExtra("test", arrayListnote.get(i));
                startActivity(x);
            }
        });
        notes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                new AlertDialog.Builder(Note_application_with_small_local_database.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete the note?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arrayListnote.remove(pos);
                                saveArray();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(Note_application_with_small_local_database.this, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Note_application_with_small_local_database.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                return true;
            }
        });

        github = findViewById(R.id.github);
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ARashad96/Note_application_with_small_local_database/upload"));
                startActivity(intent);
            }
        });
        info = findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(Note_application_with_small_local_database.this)
                        .setIcon(R.drawable.profile)
                        .setTitle("App info")
                        .setMessage("This app is a simple adding note with a local saving option using listview, sidebar menu, toast, edittext and linearlayout.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    public static void loadArray(Context mContext)
    {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        arrayListnote.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            arrayListnote.add(mSharedPreference1.getString("Status_" + i, null));
        }
    }

    public boolean saveArray()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        /* sKey is an array */
        mEdit1.putInt("Status_size", Note_application_with_small_local_database.arrayListnote.size());

        for(int i=0;i<Note_application_with_small_local_database.arrayListnote.size();i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, Note_application_with_small_local_database.arrayListnote.get(i));
        }

        return mEdit1.commit();
    }
}

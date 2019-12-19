package com.arashad96.andoriddeveloperadvancedkit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class note extends AppCompatActivity {
    EditText addnote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_note);

        addnote = findViewById(R.id.addnote);

        //receive current note to edit
        Intent intent = getIntent();
        addnote.setText(intent.getStringExtra("test"));

    }

    @Override
    public void onBackPressed() {
        if (Note_application_with_small_local_database.flag) {
            String temp = addnote.getText().toString().replaceAll("\\s+", "");
            //add new note
            if (temp.equals("")) {
                Toast.makeText(this, "Note wasn't saved its blank", Toast.LENGTH_SHORT).show();
            } else {
                Note_application_with_small_local_database.arrayListnote.set(Note_application_with_small_local_database.pos, addnote.getText().toString());
                saveArray();
                Note_application_with_small_local_database.adapter.notifyDataSetChanged();
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            String temp = addnote.getText().toString().replaceAll("\\s+", "");
            //add new note
            if (temp.equals("")) {
                Toast.makeText(this, "Note wasn't saved its blank", Toast.LENGTH_SHORT).show();
            } else {
                Note_application_with_small_local_database.arrayListnote.add(addnote.getText().toString());
                saveArray();
                Note_application_with_small_local_database.adapter.notifyDataSetChanged();
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            }
        }
        super.onBackPressed();
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

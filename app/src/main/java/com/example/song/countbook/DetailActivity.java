/*
 * EditActivity
 *
 * Version 1.0
 *
 * Oct 1, 2017
 *
 * Acknowledgement:
 *  1. Function onCreate, onStart, loadFromFile, saveInFile used in all activity classes are modified from Lonely-tweet class provided in lab
 *  2. Class CustomAdapter are learned and modified from https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
 */


package com.example.song.countbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DetailActivity  extends Activity {
    private static final String FILENAME = "file.sav";
    private ArrayList<Countbook> countbookList;
    private Countbook countbook;
    private EditText iniCounterText;
    private EditText nameText;
    private EditText counterText;
    private EditText commentText;
    private EditText dateText;
    private int editLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        editLocation =  intent.getIntExtra(MainActivity.EXTRA_MESSAGE,0);
        countbookList = new ArrayList<Countbook>();
        nameText = (EditText) findViewById(R.id.name);
        iniCounterText = (EditText) findViewById(R.id.initialCounter);
        commentText = (EditText) findViewById(R.id.comment);
        counterText = (EditText) findViewById(R.id.counter);
        dateText = (EditText) findViewById(R.id.date);
    }
    /**
     * Called after the app is created.Start activity by get the counter want to edit.
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        countbook=countbookList.get(editLocation);
        //get method from https://stackoverflow.com/questions/4590957/how-to-set-text-in-an-edittext
        //2017-9-27
        nameText.setText(countbook.getName(), TextView.BufferType.NORMAL);
        iniCounterText.setText(Integer.toString(countbook.getInitialCounter()), TextView.BufferType.NORMAL);
        commentText.setText(countbook.getComment(), TextView.BufferType.NORMAL);;
        counterText.setText(Integer.toString(countbook.getCounter()), TextView.BufferType.NORMAL);
        dateText.setText(countbook.getDate(),TextView.BufferType.NORMAL);
    }

    /**
     * Used to load data in memory
     * Modified from Lonely-tweet class provided in lab
     */
    private void loadFromFile(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Countbook>>() {}.getType();
            countbookList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            countbookList = new ArrayList<Countbook>();
        }
    }
}

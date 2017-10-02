/*
 * AddActivity
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
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddActivity extends Activity {
    private static final String FILENAME = "file.sav";
    private EditText nameText;
    private EditText counterText;
    private EditText commentText;

    private ArrayList<Countbook> countbookList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        nameText = (EditText) findViewById(R.id.name);
        counterText = (EditText) findViewById(R.id.counter);
        commentText = (EditText) findViewById(R.id.comment);
        Button saveButton = (Button) findViewById(R.id.save);
        countbookList = new ArrayList<Countbook>();

        /**
         * save Button that adds the new countbook to countbookList
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String name = nameText.getText().toString();
                String comment = commentText.getText().toString();
                try{
                    int counter = Integer.parseInt(counterText.getText().toString());
                    Countbook newCountbook = new Countbook(name, counter, comment);
                    countbookList.add(newCountbook);
                    saveInFile();
                    end();
                }catch (Exception e) {
                    //case that should not happen but handle for exception
                    Countbook newCountbook = new Countbook(name, 0, comment);// if no counter is input the app will have an error so use 0 as default
                    countbookList.add(newCountbook);
                    saveInFile();
                    end();
                }
            }
        });
    }

    /**
     * Called after the app is created
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }

    /**
     * end the current activity and go back to MainActivity
     */
    protected void end(){
        this.finish();
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

    /**
     * Used to save data in memory
     * Modified from Lonely-tweet class provided in lab
     */
    private void saveInFile()  {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);//goes stream based on filename
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos)); //create buffer writer
            Gson gson = new Gson();
            gson.toJson(countbookList, out);//convert java object to json string & save in output
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}

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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EditActivity extends Activity {
    private static final String FILENAME = "file.sav";
    private int editLocation = -1;
    private ArrayList<Countbook> countbookList;
    private Countbook countbook;
    private EditText iniCounterText;
    private EditText nameText;
    private EditText counterText;
    private EditText commentText;
    /**
     * constructor of edit activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Button saveButton = (Button) findViewById(R.id.save);
        Intent intent = getIntent();
        editLocation =  intent.getIntExtra(MainActivity.EXTRA_MESSAGE,0);
        nameText = (EditText) findViewById(R.id.name);
        iniCounterText = (EditText) findViewById(R.id.initialCounter);
        commentText = (EditText) findViewById(R.id.comment);
        counterText = (EditText) findViewById(R.id.counter);
        countbookList = new ArrayList<Countbook>();

        /**
         * Save button that saves every change in countbook
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String name = nameText.getText().toString();
                int iniCounter = Integer.parseInt(iniCounterText.getText().toString());
                int counter = Integer.parseInt(counterText.getText().toString());
                String comment = commentText.getText().toString();

                countbook.setName(name);
                countbook.setInitialCounter(iniCounter);
                countbook.setCounter(counter);
                countbook.setComment(comment);
                saveInFile();
                end();
            }
        });
    }

    /**
     * end the current activity and go back to mainactivity
     */
    private void end() {
        this.finish();
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
        nameText.setText(countbook.getName(), TextView.BufferType.EDITABLE);
        iniCounterText.setText(Integer.toString(countbook.getInitialCounter()), TextView.BufferType.EDITABLE);
        commentText.setText(countbook.getComment(), TextView.BufferType.EDITABLE);;
        counterText.setText(Integer.toString(countbook.getCounter()), TextView.BufferType.EDITABLE);
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
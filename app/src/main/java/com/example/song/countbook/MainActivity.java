/*
 * MainActivity
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
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

public class MainActivity extends Activity {

    private static final String FILENAME = "file.sav";
    public final static String EXTRA_MESSAGE = "com.example.song.countbook.MESSAGE";
    private int currentLocation=-1;
    private ListView listView;
    private ArrayList<Countbook> countbookList;
    private ArrayAdapter<Countbook> adapter;
    private Countbook countbook;

    /**
     *  Called when the activity is first created.
     *  Modified from Lonely-tweet class provided in lab
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button detailButton = (Button) findViewById(R.id.detail);
        Button addButton = (Button) findViewById(R.id.add);
        Button deleteButton = (Button) findViewById(R.id.delete);
        Button editButton = (Button) findViewById(R.id.edit);
        Button increaseButton = (Button) findViewById(R.id.increase);
        Button decreaseButton = (Button) findViewById(R.id.decrease);
        Button resetButton = (Button) findViewById(R.id.reset);

        listView = (ListView) findViewById(R.id.list);
        countbookList = new ArrayList<Countbook>();

        //Taken from https://developer.android.com/guide/topics/ui/binding.html
        //2017/10/1
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                countbook = countbookList.get(position);
                currentLocation = position;
            }
        };
        listView.setOnItemClickListener(mMessageClickedHandler);

        /**
         * Show a detail snackbar which shows initial counter and comment.
         */
        detailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentLocation != -1) {
                    setResult(RESULT_OK);
                    detail_intent(v);
                    loadFromFile();
                    adapter.notifyDataSetChanged();
//                    Snackbar.make(v, "initial counter: " + countbook.getInitialCounter()
//                            + "| comment: " + countbook.getComment(), Snackbar.LENGTH_LONG)
//                            .setAction("No action", null).show();
                }
            }
        });

        /**
         * The button "Add" which calls add_intent function and uses for creating new countbook.
         */
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                add_intent(v);
                loadFromFile();
                adapter.notifyDataSetChanged();
            }
        });

        /**
         * The button "Delete" which deletes the current selected countbook.
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentLocation != -1) {
                    setResult(RESULT_OK);
                    countbookList.remove(currentLocation);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
            }
        });

        /**
         * The button "Edit" which calls edit_intent and edit the current seleced countbook.
         */
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentLocation != -1) {
                    setResult(RESULT_OK);
                    edit_intent(v);
                    loadFromFile();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        /**
         * The button "Increase" which increases the current counter.
         */
        increaseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentLocation != -1) {
                    setResult(RESULT_OK);
                    countbook.increCounter();
                    saveInFile();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        /**
         * The button "decrease" which decreases the current counter.
         */
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentLocation != -1) {
                    setResult(RESULT_OK);
                    countbook.decreCounter();
                    saveInFile();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        /**
         * The button "reset" which resets the current counter to initial value.
         */
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentLocation != -1){
                    setResult(RESULT_OK);
                    int initialCounter=countbook.getInitialCounter();
                    countbook.setCounter(initialCounter);
                    saveInFile();
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * Called after the app is created
     * Modified from Lonely-tweet class provided in lab
     */
    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
        adapter= new CustomAdapter(countbookList,getApplicationContext());
        listView.setAdapter(adapter);
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

    /**
     * go into AddActivity
     *
     * @param view  A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
     */
    public void add_intent(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    /**
     * go into EditActivity
     *
     * @param view  A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
     */
    public void edit_intent(View view){
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(EXTRA_MESSAGE, currentLocation);
        startActivity(intent);
    }

    public void detail_intent(View view){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_MESSAGE, currentLocation);
        startActivity(intent);
    }
}
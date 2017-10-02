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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * modified from the example at https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
 */
public class CustomAdapter extends ArrayAdapter<Countbook> {

    private ArrayList<Countbook> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView Name;
        TextView Date;
        TextView Counter;
        TextView Comment;
    }

    public CustomAdapter(ArrayList<Countbook> data, Context context) {
        super(context, R.layout.list_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Countbook countbook = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.Name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.Date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.Counter = (TextView) convertView.findViewById(R.id.counter);
            viewHolder.Comment = (TextView) convertView.findViewById(R.id.comment);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.Name.setText(countbook.getName());
        viewHolder.Counter.setText(String.valueOf(countbook.getCounter()));
        viewHolder.Comment.setText(countbook.getComment());
        viewHolder.Date.setText(countbook.getDate());

        // Return the completed view to render on screen
        return convertView;
    }
}
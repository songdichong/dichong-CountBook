/*
 * Countbook
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Countbook class
 *
 * @author dichong
 */
public class Countbook {
    private String name;
    private int counter_number;
    private int initial_counter;
    private Date date;
    private String comment;

    /**
     * Construct a countbook object with name, counter, comment.
     *
     * @param name      name of user
     * @param counter   input counter value
     * @param comment   input comment
     */
    public Countbook(String name,int counter, String comment ){
        this.name = name;
        this.date = new Date();
        this.initial_counter = counter;
        this.counter_number = counter;
        this.comment = comment;
    }

    /**
     * Set name of user and update last modified date.
     *
     * @param name  name of user
     */
    public void setName(String name) {
        this.name = name;
        this.date = new Date();
    }

    /**
     * Get name of user
     *
     * @return name name of user
     */
    public String getName(){
        return this.name;
    }

    /**
     * Set value of current counter and update last modified date.
     *
     * @param counter current counter value
     */
    public void setCounter(int counter){
        if (counter<0){}
        else{
            this.counter_number = counter;
            this.date= new Date();
        }
    }

    /**
     * Set initial counter value and update last modified date.
     *
     * @param val the value that user want initial counter value to be
     */
    public void setInitialCounter(int val){
        if (val<0){}
        else{
            this.date= new Date();
            this.initial_counter = val;
        }
    }

    /**
     * Increase current counter value by 1
     */
    public void increCounter(){
        this.counter_number=this.counter_number+1;
        this.date= new Date();
    }

    /**
     * Decrease current counter value by 1. Counters will not be negative
     */
    public void decreCounter() {
        if (this.counter_number>0){
            this.counter_number = this.counter_number - 1;
            this.date=new Date();
        }else{}
    }

    /**
     * Get current counter value
     *
     * @return counter_number, current counter value
     */
    public int getCounter(){
        return this.counter_number;
    }

    /**
     * Get initial counter value
     *
     * @return initial_counter, initial counter value
     */
    public int getInitialCounter(){
        return this.initial_counter;
    }

    /**
     * Set comment and update date
     *
     * @param comment user new comment
     */
    public void setComment(String comment) {
        this.comment = comment;
        this.date=new Date();
    }

    /**
     * Get comment
     *
     * @return comment
     */
    public String getComment(){
        return this.comment;
    }

    /**
     * Get last modified date
     *
     * @return date in string format
     */
    public String getDate() {
        DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDate= df.format(this.date);
        return stringDate;
    }

    /**
     * method used to output string
     *
     * @return name + date + counter_number
     */
    @Override
    public String toString(){
        return name + date + counter_number + comment;
    }
}

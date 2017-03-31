package com.example.ryu.wordexercise.DB;

import io.realm.RealmObject;

/**
 * Created by Ryu on 2017-03-18.
 */

public class Word extends RealmObject{
    private String word;
    private String mean;

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}

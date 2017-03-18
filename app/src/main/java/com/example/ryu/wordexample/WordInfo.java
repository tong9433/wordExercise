package com.example.ryu.wordexample;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ryu on 2017-03-17.
 */

@DatabaseTable(tableName = "WordInfo")
public class WordInfo {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int _id;
    @DatabaseField
    private String wSpelling;
    @DatabaseField
    private String wMean;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getwMean() {
        return wMean;
    }

    public void setwMean(String wMean) {
        this.wMean = wMean;
    }

    public String getwSpelling() {
        return wSpelling;
    }

    public void setwSpelling(String wSpelling) {
        this.wSpelling = wSpelling;
    }
}

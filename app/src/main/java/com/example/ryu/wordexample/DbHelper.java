package com.example.ryu.wordexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Ryu on 2017-03-17.
 */

public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "wordinfo.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<WordInfo,Integer> mMemInfo;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, WordInfo.class);
        } catch (SQLException e) {
            Log.e(DbHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,WordInfo.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            Log.e(DbHelper.class.getName(),"Unable to update database from version "+ oldVersion + "to new " + newVersion,e);
        }
    }

    public Dao<WordInfo, Integer> getStandardInfosDao() throws SQLException {
        if (mMemInfo == null) {
            mMemInfo = getDao(WordInfo.class);
        }
        return mMemInfo;
    }
}

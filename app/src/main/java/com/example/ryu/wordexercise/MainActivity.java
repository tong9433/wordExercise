package com.example.ryu.wordexercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmResults;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class MainActivity extends AppCompatActivity {
    Workbook workbook = null;
    WorkbookSettings workbookSettings;
    Sheet sheet = null;
    InputStream inputStream;
    ListView listView;
    ArrayAdapter arrayAdapter;

    Realm realm;
    Word word;
    Context mContext = this;

    Button bt_recognize;
    Button bt_translate;
    Button bt_textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_word);
        bt_recognize = (Button) findViewById(R.id.bt_recognize);
        bt_translate = (Button) findViewById(R.id.bt_translate);
        bt_textToSpeech = (Button) findViewById(R.id.bt_textToSpeech);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
        copyExcelToDatabase();

        bt_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TranslateActivity.class);
                startActivity(intent);
            }
        });

    }


    protected void copyExcelToDatabase() {
        try {
            inputStream = getBaseContext().getResources().getAssets().open("words.xls");
            workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("euc-kr");
            workbook = Workbook.getWorkbook(inputStream, workbookSettings);

            if (workbook != null) {
                Log.d("check1", "workbook is not null");
                sheet = workbook.getSheet(0);
                if (sheet != null) {
                    Log.d("check1", "sheet is not null");
                    realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for (int i = 0; i < 800; i++) {
                                word = realm.createObject(Word.class);
                                word.setWord(sheet.getCell(0, i).getContents().toString());
                                Log.d("Word UTF checking", "" + word.getWord());
                                word.setMean(sheet.getCell(1, i).getContents().toString());
                                Log.d("Mean UTF checking", "" + word.getMean());
                            }
                        }
                    });
                } else {
                    Log.d("check1", "sheet is null");
                }
            } else {
                Log.d("check", "workbook is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final RealmResults<Word> results = realm.where(Word.class).findAll();
        Log.d("result size : ", "" + results.size());
        for (int i = 0; i < results.size(); i++) {
            String word = results.get(i).getWord().toString();
            String mean = results.get(i).getMean().toString();
            String result = word + " : " + mean;
            arrayAdapter.add(result);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }
}
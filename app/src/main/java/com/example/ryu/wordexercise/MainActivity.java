package com.example.ryu.wordexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmResults;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class MainActivity extends AppCompatActivity {
    Workbook workbook = null;
    Sheet sheet = null;
    InputStream inputStream;
    ListView listView;
    ArrayAdapter arrayAdapter;

    Realm realm;
    Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_word);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
        copyExcelToDatabase();

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

    protected void copyExcelToDatabase() {
        try {
            inputStream = getBaseContext().getResources().getAssets().open("words.xls");
            workbook = Workbook.getWorkbook(inputStream);

            if( workbook != null) {
                Log.d("check1", "workbook is not null");
                sheet = workbook.getSheet(0);
                if( sheet != null) {
                    Log.d("check1", "sheet is not null");
                    realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for(int i=0; i<800; i++) {
                                word = realm.createObject(Word.class);
                                word.setWord(sheet.getCell(0, i).getContents());
                                //word.setMean(sheet.getCell(1,i).getContents());
                                //UTF-8 변환문제 해결
                            }
                        }
                    });
                }
                else {
                    Log.d("check1", "sheet is null");
                }
            }
            else {
                Log.d("check", "workbook is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final RealmResults<Word> results = realm.where(Word.class).findAll();
        Log.d("result size : ", ""+results.size());
        for (int i = 0; i < results.size(); i++) {
            String word = results.get(i).getWord().toString();
            arrayAdapter.add(word);
        }
    }
}

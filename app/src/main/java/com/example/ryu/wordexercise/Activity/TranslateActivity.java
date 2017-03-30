package com.example.ryu.wordexercise.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ryu.wordexercise.R;
import com.example.ryu.wordexercise.Translator;

public class TranslateActivity extends AppCompatActivity {
    Button bt_translate;
    TextView txt_translated;

    String testText;
    String translatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        bt_translate = (Button)findViewById(R.id.TransButton);
        txt_translated = (TextView)findViewById(R.id.TransText);

        bt_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translator tr = Translator.getInstance();

                testText = "감사합니다";
                translatedText = tr.translate(testText);
                txt_translated.setText(translatedText);
            }
        });
    }
}
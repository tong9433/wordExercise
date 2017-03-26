package com.example.ryu.wordexercise.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ryu.wordexercise.R;
import com.example.ryu.wordexercise.TTS;

/**
 * Created by tong-ilsong on 2017. 3. 23..
 */

public class QuizGameActivity extends Activity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quizgame);

        editText =(EditText) findViewById(R.id.edit_query);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTS tts=new TTS(QuizGameActivity.this,v,editText);
            }
        });





    }


}

package com.example.ryu.wordexercise.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
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
    private Button button;
    private Button playButton;
    private TTS tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quizgame);

        editText =(EditText) findViewById(R.id.edit_query);
        button = (Button) findViewById(R.id.button);
        playButton = (Button) findViewById(R.id.playbutton);

        tts=new TTS(QuizGameActivity.this,playButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message=editText.getText().toString();;
                if (!TextUtils.isEmpty(message)) {

                    tts.requestNaverApi(message);
                    Log.e("sds","sdsd");
                }

            }
        });
    }


}

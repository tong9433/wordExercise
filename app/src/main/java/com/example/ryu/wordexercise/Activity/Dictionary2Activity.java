package com.example.ryu.wordexercise.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.example.ryu.wordexercise.AudioWriterPCM;
import com.example.ryu.wordexercise.NaverRecognizer;
import com.example.ryu.wordexercise.R;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.util.List;

/**
 * Created by Ryu on 2017-03-30.
 */

public class Dictionary2Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic2);


    }
}

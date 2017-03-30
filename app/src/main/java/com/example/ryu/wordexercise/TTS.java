package com.example.ryu.wordexercise;

import android.app.Activity;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.example.ryu.wordexercise.Activity.QuizGameActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;

/**
 * Created by tong-ilsong on 2017. 3. 23..
 */

public class TTS {
    Activity activity;
    Button button;

    public TTS(Activity activity, Button button){// editText에 입력한 text를 음성으로 변환해줌,
//        //매개변수, 해당 Activity 와 변환 버튼 , 변환할 text가 있는 editText
        this.activity=activity;
        this.button=button;
    }

    /**
     * > POST /v1/labs/translate.json HTTP/1.1
     * > Host: openapi.naver.com
     * > User-Agent: curl/7.43.0
     * > Accept: *
     * > Content-Type: application/json
     * > X-Naver-Client-Id: YOUR_CLIENT_ID
     * > X-Naver-Client-Secret: YOUR_CLIENT_SECRET"
     * @param message
     */

    public void requestNaverApi(String message) {

        final File mpFile = new File(activity.getExternalFilesDir(null), "demo_" + message.length() + ".mp3");
        Ion.with(activity)
                .load("https://openapi.naver.com/v1/voice/tts.bin")
                .setHeader("X-Naver-Client-Id", "5uw46EbLkRN_sqJpaksZ")
                .setHeader("X-Naver-Client-Secret", "I1EkW9k2Hu")
                .setBodyParameter("speaker", "matt")
                .setBodyParameter("speed", "0")
                .setBodyParameter("text", message)
                .write(mpFile)
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, final File file) {
                        if (e != null) {
                            Log.e("Eerrrr", "ee", e);
                            return;
                        }
                        if (file != null) {
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    MediaPlayer mp = new MediaPlayer();
                                    try {
                                        mp.setDataSource(file.getAbsolutePath());
                                        mp.prepare();
                                        mp.start();
                                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            public void onCompletion(MediaPlayer mp) {
                                                mp.release();
                                            };
                                        });//30번정도 반복하면 에러 나는 현상을 mp.release 함으로써 해결


                                    } catch (IOException e1) {
                                        Log.e("ERROR", "ee", e1);
                                    }
                                }
                            });

                        }
                    }
                });

    }


}

package com.example.ryu.wordexercise.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ryu.wordexercise.AudioWriterPCM;
import com.example.ryu.wordexercise.NaverRecognizer;
import com.example.ryu.wordexercise.R;
import com.example.ryu.wordexercise.TTS;
import com.example.ryu.wordexercise.Translator;
import com.naver.speech.clientapi.SpeechConfig;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Ryu on 2017-03-30.
 */

public class DictionaryActivity extends Activity {
    private static final String TAG = RunningGameActivity.class.getSimpleName();
    private static final String CLIENT_ID = "HJwABjKOdTik1lfe7o_E";
    // 1. "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요.
    // 2. build.gradle (Module:app)에서 패키지명을 실제 개발자센터 애플리케이션 설정의 '안드로이드 앱 패키지 이름'으로 바꿔 주세요

    private DictionaryActivity.RecognitionHandler handler;
    private NaverRecognizer naverRecognizer;
    private TextView txtResult;
    private Button btnStartEng;
    private Button btnStartKo;
    private Button btnListen;
    private String mResult;

    private AudioWriterPCM writer;

    // Handle speech recognition Messages.
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
                txtResult.setText("Connected");
                writer = new AudioWriterPCM(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("Test");
                break;

            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;

            case R.id.partialResult:
                // Extract obj property typed with String.
                mResult = (String) (msg.obj);
                //txtResult.setText(mResult);
                break;

            case R.id.finalResult:
                SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
                List<String> results = speechRecognitionResult.getResults(); //음성인식 결과값 list
                mResult = results.get(0);
                String langType = naverRecognizer.getLangType().toString();
                Translator tr = Translator.getInstance();
                if(langType == "ENGLISH" ) { //영어를 한글로
                    String result_EngToKo;
                    result_EngToKo = tr.translate(mResult,Translator.ENG,Translator.KOR);
                    txtResult.setText(result_EngToKo);
                }
                else { //한글을 영어로
                    String result_KoToEng;
                    result_KoToEng = tr.translate(mResult,Translator.KOR,Translator.ENG);
                    txtResult.setText(result_KoToEng);
                    TTS tts = new TTS(DictionaryActivity.this, btnListen);
                    tts.requestNaverApi(result_KoToEng);
                }
                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
                txtResult.setText(mResult);
                btnStartEng.setEnabled(true);
                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }

                btnStartEng.setEnabled(true);
                break;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic);

        txtResult = (TextView) findViewById(R.id.txt_result);
        btnStartEng = (Button) findViewById(R.id.btn_start_eng);
        btnStartKo = (Button) findViewById(R.id.btn_start_ko);
        btnListen = (Button) findViewById(R.id.btn_listen);

        handler = new DictionaryActivity.RecognitionHandler(this);
        naverRecognizer = new NaverRecognizer(this, handler, CLIENT_ID);

        btnStartEng.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!naverRecognizer.getSpeechRecognizer().isRunning()) {
                    // Start button is pushed when SpeechRecognizer's state is inactive.
                    // Run SpeechRecongizer by calling recognize().
                    mResult = "";
                    txtResult.setText("Connecting...");
                    btnStartEng.setEnabled(false);
                    naverRecognizer.recognize(SpeechConfig.LanguageType.ENGLISH);
                } else {
                    Log.d(TAG, "stop and wait Final Result");
                    btnStartEng.setEnabled(false);

                    naverRecognizer.getSpeechRecognizer().stop();
                }
            }
        });

        btnStartKo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!naverRecognizer.getSpeechRecognizer().isRunning()) {
                    // Start button is pushed when SpeechRecognizer's state is inactive.
                    // Run SpeechRecongizer by calling recognize().
                    mResult = "";
                    txtResult.setText("Connecting...");
                    btnStartEng.setEnabled(false);
                    naverRecognizer.recognize(SpeechConfig.LanguageType.KOREAN);
                } else {
                    Log.d(TAG, "stop and wait Final Result");
                    btnStartEng.setEnabled(false);
                    naverRecognizer.getSpeechRecognizer().stop();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // NOTE : initialize() must be called on start time.
        naverRecognizer.getSpeechRecognizer().initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mResult = "";
        txtResult.setText("");
        btnStartEng.setEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // NOTE : release() must be called on stop time.
        naverRecognizer.getSpeechRecognizer().release();
    }

    // Declare handler for handling SpeechRecognizer thread's Messages.
    static class RecognitionHandler extends Handler {
        private final WeakReference<DictionaryActivity> mActivity;

        RecognitionHandler(DictionaryActivity activity) {
            mActivity = new WeakReference<DictionaryActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DictionaryActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }
}

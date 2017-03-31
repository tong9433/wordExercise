package com.example.ryu.wordexercise.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ryu.wordexercise.R;

/**
 * Created by tong-ilsong on 2017. 3. 31..
 */

public class MenuActivity extends AppCompatActivity {

    private Button btn_game1;
    private Button btn_game2;
    private Button btn_dic;
    private Button btn_setting;
    private Button btn_question;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  // 상단 상태바 없애고 풀스크린 만들기 위한 코드
        setContentView(R.layout.activity_menu);




    }
}

package com.example.ryu.wordexercise;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by osujin on 2017-03-29.
 */

public class Translator {
    //언어 설정은 클래스안의 ENG, KOR 상수를 이용하면 됨
    public static final String ENG = "en";
    public static final String KOR = "ko";

    private static Translator translator = new Translator();
    private static String returnLangType;

    private static final String clientId = "HJwABjKOdTik1lfe7o_E";
    private static final String clientSecret = "R__r9XpvZu";

    String translatedStr="";

    public static Translator getInstance(){
        return translator;
    }

    // #parameter str : 번역할 말, source : 번역할 언어, target : 번역 결과 언어
    public String translate(final String str, final String source, final String target){

        Thread workingThread = new Thread() {
            public void run() {
                try {
                    translatedStr = getTranslatedText(str,source,target);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };

        workingThread.start();

        try {
            workingThread.join();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
        return translatedStr;
    }

    private String getTranslatedText(String str,String source, String target){
        StringBuffer response = new StringBuffer();
        String result="";

        try {
            String text = URLEncoder.encode(str, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source="+source+"&target="+target+"&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            JSONObject obj = new JSONObject(response.toString());
            result = obj.getJSONObject("message").getJSONObject("result").getString("translatedText");
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }
}
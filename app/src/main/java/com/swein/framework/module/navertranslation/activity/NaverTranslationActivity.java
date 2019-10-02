package com.swein.framework.module.navertranslation.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.swein.framework.module.easyscreenrecord.framework.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NaverTranslationActivity extends AppCompatActivity {

    String clientId = "";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "";//애플리케이션 클라이언트 시크릿값";

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO

            String sourceText = "do you want to smoke?";

            try {
                String text = URLEncoder.encode(sourceText, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/language/translate";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source=en&target=ko&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                //System.out.println(response.toString());
                ILog.iLogDebug("seokho", response.toString());

            } catch (Exception e) {
                //System.out.println(e);
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_translation);

        new Thread(networkTask).start();
    }
}

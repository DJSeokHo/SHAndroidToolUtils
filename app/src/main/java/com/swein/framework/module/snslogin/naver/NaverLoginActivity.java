package com.swein.framework.module.snslogin.naver;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class NaverLoginActivity extends AppCompatActivity {

    private final static String TAG = "NaverLoginActivity";

    private final static String OAUTH_CLIENT_ID = "VAn8lzxOs0nCdKm029K7";
    private final static String OAUTH_CLIENT_SECRET = "RyM3ne4flO";
    private final static String OAUTH_CLIENT_NAME = "shandroidtoolutils";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_login);

        OAuthLogin.getInstance().init(this, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick(View view) {
                OAuthLogin.getInstance().startOauthLoginActivity(NaverLoginActivity.this, new OAuthLoginHandler() {
                    @Override
                    public void run(boolean success) {
                        if(success) {
                            String accessToken = OAuthLogin.getInstance().getAccessToken(NaverLoginActivity.this);
                            String refreshToken = OAuthLogin.getInstance().getRefreshToken(NaverLoginActivity.this);

                            long expiresAt = OAuthLogin.getInstance().getExpiresAt(NaverLoginActivity.this);
                            String tokenType = OAuthLogin.getInstance().getTokenType(NaverLoginActivity.this);

                            ILog.iLogDebug(TAG, accessToken + " " + refreshToken + " " + String.valueOf(expiresAt) + " " + tokenType);
                            ILog.iLogDebug(TAG, OAuthLogin.getInstance().getState(NaverLoginActivity.this).toString());

                            ThreadUtil.startThread(() -> {
                                String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml";
                                String at = OAuthLogin.getInstance().getAccessToken(NaverLoginActivity.this);

                                String result = OAuthLogin.getInstance().requestApi(NaverLoginActivity.this, at, url);
                                ILog.iLogDebug(TAG, result);
                                ParsingVersionData(result);
                            });
                        }
                        else {
                            String errorCode = OAuthLogin.getInstance().getLastErrorCode(NaverLoginActivity.this).getCode();
                            String errorDesc = OAuthLogin.getInstance().getLastErrorDesc(NaverLoginActivity.this);
                            Toast.makeText(NaverLoginActivity.this, "errorCode:" + errorCode
                                    + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void ParsingVersionData(String data) {

        String f_array[] = new String[9];

        try {
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            InputStream input = new ByteArrayInputStream(data.getBytes("UTF-8"));
            parser.setInput(input, "UTF-8");

            int parserEvent = parser.getEventType();
            String tag;
            boolean inText = false;
            boolean lastMatTag = false;

            int colIdx = 0;

            while (parserEvent != XmlPullParser.END_DOCUMENT) {

                switch (parserEvent) {
                    case XmlPullParser.START_TAG:

                        tag = parser.getName();
                        if ( 0 == tag.compareTo("xml") ) {
                            inText = false;
                        }
                        else if ( 0 == tag.compareTo("data") ) {
                            inText = false;
                        }
                        else if ( 0 == tag.compareTo("result") ) {
                            inText = false;
                        }
                        else if ( 0 == tag.compareTo("resultcode") ) {
                            inText = false;
                        }
                        else if ( 0 == tag.compareTo("message") ) {
                            inText = false;
                        }
                        else if ( 0 == tag.compareTo("response") ) {
                            inText = false;
                        }
                        else {
                            inText = true;
                        }

                        break;

                    case XmlPullParser.TEXT:

                        tag = parser.getName();
                        if ( inText ) {
                            if ( parser.getText() == null ) {
                                f_array[colIdx] = "";
                            }
                            else {
                                f_array[colIdx] = parser.getText().trim();
                            }

                            colIdx++;
                        }
                        inText = false;

                        break;

                    case XmlPullParser.END_TAG:

                        tag = parser.getName();
                        inText = false;

                        break;
                }
                parserEvent = parser.next();
            }
        }
        catch ( Exception e ) {
            ILog.iLogDebug(TAG, "network error");
        }

        String id = f_array[0];
        String email = f_array[6];

        ILog.iLogDebug(TAG, id + " " + email);
    }

    @Override
    protected void onDestroy() {
        OAuthLogin.getInstance().logout(this);
        super.onDestroy();
    }
}

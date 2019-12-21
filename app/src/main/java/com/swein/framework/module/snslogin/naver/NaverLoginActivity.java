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

import org.json.JSONException;
import org.json.JSONObject;

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
                                String url = "https://openapi.naver.com/v1/nid/me";
                                String at = OAuthLogin.getInstance().getAccessToken(NaverLoginActivity.this);

                                String result = OAuthLogin.getInstance().requestApi(NaverLoginActivity.this, at, url);
                                ILog.iLogDebug(TAG, result);

                                try {
                                    JSONObject jsonObject = new JSONObject(result);

                                    String id = jsonObject.getString("id");
                                    String email = jsonObject.getString("email");
                                    ILog.iLogDebug(TAG, id + " " + email);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }

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


    @Override
    protected void onDestroy() {
        OAuthLogin.getInstance().logout(this);
        super.onDestroy();
    }
}

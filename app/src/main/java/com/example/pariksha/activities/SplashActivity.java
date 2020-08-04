package com.example.pariksha.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.pariksha.R;
import com.example.pariksha.util.MYCONSTANTS;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /**  Case 1 : When Internet Connection is Availaible **/
                    /**  Case 2 : When Internet Connection is not Availaible **/
                    /**  Case 3 : When User has already LoggedIn **/
                    /**  Case 4 : When User has not LoggedIn Yet **/

                    Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                    startActivity(intent);
                    finish(); /** finishes the current Activity **/
                }
            }, MYCONSTANTS.SPLASH_DELAY);
        } catch (Exception ex) {
            Log.i(TAG, ex.getMessage());
        }
    }


}

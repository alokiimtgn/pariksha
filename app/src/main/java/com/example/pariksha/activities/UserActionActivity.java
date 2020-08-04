package com.example.pariksha.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pariksha.R;
import com.example.pariksha.fragments.AboutFragment;
import com.example.pariksha.fragments.ContactFragment;
import com.example.pariksha.fragments.DashBoardFragment;
import com.example.pariksha.models.User;
import com.example.pariksha.util.MYCONSTANTS;
import com.example.pariksha.util.MyPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class UserActionActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private RelativeLayout id_relLayout;
    private Toolbar id_toolbar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private BottomNavigationView bottomNavigationView;
    private static final String TAG = "UserActionActivity";
    private int bottomNavActiveItem = -1;
    private GoogleSignInClient mGoogleSignInClient;
   private GoogleSignInAccount account;
    private boolean fragStatus = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_action);
        sharedPreferences = getSharedPreferences(MYCONSTANTS.APP_SPREFERENCE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        bottomNavigationView = findViewById(R.id.idBtmnview);
        User user = MyPreferences.getLoginInfo(sharedPreferences, MYCONSTANTS.USER_INFO_KEY);
        Calendar calendar = Calendar.getInstance();
        user.setLastActive(calendar.getTime().toString());
        id_toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(id_toolbar);
        id_toolbar.setTitle("Epariksha");
        id_toolbar.setTitleTextColor(Color.WHITE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_relLayout, new DashBoardFragment(sharedPreferences, editor), "fragment_dashboard");
        fragmentTransaction.commit();
        //bottomNavigationView.setVisibility(View.GONE);
        //id_toolbar.setVisibility(View.GONE);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_notification:
                Toast.makeText(getApplicationContext(), "Notifications....", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item_logout:
                MyPreferences.clearUserInfo(editor, MYCONSTANTS.USER_INFO_KEY);
                Toast.makeText(getApplicationContext(), "Logged out..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                logOut();
                startActivity(intent);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_dashboard:
                if (bottomNavActiveItem != 0) {
                    switchFragment(new DashBoardFragment(sharedPreferences, editor), "fragment_dashboard");
                    bottomNavActiveItem = 0;
                }
                break;

            case R.id.item_about:
                if (bottomNavActiveItem != 1) {
                    switchFragment(new AboutFragment(), "fragment_about");
                    bottomNavActiveItem = 1;
                    fragStatus = true;
                }
                break;

            case R.id.item_contact:
                if (bottomNavActiveItem != 2) {
                    switchFragment(new ContactFragment(), "fragment_contact");
                    bottomNavActiveItem = 2;
                    fragStatus = true;
                }
                break;
        }
        return true;
    }

    public void switchFragment(Fragment fragment, String tagName) {
        // showToastMessage("Switched to .. " + tagName);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.id_relLayout, fragment, tagName);
        fragStatus = true;
        fragmentTransaction.commit();
    }

    private void showToastMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);
    }

    public void logOut() {
        account = null;
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Logged out Successfully......", Toast.LENGTH_LONG).show();
                        //Log.i(TAG, task.toString());
                        //Log.i(TAG, "LOGOUT EXECUTED...........");
                    }
                });
    }


    @Override
    public void onBackPressed() {
        if (fragStatus) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.id_relLayout, new DashBoardFragment(sharedPreferences, editor), "fragment_dashboard");
            fragStatus = false;
            fragmentTransaction.commit();
            bottomNavigationView.setSelectedItemId(R.id.item_dashboard);
        } else {
            super.onBackPressed();
        }
    }
}

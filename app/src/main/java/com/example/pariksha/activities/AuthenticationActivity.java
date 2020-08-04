package com.example.pariksha.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pariksha.R;
import com.example.pariksha.models.User;
import com.example.pariksha.util.FireBaseMethods;
import com.example.pariksha.util.MYCONSTANTS;
import com.example.pariksha.util.MyPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Calendar;

import static com.example.pariksha.util.MYCONSTANTS.APP_SPREFERENCE_NAME;
import static com.example.pariksha.util.MYCONSTANTS.COURSE;
import static com.example.pariksha.util.MYCONSTANTS.GOOGLE_ACC_INFO_KEY;
import static com.example.pariksha.util.MYCONSTANTS.LAST_TEST_DETAILS;
import static com.example.pariksha.util.MYCONSTANTS.LOGINMODE;
import static com.example.pariksha.util.MYCONSTANTS.MOBILE_NO;
import static com.example.pariksha.util.MYCONSTANTS.TESTS_APPEARED;
import static com.example.pariksha.util.MYCONSTANTS.USER_INFO_KEY;


public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout id_authenticatonLayout;
    private static final String TAG = "AuthenticationActivity";
    private Toolbar id_toolbar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    // Write a message to the database
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    private static final int RC_SIGN_IN = 100;
    int st = 0;
    Button id_btnLogout;
    GoogleSignInAccount account;
    private TextView id_tv_loginAccInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        try {


            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("users-onlintest");
            id_toolbar = findViewById(R.id.id_toolbar);
            sharedPreferences = getSharedPreferences(APP_SPREFERENCE_NAME, MODE_PRIVATE);
            editor = sharedPreferences.edit();
            if (MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY) != null) {
                Intent intent = new Intent(getApplicationContext(), UserActionActivity.class);
                startActivity(intent);
                finish();
            }

            signInButton = findViewById(R.id.signInButton);
            id_tv_loginAccInfo = findViewById(R.id.id_tv_loginAccInfo);
            id_btnLogout = findViewById(R.id.id_btnLogout);

            setSupportActionBar(id_toolbar);
            id_toolbar.setTitle("Epariksha");
            id_toolbar.setTitleTextColor(Color.WHITE);
            id_authenticatonLayout = findViewById(R.id.id_authenticatonLayout);


            if (signInButton != null) {
                //   signInButton.setSize(SignInButton.SIZE_STANDARD);
            }

            if (signInButton != null) {
                signInButton.setOnClickListener(this);
            }

            if (id_btnLogout != null) {
                id_btnLogout.setOnClickListener(this);
            }


            initGoogle();
        } catch (Exception ex) {
            Log.e(TAG, "Exception @ onCreate() : " + ex.getMessage());
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            //  Signed in successfully, show authenticated UI.
            //  updateUI(account, st);
            //  navigateToDashBoard();
            try {
                userLogin(account);
            } catch (Exception ex) {
                Log.e(TAG, "An Exception has Occurred : " + ex.getMessage());
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInButton:
                signIn();
                break;
            case R.id.id_btnLogout:
                //logOut();
                break;
        }
    }

    public void logOut(GoogleSignInAccount account) {
        if (account != null && mGoogleSignInClient != null) {
            account = null;
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Logged out Successfully......", Toast.LENGTH_LONG).show();
                            Log.i(TAG, task.toString());
                        }
                    });
        }

        //  updateUI(account, st);
    }


    public void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);

    }


    public void userLogin(final GoogleSignInAccount account) {
        // Code for validation of user input & Login process

        final User user = new User();
        //  user.setGoogleSignInAccount(account);
        user.setUsername(account.getEmail());
        user.setEmail(account.getEmail());
        user.setName(account.getDisplayName());
        user.setMobileNo(MOBILE_NO);

        //ArrayList<TestResult> testResultArrayList = new ArrayList<TestResult>();
//        TestResult testResult = new TestResult();
//        testResult.setTestId("123");
//        testResult.setTestName("TEST 123");
//        testResult.setTotalCorrect(4);
//        testResult.setTotalIncorrect(2);
//        testResult.setTotalMarks(234);
//        testResultArrayList.add(testResult);
//
//        testResult = new TestResult();
//        testResult.setTestId("123 3");
//        testResult.setTestName("TEST 123 3");
//        testResult.setTotalCorrect(43);
//        testResult.setTotalIncorrect(2);
//        testResult.setTotalMarks(234);
//        testResultArrayList.add(testResult);


        //user.setReportCard(testResultArrayList);
        user.setPassword("#@#@");
        user.setLastActive(Calendar.getInstance().getTime() + "");
        user.setCourse(COURSE);
        user.setLastTestDetails(LAST_TEST_DETAILS);
        user.setTestsAppeared(TESTS_APPEARED);
        user.setLoginMode(LOGINMODE);
        user.setUrl_profile_pic(account.getPhotoUrl() + "");
        MyPreferences.setLoginInfo(editor, USER_INFO_KEY, user, GOOGLE_ACC_INFO_KEY, account);
        //Log.i(TAG, "User data was saved to Shared Pref : " + new Gson().toJson(MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY)));
        Intent intent = new Intent(getApplicationContext(), UserActionActivity.class);
        databaseReference.child(MYCONSTANTS.FIREBASE_USERPATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.i(TAG, new Gson().toJson(dataSnapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.i(TAG, databaseError.toString());
            }
        });
        /*Query query = FirebaseDatabase.getInstance().getReference(MYCONSTANTS.FIREBASE_USERPATH)
                .orderByChild("username")
                .equalTo("alokiimtgn@gmail.com");
query.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()){
            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                if (dataSnapshot1.exists() && dataSnapshot1 !=null) {
                    Log.i("Query", new Gson().toJson(dataSnapshot1));
                }
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
Log.i(TAG,"Query error");
    }
});*/
        FireBaseMethods.createUserRecordInFbaseDb(databaseReference, account, user);
        startActivity(intent);
        finish();
    }


    private void showToastMesage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void logOut() {
        account = null;
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Logged out Successfully......", Toast.LENGTH_LONG).show();
                        //      Log.i(TAG, task.toString());
                        //    Log.i(TAG, "LOGOUT EXECUTED...........");
                    }
                });

        //   updateUI(account, st);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}

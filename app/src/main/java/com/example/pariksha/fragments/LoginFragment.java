package com.example.pariksha.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pariksha.R;
import com.example.pariksha.activities.MainActivity;
import com.example.pariksha.activities.UserActionActivity;
import com.example.pariksha.models.User;
import com.example.pariksha.util.MYCONSTANTS;
import com.example.pariksha.util.MyPreferences;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private Button idBtnLogin;
    private TextView idTViewNotRegistered;
    private EditText idEtUsername, idEtPassword;
    private static final String TAG = "LoginFragment";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public LoginFragment() {
    }

    public LoginFragment(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        // Required empty public constructor
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        idBtnLogin = (Button) view.findViewById(R.id.idBtnLogin);
        idTViewNotRegistered = (TextView) view.findViewById(R.id.idTViewNotRegistered);
        idEtUsername = (EditText) view.findViewById(R.id.idEtUsername);
        idEtPassword = (EditText) view.findViewById(R.id.idEtPassword);
        idTViewNotRegistered.setOnClickListener(this);
        idBtnLogin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idTViewNotRegistered:
                try {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.id_authenticatonLayout, new RegisterFragment(), "fragment_register");
                    fragmentTransaction.commit();
                } catch (Exception ex) {
                    Log.e(TAG, "An Exception @ Calling RegisterFragment" + ex.getMessage());
                    showToastMesaage("An Exception @ Calling RegisterFragment" + ex.getMessage());
                }
                break;

            case R.id.idBtnLogin:
                try {
                    userLogin();
                } catch (Exception ex) {
                    Log.e(TAG, "An Exception @ click of Login Button " + ex.getMessage());
                    showToastMesaage("An Exception @ click of Login Button " + ex.getMessage());
                }
                break;
        }
    }


    public void userLogin() {
        // Code for validation of user input & Login process
        if (validateUserInput()) {
            User user = new User();
            user.setUsername(idEtUsername.getText().toString());
            user.setEmail(idEtUsername.getText().toString());
            user.setName(MYCONSTANTS.NAME);
            user.setMobileNo(MYCONSTANTS.MOBILE_NO);
            user.setPassword(idEtPassword.getText().toString());
            user.setLastActive(MYCONSTANTS.LAST_ACTIVE);
            user.setCourse(MYCONSTANTS.COURSE);
            user.setLastTestDetails(MYCONSTANTS.LAST_TEST_DETAILS);
            user.setTestsAppeared(MYCONSTANTS.TESTS_APPEARED);
            user.setLoginMode(MYCONSTANTS.LOGINMODE);
            user.setUrl_profile_pic(MYCONSTANTS.URL_PROFILE_PIC);
           // MyPreferences.setLoginInfo(editor, MYCONSTANTS.USER_INFO_KEY, user);
            Log.i(TAG, "User data was saved to Shared Pref : " + new Gson().toJson(MyPreferences.getLoginInfo(sharedPreferences, MYCONSTANTS.USER_INFO_KEY)));
            Intent intent = new Intent(getContext(), UserActionActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            showToastMesaage("Invalid Data!");
        }
    }

    private void showToastMesaage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    private boolean validateUserInput() {
        boolean status = true;
        try {
            if (idEtUsername.getText().toString().length() == 0) {
                status = false;
                idEtUsername.setError(getResources().getString(R.string.text_error_emptyUsername));
            } else if (idEtUsername.getText().toString().contains(" ")) {
                status = false;
                idEtUsername.setError(getResources().getString(R.string.text_error_whiteSpaceUsername));
            } else if (idEtUsername.getText().toString().length() < 6) {
                status = false;
                idEtUsername.setError(getResources().getString(R.string.text_error_tooShortUsername));
            } else if (idEtPassword.getText().toString().length() == 0) {
                status = false;
                idEtPassword.setError(getResources().getString(R.string.text_error_emptyPassword));
            } else if (idEtPassword.getText().toString().contains(" ")) {
                status = false;
                idEtPassword.setError(getResources().getString(R.string.text_error_whiteSpacePassword));
            } else if (idEtPassword.getText().toString().length() < 6) {
                status = false;
                idEtPassword.setError(getResources().getString(R.string.text_error_tooShortPassword));
            }
        } catch (Exception ex) {
            Log.e(TAG, "An error @ validateUserInput : " + ex.getMessage());
        }
        return status;
    }
}

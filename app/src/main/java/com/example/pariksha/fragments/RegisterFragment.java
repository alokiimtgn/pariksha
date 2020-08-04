package com.example.pariksha.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pariksha.R;
import com.example.pariksha.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private TextView idTViewLogin;
    private Button idBtnRegister;
    private static final String TAG = "RegisterFragment";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public RegisterFragment() {
    }

    public RegisterFragment(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        // Required empty public constructor
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        idTViewLogin = (TextView) view.findViewById(R.id.idTViewLogin);
        idBtnRegister = (Button) view.findViewById(R.id.idBtnRegister);
        idBtnRegister.setOnClickListener(this);
        idTViewLogin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idBtnRegister:
                Intent intent = new Intent(getContext(), MainActivity.class);
                // Code for validation of user input & Login
                startActivity(intent);
                break;

            case R.id.idTViewLogin:
                try {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.id_authenticatonLayout, new LoginFragment(sharedPreferences,editor), "fragment_register");
                    fragmentTransaction.commit();
                } catch (Exception ex) {
                    Log.e(TAG, "An Error @ Calling Login Fragment" + ex.getMessage());
                }
                break;
        }
    }
}

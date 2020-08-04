package com.example.pariksha.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pariksha.models.Solution;
import com.example.pariksha.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyPreferences {
    private static final String TAG = "MyPreferences";

    public static void setLoginInfo(SharedPreferences.Editor editor, String key, User user, String googleAccInfoKey, GoogleSignInAccount account) {
        editor.putString(key, new Gson().toJson(user));
        editor.putString(googleAccInfoKey, new Gson().toJson(account));
        editor.commit();
       // Log.i(TAG, new Gson().toJson(user) + " Was saved to SharedPreferences...");
    }


    public static void setLoginInfo(SharedPreferences.Editor editor, String key, User user) {
        editor.putString(key, new Gson().toJson(user));

        editor.commit();
        //Log.i(TAG, new Gson().toJson(user) + " Was saved to SharedPreferences...");
    }

    public static User getLoginInfo(SharedPreferences sharedPreferences, String key) {
        User user = null;
        if (sharedPreferences.contains(key)) {
            user = new Gson().fromJson(sharedPreferences.getString(key, ""), User.class);
        } else {
          //  Log.i(TAG, "Login Required...");
        }
        return user;
    }

    public static void setTestProgress(String key, ArrayList<Solution> solutionArrayList) {

    }


    public static ArrayList<Solution> getTestProgress(String key, ArrayList<Solution> solutionArrayList) {

        return null;
    }

    public static void clearUserInfo(SharedPreferences.Editor editor, String userInfoKey) {
        editor.remove(userInfoKey);
        editor.commit();
        //Log.i(TAG, "USER DATA REMOVED");
    }

}
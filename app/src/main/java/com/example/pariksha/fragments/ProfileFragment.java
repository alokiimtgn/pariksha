package com.example.pariksha.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pariksha.R;
import com.example.pariksha.models.User;
import com.example.pariksha.util.MYCONSTANTS;
import com.example.pariksha.util.MyPreferences;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.pariksha.util.MYCONSTANTS.USER_INFO_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TextView idTviewName, idTviewUserMobile, idTviewUserEmail;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String TAG = "ProfileFragment";
    private CircleImageView idImvUserPic;

    public ProfileFragment() {
    }

    public ProfileFragment(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        // Required empty public constructor
        Log.i(TAG, "SP: " + sharedPreferences);
        Log.i(TAG, "SP E: " + editor);
        this.sharedPreferences = sharedPreferences;

        this.editor = editor;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        idTviewName = view.findViewById(R.id.idTviewName);
        idTviewUserMobile = view.findViewById(R.id.idTviewUserMobile);
        idTviewUserEmail = view.findViewById(R.id.idTviewUserEmail);
        idImvUserPic = view.findViewById(R.id.idImvUserPic);
        if (sharedPreferences != null) {
            setData(MyPreferences.getLoginInfo(sharedPreferences, MYCONSTANTS.USER_INFO_KEY));
            Log.i("User : ", new Gson().toJson(MyPreferences.getLoginInfo(sharedPreferences, MYCONSTANTS.USER_INFO_KEY)));
            try {
                if(idImvUserPic != null) {
                    Picasso.get()
                            .load(MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY).getUrl_profile_pic())
                            .placeholder(R.drawable.person_orange)
                            .error(R.drawable.person_orange)
                            .into(idImvUserPic);
                }
            } catch (Exception ex) {
                Log.i(TAG, "An Exception occurred while loading images : " + ex.getMessage());
            }
        } else {
            Log.i(TAG, "null");
        }
        return view;
    }

    public void setData(User user) {
        if (idTviewName != null) {
            idTviewName.setText("Name : " + user.getName());
        }
        if (idTviewUserMobile != null) {
            idTviewUserMobile.setText("Mobile No : " + user.getMobileNo());
        }
        if (idTviewUserEmail != null) {
            idTviewUserEmail.setText("Email ID : " + user.getEmail() + "\nLast Test Details : " + user.getLastTestDetails() + "\nCourse : " + user.getCourse() + "\nLoginMode : " + user.getLoginMode() + "\nLast Active : " + user.getLastActive());
        }
    }
}
package com.example.pariksha.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.pariksha.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevInfoFragment extends Fragment {


    public DevInfoFragment(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dev_info, container, false);

        return view;
    }


}

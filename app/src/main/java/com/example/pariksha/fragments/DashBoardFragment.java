package com.example.pariksha.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pariksha.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {
    private CardView id_cview0, id_cview1, id_cview2, id_cview3, id_cview4;
    private static final String TAG = "DashBoardFragment";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public DashBoardFragment(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        // Required empty public constructor
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        id_cview0 = (CardView) view.findViewById(R.id.id_cview0);
        id_cview1 = (CardView) view.findViewById(R.id.id_cview1);
        id_cview2 = (CardView) view.findViewById(R.id.id_cview2);
        id_cview3 = (CardView) view.findViewById(R.id.id_cview3);
        id_cview4 = (CardView) view.findViewById(R.id.id_cview4);

        id_cview0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new TestSelectionFragment(sharedPreferences,editor));
                //Intent intent = new Intent(getContext(), MainActivity.class);
                //startActivity(intent);
            }
        });

        id_cview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage("Profile was clicked...");
                switchFragment(new ProfileFragment(sharedPreferences,editor));
            }
        });

        id_cview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage("Result was clicked...");
                switchFragment(new ResultFragment(sharedPreferences,editor));

            }
        });

        id_cview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage("Notice was clicked...");
                switchFragment(new NoticeFragment(sharedPreferences,editor));
            }
        });

        id_cview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage("DevInfo was clicked...");
                switchFragment(new DevInfoFragment(sharedPreferences,editor));
            }
        });
        return view;
    }

    private void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void switchFragment(Fragment fragment) {
        try {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.id_relLayout, fragment, "fragment_devnInfo");
            fragmentTransaction.commit();
        } catch (Exception ex) {
            showToastMessage("An Exception @ (SwitchFragment" + fragment + ")" + ex.getMessage());
        }
    }
}

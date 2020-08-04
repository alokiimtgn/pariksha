package com.example.pariksha.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pariksha.R;
import com.example.pariksha.models.TestResult;
import com.example.pariksha.models.User;
import com.example.pariksha.recyclerviewadapters.TestResultAdapter;
import com.example.pariksha.util.MYCONSTANTS;
import com.example.pariksha.util.MyPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.pariksha.util.MYCONSTANTS.APP_SPREFERENCE_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {
    private static final String TAG = "ResultFragment";
    private RecyclerView id_recyclerViewTestResult;
    private ArrayList<TestResult> testResultArrayList;
    private TestResultAdapter testResultAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private CardView idNoresTview;

    public ResultFragment(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        try {
            // Inflate the layout for this fragment
            idNoresTview = view.findViewById(R.id.idNoresTview);
            testResultArrayList = new ArrayList<TestResult>();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference(MYCONSTANTS.FIREBASE_USERPATH);
            sharedPreferences = getContext().getSharedPreferences(APP_SPREFERENCE_NAME, MODE_PRIVATE);

            id_recyclerViewTestResult = view.findViewById(R.id.id_recyclerViewTestResult);
            User user = MyPreferences.getLoginInfo(sharedPreferences, MYCONSTANTS.USER_INFO_KEY);
            if (user != null && testResultArrayList != null) {
                for (int i = user.getReportCard().size() - 1; i >= 0; i--) {
                    testResultArrayList.add(user.getReportCard().get(i));
                }
                if (testResultArrayList.size() == 0) {
                    idNoresTview.setVisibility(View.VISIBLE);
                } else {
                    idNoresTview.setVisibility(View.GONE);
                }
                testResultAdapter = new TestResultAdapter(getContext(), testResultArrayList);
                if (testResultAdapter != null) {
                    id_recyclerViewTestResult.setAdapter(testResultAdapter);
                }
                id_recyclerViewTestResult.setLayoutManager(new LinearLayoutManager(getContext()));
                id_recyclerViewTestResult.setHasFixedSize(true);
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.getMessage());
            showToastMessage(ex.getMessage());
        }
        return view;
    }

    private void showToastMessage(String message) {
        //Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
}

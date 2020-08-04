package com.example.pariksha.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pariksha.R;
import com.example.pariksha.interfaces.OnTestStatusChanged;
import com.example.pariksha.models.Test;
import com.example.pariksha.recyclerviewadapters.TestListAdapter;
import com.example.pariksha.util.MYCONSTANTS;
import com.google.gson.Gson;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestSelectionFragment extends Fragment implements OnTestStatusChanged {
    private RecyclerView id_RecyclerViewTest;
    private ArrayList<Test> testArrayList;
    private TestListAdapter testListAdapter;
    private MKLoader id_MKLoader;
    private static final String TAG = "TestSelectionFragment";

    public TestSelectionFragment(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_selection, container, false);
        testArrayList = new ArrayList();
        id_RecyclerViewTest = view.findViewById(R.id.id_RecyclerViewTest);
        id_MKLoader = view.findViewById(R.id.id_MKLoader);
        callTestListApi(MYCONSTANTS.URL_TESTLIST);  // Will fetch TestInfo List from server
        return view;
    }

    private void setTestData(){
        if (testArrayList.size()>0) {
            testListAdapter = new TestListAdapter(getContext(), testArrayList);
            id_RecyclerViewTest.setLayoutManager(new LinearLayoutManager(getContext()));
            id_RecyclerViewTest.setHasFixedSize(true);
            id_RecyclerViewTest.setAdapter(testListAdapter);
        }else {
           // Log.i(TAG,"Test ArrayList was Empty!");
        }
    }

    @Override
    public void onActivated(int position, boolean status) {
        if (status) {
            testArrayList.get(position).setTestActive(true);
            testListAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onDeActivated(int position, boolean status) {
        if (!status) {
            testArrayList.get(position).setTestActive(false);
            testListAdapter.notifyItemChanged(position);
        }
    }


    public void callTestListApi(String url) {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int index = 0; index < response.length(); index++) {
                        Test test = new Gson().fromJson(response.get(index).toString(), Test.class);
                       if ((test != null) && (test.getUrl() != null) && !(test.getUrl().equalsIgnoreCase(""))) {
                           testArrayList.add(test);
                       }else {
                          // Log.i(TAG,"Null Test object or Test object with empty Url detected @"+index);
                       }
                    }
                    setTestData();
                    id_MKLoader.setVisibility(View.GONE);
                } catch (Exception ex) {
                    Log.e(TAG, ex.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.e(TAG, "VolleyError @@: " + error.getMessage());
                if (error.toString().contains("java.net.UnknownHostException"))
                    showToastMessage("Cannot connect to the internet");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

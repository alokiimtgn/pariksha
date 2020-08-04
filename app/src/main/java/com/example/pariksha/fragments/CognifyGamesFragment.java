package com.example.pariksha.fragments;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pariksha.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class CognifyGamesFragment extends Fragment implements View.OnClickListener {
    private FloatingActionButton id_fabLessThan, id_fabEqualTo, id_fabGreaterThan;
    private TextView id_TviewNum1, id_TviewSign0, id_TviewNum2, id_TviewNum3, id_TviewSign, id_TviewNum4;

    public CognifyGamesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cognify_games, container, false);

        id_TviewNum1 = (TextView) view.findViewById(R.id.id_TviewNum1);
        id_TviewSign0 = (TextView) view.findViewById(R.id.id_TviewSign0);
        id_TviewNum2 = (TextView) view.findViewById(R.id.id_TviewNum2);
        id_TviewNum3 = (TextView) view.findViewById(R.id.id_TviewNum3);
        id_TviewSign = (TextView) view.findViewById(R.id.id_TviewSign);
        id_TviewNum4 = (TextView) view.findViewById(R.id.id_TviewNum4);

        id_fabLessThan = (FloatingActionButton) view.findViewById(R.id.id_fabLessThan);
        id_fabEqualTo = (FloatingActionButton) view.findViewById(R.id.id_fabEqualTo);
        id_fabGreaterThan = (FloatingActionButton) view.findViewById(R.id.id_fabGreaterThan);
        id_fabLessThan.setOnClickListener(this);
        id_fabEqualTo.setOnClickListener(this);
        id_fabGreaterThan.setOnClickListener(this);
generateGamevalues();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_fabLessThan:
                evaluate(-1);

                break;
            case R.id.id_fabEqualTo:
                evaluate(0);

                break;
            case R.id.id_fabGreaterThan:
                evaluate(1);
                break;
        }
    }

    private void generateGamevalues() {
        Random random = new Random();
        String operator[] = {"+", "-", "X", "/"};
        int num1 = random.nextInt(99);
        int num2 = random.nextInt(9);
        int num3 = random.nextInt(99);
        int num4 = random.nextInt(9);
        String opr1 = operator[random.nextInt(operator.length - 1)];
        String opr2 = operator[random.nextInt(operator.length - 1)];
        id_TviewNum1.setText(num1 + "");
        id_TviewSign0.setText(opr1 + "");
        id_TviewNum2.setText(num2 + "");
        id_TviewNum3.setText(num3 + "");
        id_TviewSign.setText(opr2 + "");
        id_TviewNum4.setText(num4 + "");
    }


    private void evaluate(int i) {
        String message = "";
        int num1 = Integer.parseInt(id_TviewNum1.getText().toString());
        int num2 = Integer.parseInt(id_TviewNum2.getText().toString());
        int num3 = Integer.parseInt(id_TviewNum3.getText().toString());
        int num4 = Integer.parseInt(id_TviewNum4.getText().toString());
        char opr1 = id_TviewSign0.getText().toString().charAt(0);
        char opr2 = id_TviewSign.getText().toString().charAt(0);
        int res1 = 0, res2 = 0;
        switch (opr1) {
            case '+':
                res1 = num1 + num2;
                break;
            case '-':
                res1 = num1 - num2;
                break;
            case 'X':
                res1 = num1 * num2;
                break;
            case '/':
                res1 = num1 + num2;
                break;
        }

        switch (opr2) {
            case '+':
                res1 = num1 + num2;
                break;
            case '-':
                res2 = num1 - num2;
                break;
            case 'X':
                res2 = num1 * num2;
                break;
            case '/':
                res2 = num1 / num2;
                break;
        }

        message = "\nI : "+i+"\nNum1 : "+num1+"\nNum2 : "+num2+"\nRes1 : "+res1+"\nRes2 : "+res2;
        int fRes = -8;
        if (res1 == res2) {
            fRes = 0;

        } else if (res1 < res2) {
            fRes = -1;
        } else if (res2 < res1) {
            fRes = 1;
        }
message+="\nFinal : "+fRes;
        if (i == fRes){
            showToastMessage("SHI HAI...");
            message += "\nSHI HAI";
        }else {
            showToastMessage("GLAT HAI....");
             message += "\nGLAT HAI";
        }
Log.i("Cognify",message);
        generateGamevalues();
    }

    private void showToastMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }
}

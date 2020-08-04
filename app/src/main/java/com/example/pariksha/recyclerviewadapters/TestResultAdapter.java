package com.example.pariksha.recyclerviewadapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pariksha.R;
import com.example.pariksha.models.TestResult;
import com.example.pariksha.util.MYCONSTANTS;

import java.util.ArrayList;

public class TestResultAdapter extends RecyclerView.Adapter<TestResultAdapter.MyViewHolder> {
    private static final String TAG = "TestResultAdapter";
    private Context context;
    private ArrayList<TestResult> testResultArrayList;

    public TestResultAdapter(Context context, ArrayList<TestResult> testResultArrayList) {
        this.context = context;
        this.testResultArrayList = testResultArrayList;
        Log.i(TAG, "Size : " + testResultArrayList.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater.inflate(R.layout.item_test_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (testResultArrayList.get(position).getTestId() != null) {
            holder.idTviewtestInfo.setText("Test Id : " + testResultArrayList.get(position).getTestId());
        }
        if (testResultArrayList.get(position).getTestName() != null) {
            holder.idTviewtestInfo.append("\nName : " + testResultArrayList.get(position).getTestName());
        }
        holder.idTviewtestInfo.append("\nHeld On : " + testResultArrayList.get(position).getHeldOn() + "\n");

        holder.idTviewtestInfo.append("\nTotal No. of Questions : " + testResultArrayList.get(position).getTotalNoOfQuestion());
        holder.idTviewtestInfo.append("\n+ve Marks : " + testResultArrayList.get(position).getTotalPositiveMarks());
        holder.idTviewtestInfo.append("\n-ve Marks : " + testResultArrayList.get(position).getTotalNegativeMarks());
        holder.idTviewtestInfo.append("\nMarks Obtained : " + testResultArrayList.get(position).getTotalMarks());
        holder.idTviewtestInfo.append("\nPercentage : " + testResultArrayList.get(position).getPercentage());
        holder.idTviewtestInfo.append("\nUnattempted : " + testResultArrayList.get(position).getTotalUnattempted());
        if (testResultArrayList.get(position).getResultStatus().equalsIgnoreCase(MYCONSTANTS.RESULT_STATUS_PASS)) {
            holder.idTviewtestInfo.setTextColor(Color.BLACK);
        } else {
            holder.idTviewtestInfo.setTextColor(Color.RED);
        }
        holder.idTviewtestInfo.append("\nResult Status : " + testResultArrayList.get(position).getResultStatus());
    }

    @Override
    public int getItemCount() {
        return testResultArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView idTviewtestInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idTviewtestInfo = itemView.findViewById(R.id.idTviewtestInfo);
        }
    }
}

package com.example.pariksha.recyclerviewadapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pariksha.R;
import com.example.pariksha.activities.MainActivity;
import com.example.pariksha.interfaces.OnTestStatusChanged;
import com.example.pariksha.models.Test;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.TestListViewHolder> {
    private Context context;
    private ArrayList<Test> testArrayList;
    private OnTestStatusChanged onTestStatusChanged;
    private RecyclerView id_RecyclerViewTest;
    private static final String TAG = "TestListAdapter";

    public TestListAdapter(Context context, ArrayList<Test> testArrayList) {
        this.context = context;
        this.testArrayList = testArrayList;
       // Log.i(TAG, "Size ..." + testArrayList.size());

    }

    @NonNull
    @Override
    public TestListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_test, parent, false);
        return new TestListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TestListViewHolder holder, final int position) {
        holder.idTview_TestId.setText("Test ID : " + testArrayList.get(position).getTestID());
        holder.idTview_TestName.setText("Heading : " + testArrayList.get(position).getHeading());
        holder.idTview_Audience.setText("Audience : " + testArrayList.get(position).getAudience());
        holder.idTview_createdBy.setText(testArrayList.get(position).getCreatedBy());
        if ((testArrayList.get(position).getTestDescription() != null) && (!testArrayList.get(position).getTestDescription().equalsIgnoreCase(""))) {
            holder.idTview_Description.setText("Description : " + testArrayList.get(position).getTestDescription());
        }else {
            holder.idTview_Description.setVisibility(View.GONE);
        }
        holder.idTview_timeStamp.setText("Created On : " + testArrayList.get(position).getTimestamp());
        if (testArrayList.get(position).isTestActive()) {
            holder.idSwitch_Status.setChecked(true);
        } else {
            holder.idSwitch_Status.setChecked(false);
        }
        holder.id_CView_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.idSwitch_Status.isChecked() && ((testArrayList.get(position).getUrl() != null) && !(testArrayList.get(position).getUrl().equalsIgnoreCase("")))) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("KEY_POSITION", position);
                    intent.putExtra("KEY_TESTINFO", new Gson().toJson(testArrayList.get(position)));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "This Test is currently not Availaible..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (testArrayList != null) {
            size = testArrayList.size();
        } else {
            size = 0;
        }
        return size;
    }

    public static class TestListViewHolder extends RecyclerView.ViewHolder {
        private CardView id_CView_item;
        private Switch idSwitch_Status;
        private TextView idTview_TestId, idTview_TestName, idTview_Audience, idTview_createdBy, idTview_Description, idTview_timeStamp;

        public TestListViewHolder(@NonNull View itemView) {
            super(itemView);
            id_CView_item = (CardView) itemView.findViewById(R.id.id_CView_item);
            idSwitch_Status = (Switch) itemView.findViewById(R.id.idSwitch_Status);
            idTview_TestId = (TextView) itemView.findViewById(R.id.idTview_TestId);
            idTview_TestName = (TextView) itemView.findViewById(R.id.idTview_TestName);
            idTview_Audience = (TextView) itemView.findViewById(R.id.idTview_Audience);
            idTview_Description = (TextView) itemView.findViewById(R.id.idTview_Description);
            idTview_createdBy = (TextView) itemView.findViewById(R.id.idTview_createdBy);
            idTview_timeStamp = (TextView) itemView.findViewById(R.id.idTview_timeStamp);
        }
    }
}

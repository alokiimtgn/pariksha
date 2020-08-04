package com.example.pariksha.recyclerviewadapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pariksha.R;

public class JumpToQuesAdapter extends RecyclerView.Adapter<JumpToQuesAdapter.MyVieHolder> {
    private Context context;
    private int btnCount;
    //private OnClickBtnInterface onClickBtnInterface;
    private static final String TAG = "BtnAdapter";

    public JumpToQuesAdapter(Context context, int btnCount) {
        this.context = context;
        this.btnCount = btnCount;
      //  this.onClickBtnInterface = (OnClickBtnInterface) context;
    }

    @NonNull
    @Override
    public MyVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_btn, parent, false);
        return new MyVieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVieHolder holder, final int position) {
        holder.idBtnQ.setText("Q" + (position + 1));

        holder.idBtnQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i(TAG, "....." + position);
        //            onClickBtnInterface.onClickQbtn(position);
                } catch (Exception ex) {
                    Log.e(TAG, ex.getMessage());
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return btnCount;
    }

    public static class MyVieHolder extends RecyclerView.ViewHolder {
        private Button idBtnQ;

        public MyVieHolder(@NonNull View itemView) {
            super(itemView);
            idBtnQ = (Button) itemView.findViewById(R.id.idBtnQ);
        }
    }
}

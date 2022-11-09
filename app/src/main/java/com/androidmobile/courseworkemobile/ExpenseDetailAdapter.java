package com.androidmobile.courseworkemobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseDetailAdapter extends RecyclerView.Adapter<ExpenseDetailAdapter.ExpenseDetailViewHolder> {

    private Context context;
    private ArrayList id, type, amount, time;

    public ExpenseDetailAdapter(Context context, ArrayList id, ArrayList type, ArrayList amount, ArrayList time) {
        this.context = context;
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

    @NonNull
    @Override
    public ExpenseDetailAdapter.ExpenseDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_display_expenses_detail, parent, false);
        return new ExpenseDetailAdapter.ExpenseDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseDetailAdapter.ExpenseDetailViewHolder holder, int position) {
        holder.showType.setText(String.valueOf(type.get(position)));
        holder.showAmount.setText(String.valueOf(amount.get(position)));
        holder.showTime.setText(String.valueOf(time.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ExpenseDetailViewHolder extends RecyclerView.ViewHolder{

        TextView showType, showAmount, showTime;

        public ExpenseDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            showType = itemView.findViewById(R.id.showType);
            showAmount = itemView.findViewById(R.id.showAmount);
            showTime = itemView.findViewById(R.id.showTime);
        }
    }
}

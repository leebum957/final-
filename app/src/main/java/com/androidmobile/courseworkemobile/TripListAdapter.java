package com.androidmobile.courseworkemobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripListHolder> {

    private Activity activity;
    private Context context;
    private ArrayList id, name, destination, date, assessment, description;

    TripListAdapter(Activity activity, Context context, ArrayList id, ArrayList name, ArrayList destination, ArrayList date,
                    ArrayList assessment, ArrayList description) {
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.assessment = assessment;
        this.description = description;
    }

    @NonNull
    @Override
    public TripListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_display_trip, parent, false);
        return new TripListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripListHolder holder, int position) {
        holder.showName.setText(String.valueOf(name.get(position)));
        holder.showDestination.setText(String.valueOf(destination.get(position)));
        holder.showDate.setText(String.valueOf(date.get(position)));
        holder.showAssessment.setText(new StringBuilder().append("Assessment: ")
                                                         .append(assessment.get(position)).toString());
        holder.showId.setText(String.valueOf(id.get(position)));

        holder.displayLayout.setOnClickListener((View -> {
            Intent intent = new Intent(context, UpdateTripItemScreen.class);
            intent.putExtra("id_holder", String.valueOf(id.get(position)));
            intent.putExtra("name_holder", String.valueOf(name.get(position)));
            intent.putExtra("destination_holder", String.valueOf(destination.get(position)));
            intent.putExtra("date_holder", String.valueOf(date.get(position)));
            intent.putExtra("assessment_holder", String.valueOf(assessment.get(position)));
            intent.putExtra("description_holder", String.valueOf(description.get(position)));
            activity.startActivityForResult(intent, 1);
        }));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class TripListHolder extends RecyclerView.ViewHolder {

        LinearLayout displayLayout;
        TextView showName, showDestination, showDate, showAssessment, showId;

        public TripListHolder(@NonNull View itemView) {
            super(itemView);
            displayLayout = itemView.findViewById(R.id.layout);
            showName = itemView.findViewById(R.id.showName);
            showDestination = itemView.findViewById(R.id.showDestination);
            showDate = itemView.findViewById(R.id.showDate);
            showAssessment = itemView.findViewById(R.id.showAssessment);
            showId = itemView.findViewById(R.id.showId);
        }
    }
}

package com.example.notpad;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Recycler_view_Adapter extends RecyclerView.Adapter<Recycler_view_Adapter.ViewHolder> {

    Context context;
    List<notes_model> list;
    private nots_recycler_clickListener listener;


    // Constructor to initialize the adapter with the context, list of notes, and click listener

    public Recycler_view_Adapter(Context context, List<notes_model> list,nots_recycler_clickListener listener){
    this.context=context;
    this.list=list;
    this.listener=listener;
}
    // Create new views (invoked by the layout manager)

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(context).inflate(R.layout.notes_recycler,parent,false);
    ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
//bind data into recycler view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    notes_model item= list.get(position);
        int id= item.getId();
        holder.titleText.setText(item.getTitle());
        holder.descriptionText.setText(item.getDescription());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             listener.onItemClick(list.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    // Provide a reference to the views for each data item
    public  class  ViewHolder extends RecyclerView.ViewHolder{

    TextView titleText,descriptionText;
    CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText=itemView.findViewById(R.id.rec_title);
            descriptionText=itemView.findViewById(R.id.rec_description);
            cardView=itemView.findViewById(R.id.contion_Rcycleitem);
        }

    }


}

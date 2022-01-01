package com.example.crud;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrudAdapter extends RecyclerView.Adapter<CrudViewHolder> {

    Context context;
    List<CrudModel> crudModelList;

    public CrudAdapter(Context context, List<CrudModel> crudModelList) {
        this.context = context;
        this.crudModelList = crudModelList;
    }

    @NonNull
    @Override
    public CrudViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CrudViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CrudViewHolder holder, int position) {
        holder.task_name.setText(crudModelList.get(position).getTitle());
        holder.subtitle.setText(crudModelList.get(position).getSubtitle());
        holder.duedate.setText(crudModelList.get(position).getDuedate());
        holder.task_description.setText(crudModelList.get(position).getDescription());

        holder.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.childLinearLayout.getVisibility() == View.GONE){
                    holder.childLinearLayout.setVisibility(View.VISIBLE);
                }else {
                    holder.childLinearLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return crudModelList.size();
    }
}

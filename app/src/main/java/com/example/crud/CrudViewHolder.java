package com.example.crud;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CrudViewHolder extends RecyclerView.ViewHolder {

    TextView task_name, subtitle, duedate, task_description;
    LinearLayoutCompat parentLinearLayout, childLinearLayout;

    public CrudViewHolder(@NonNull View itemView) {
        super(itemView);

        task_name = itemView.findViewById(R.id.taskname);
        subtitle = itemView.findViewById(R.id.subtitle);
        duedate = itemView.findViewById(R.id.duedate);
        task_description = itemView.findViewById(R.id.taskdescription);
        parentLinearLayout = itemView.findViewById(R.id.parentll);
        childLinearLayout = itemView.findViewById(R.id.childll);

    }
}

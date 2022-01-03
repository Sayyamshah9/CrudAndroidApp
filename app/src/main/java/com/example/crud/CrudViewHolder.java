package com.example.crud;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CrudViewHolder extends RecyclerView.ViewHolder {

    TextView task_name, subtitle, duedate, task_description;
    LinearLayoutCompat masterLinearLayout, parentLinearLayout, childLinearLayout;
    ImageView editbtn, deletebtn;

    public CrudViewHolder(@NonNull View itemView) {
        super(itemView);

        task_name = itemView.findViewById(R.id.taskname);
        subtitle = itemView.findViewById(R.id.subtitle);
        duedate = itemView.findViewById(R.id.duedate);
        task_description = itemView.findViewById(R.id.taskdescription);
        masterLinearLayout = itemView.findViewById(R.id.masterll);
        parentLinearLayout = itemView.findViewById(R.id.parentll);
        childLinearLayout = itemView.findViewById(R.id.childll);
        deletebtn = itemView.findViewById(R.id.deletebtn);
        editbtn = itemView.findViewById(R.id.editbtn);
    }
}

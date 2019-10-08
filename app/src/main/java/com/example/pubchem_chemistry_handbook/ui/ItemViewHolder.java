package com.example.pubchem_chemistry_handbook.ui;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Student;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView name_TextView;
    public TextView iso_TextView;


    public ItemViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        name_TextView = (TextView) itemView.findViewById(R.id.compound_name);
        iso_TextView = (TextView) itemView.findViewById(R.id.compound_iso);

    }

    public void bind(Student student) {
        name_TextView.setText(student.getName());
        iso_TextView.setText(student.getAdd() );

    }


}
package com.example.pubchem_chemistry_handbook.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView name_TextView;
    public TextView iso_TextView;
    public ImageView image_ImageView;


    public ItemViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        image_ImageView = (ImageView) itemView.findViewById(R.id.compound_image);
        name_TextView = (TextView) itemView.findViewById(R.id.compound_name);
        iso_TextView = (TextView) itemView.findViewById(R.id.compound_formula);
    }

    public void bind(Compound compound) {
        name_TextView.setText(compound.getName());
        iso_TextView.setText(compound.getFormula());
    }

}
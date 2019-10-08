package com.example.pubchem_chemistry_handbook.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    Context mcContext;
    List<Compound> CompoundList;

    public RVAdapter(Context mcContext, List<Compound> compoundList) {
        this.mcContext = mcContext;
        this.CompoundList = compoundList;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Compound student = CompoundList.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return CompoundList.size();
    }
}
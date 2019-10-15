package com.example.pubchem_chemistry_handbook.ui;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Element;
import com.example.pubchem_chemistry_handbook.data.Event;

import java.util.List;

public class pTable_Adapter extends RecyclerView.Adapter<pTable_Adapter.pTableViewHolder>{
    private List<Element> elementSet;

    public static class pTableViewHolder extends RecyclerView.ViewHolder{
        public View pTableView;
        public pTableViewHolder(View v) {
            super(v);
            pTableView = v;
        }
}

    public pTable_Adapter(List<Element> elements) {
        elementSet = elements;
    }

    @Override
    public pTable_Adapter.pTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ptable_element, parent, false);
        pTableViewHolder vh = new pTableViewHolder(v);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(pTableViewHolder holder, int position) {
        if (elementSet.get(position).getAtomicNumber() > 0) {
            switch (elementSet.get(position).getChemicalGroupBlock()) {
                case "Nonmetal":
                    (holder.pTableView.findViewById(R.id.element_background)).setBackgroundColor(Color.parseColor("#FEFECE"));
                    break;
                case "Halogen":
                    (holder.pTableView.findViewById(R.id.element_background)).setBackgroundColor(Color.parseColor("#FEFED3"));
                    break;
                case "Noble gas":
                    (holder.pTableView.findViewById(R.id.element_background)).setBackgroundColor(Color.parseColor("#FBE4C8"));
                    break;
                case "Alkali metal":
                    (holder.pTableView.findViewById(R.id.element_background)).setBackgroundColor(Color.parseColor("#FACFCB"));
                    break;
                case "Alkaline earth metal":
                    (holder.pTableView.findViewById(R.id.element_background)).setBackgroundColor(Color.parseColor("#D3D4FD"));
                    break;
                case "Metalloid":
                    (holder.pTableView.findViewById(R.id.element_background)).setBackgroundColor(Color.parseColor("#E2EDC9"));
                    break;
                case "Post-Transition metal":
                    (holder.pTableView.findViewById(R.id.element_background)).setBackgroundColor(Color.parseColor("#DBFDD1"));
                    break;
                case "Transition metal":
                    (holder.pTableView.findViewById(R.id.element_background)).setBackgroundColor(Color.parseColor("#CADDFD"));
                    break;
            }
            ((TextView)holder.pTableView.findViewById(R.id.element_atomic_number)).setText(Integer.toString(elementSet.get(position).getAtomicNumber()));
            ((TextView)holder.pTableView.findViewById(R.id.element_symbol)).setText(elementSet.get(position).getSymbol());
            ((TextView)holder.pTableView.findViewById(R.id.element_name)).setText(elementSet.get(position).getName());
        } else {
            if (elementSet.get(position).getAtomicNumber() == -1) {
                (holder.pTableView.findViewById(R.id.element_background)).setBackground(null);
                ((TextView)holder.pTableView.findViewById(R.id.element_symbol)).setText(elementSet.get(position).getSymbol());
            } else {
                (holder.pTableView.findViewById(R.id.element_background)).setBackground(null);
            }
        }
    }

    @Override
    public int getItemCount() {
        return elementSet.size();
    }
}

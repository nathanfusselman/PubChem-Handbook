package com.example.pubchem_chemistry_handbook.ui;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;
import com.example.pubchem_chemistry_handbook.data.global;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<ItemViewHolder> implements Filterable {
    Context mcContext;
    List<Compound> CompoundList;
    List<Compound> CompoundListFull;
    global global;

    public RVAdapter(Context mcContext, List<Compound> compoundList, global global) {
        this.mcContext = mcContext;
        this.CompoundList = compoundList;
        this.global = global;
        CompoundListFull = new ArrayList<>(compoundList);
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

    @Override
    public Filter getFilter() {
        return compoundFilter;
    }

    private Filter compoundFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Compound> filteredList = new ArrayList<>();

            if (charSequence != null && charSequence.length() != 0) {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Compound item : CompoundListFull) {
                    if (filterPattern.toLowerCase().trim().contentEquals("*")) {
                        filteredList.addAll(CompoundListFull);
                    } else {
                        if (isNumeric(filterPattern)) {
                            if (Integer.parseInt(filterPattern) == item.getEID()) {
                                filteredList.add(item);
                            }
                        } else {
                            if (global.getSearch_type() == 0) {
                                if (item.getName().toLowerCase().contains(filterPattern)) {
                                    filteredList.add(item);
                                }
                                if (item.getFormula().toLowerCase().contains(filterPattern)) {
                                    filteredList.add(item);
                                }
                            }
                            if (global.getSearch_type() == 1) {
                                if (item.getName().toLowerCase().startsWith(filterPattern)) {
                                    filteredList.add(item);
                                }
                                if (item.getFormula().toLowerCase().startsWith(filterPattern)) {
                                    filteredList.add(item);
                                }
                            }
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            CompoundList.clear();
            CompoundList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static boolean isNumeric(final String str) {

        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }
}
package com.example.pubchem_chemistry_handbook.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;
import com.example.pubchem_chemistry_handbook.data.global;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<CompoundListItem> implements Filterable {
    public List<Compound> CompoundList;
    private List<Compound> CompoundListFull;
    private global global;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RVAdapter(List<Compound> compoundList, global global) {
        this.CompoundList = compoundList;
        this.global = global;
        CompoundListFull = new ArrayList<>(compoundList);
    }

    @NonNull
    @Override
    public CompoundListItem onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new CompoundListItem(view, mListener,parent.getContext());
    }

    @Override
    public void onBindViewHolder(CompoundListItem holder, int position) {
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
                if (filterPattern.toLowerCase().trim().contentEquals("*")) {
                    filteredList.addAll(CompoundListFull);
                } else {
                    for (Compound item : CompoundListFull) {
                        if (isNumeric(filterPattern)) {
                            if (Integer.parseInt(filterPattern) == item.getCID()) {
                                filteredList.add(item);
                            }
                        } else {
                            if (global.getSearch_type_startsWith() == 1) {
                                if (item.getName().toLowerCase().startsWith(filterPattern)) {
                                    filteredList.add(item);
                                }
                                if (item.getFormula().toLowerCase().startsWith(filterPattern)) {
                                    filteredList.add(item);
                                }
                            } else {
                                boolean good_name = true;
                                boolean good_formula = true;
                                String[] tokens = filterPattern.split(" ");
                                for (String token : tokens) {
                                    if (global.getSearch_type_startsWith() == 0) {
                                        if (!item.getName().toLowerCase().contains(token)) {
                                            good_name = false;
                                        }
                                        if (!item.getFormula().toLowerCase().contains(token)) {
                                            good_formula = false;
                                        }
                                    }

                                }
                                if (good_formula || good_name) {
                                    filteredList.add(item);
                                }
                            }
                        }
                    }
                }
            }
            Collections.sort(filteredList, new Comparator<Object>() {

                public int compare(Object o1, Object o2) {
                    Compound p1 = (Compound) o1;
                    Compound p2 = (Compound) o2;
                    int ret = -1;
                    //business logic here
                    if (p1.getFormula().length() == p2.getFormula().length()) {
                        ret = 0;
                    } else if (p1.getFormula().length() > p2.getFormula().length()) {
                        ret = 1;
                    }
                    //end business logic
                    return ret;
                }
            });
            global.setResults(filteredList.size());
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            CompoundList.clear();
            CompoundList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    private static boolean isNumeric(final String str) {

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


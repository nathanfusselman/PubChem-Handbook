package com.example.pubchem_chemistry_handbook.ui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Element;
import com.example.pubchem_chemistry_handbook.data.global;

import java.util.List;

public class pTable_Adapter extends RecyclerView.Adapter<pTable_Adapter.pTableViewHolder>{
    private List<Element> elementSet;
    private com.example.pubchem_chemistry_handbook.data.global global;

    static class pTableViewHolder extends RecyclerView.ViewHolder{
        View pTableView;
        pTableViewHolder(View v) {
            super(v);
            pTableView = v;
        }
}

    public pTable_Adapter(List<Element> elements, global global) {
        elementSet = elements;
        this.global = global;
    }

    @NonNull
    @Override
    public pTable_Adapter.pTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ptable_element, parent, false);
        return new pTableViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull pTableViewHolder holder, int position) {
        GradientDrawable gd = null;
        if (elementSet.get(position).getAtomicNumber() > 0) {
            switch (global.getStyle()) {
                case 0:
                    switch (elementSet.get(position).getChemicalGroupBlock()) {
                        case "Nonmetal":
                        case "Halogen":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFFFFD4,0xFFFFFFCE});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#DEDDB8"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Noble gas":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFEE8D1,0xFFFEE5CC});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#DDC7B0"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Alkali metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFDD0D3,0xFFFCCCD0});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#DCB1B4"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Alkaline earth metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFD8D8FF,0xFFD7D5FF});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#BCBDDE"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Metalloid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFE5F0D1,0xFFE2EECB});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#C8D1B8"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Post-transition metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFCEFFD3,0xFFC7FFCD});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#B4DDB8"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Transition metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFCCE4FF,0xFFC5DFFF});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#B1C5DD"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Lanthanide":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFC3FFFF,0xFFBCFFFF});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#A7DEDD"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Actinide":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFC3FFED,0xFFBCFFEC});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#ADDED0"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                    }
                    break;
                case 1:
                    switch (elementSet.get(position).getStandardState()) {
                        case "Gas":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFCEFFFF,0xFFC7FFFF});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#B6DEDE"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Solid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFF2F2F3,0xFFF1F1F2});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#D1D2D3"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Liquid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFDCED1,0xFFFCC6CA});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#DCB1B4"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Expected to be a Solid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFCFCFC,0xFFFBFBFB});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#DBDBDB"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                        case "Expected to be a Gas":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFEFFFFF,0xFFE6FEFE});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.parseColor("#D2DEDD"));
                            (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                            break;
                    }
                    break;
                case 3:
                    int Max = 0xFFFFFFFF;
                    int Min = 0xFF61E0C3;
                    int bottom = (int) ((elementSet.get(position).getAtomicMass()) * 0x9E1F3C + 0xFF61E0C3);
                    if (bottom > Max) bottom = Max;
                    if (bottom < Min) bottom = Min;
                    int top = bottom + 0x180509;
                    if (top > Max) top = Max;
                    if (top < Min) top = Min;
                    int boarder = bottom - 0x111E1B;
                    gd = new GradientDrawable(
                            GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[] {bottom,top});
                    gd.setCornerRadius(5f);
                    gd.setStroke(1, boarder);
                    (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
                default:
                    gd = new GradientDrawable(
                            GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[] {0xFFC9F5EA,0xFFB5F1E3});
                    gd.setCornerRadius(5f);
                    gd.setStroke(1, Color.parseColor("#AFD5CC"));
                    (holder.pTableView.findViewById(R.id.element_background)).setBackgroundDrawable(gd);
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
                if (elementSet.get(position).getAtomicNumber() == -2) {
                    (holder.pTableView.findViewById(R.id.element_background)).setBackground(null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(25, 25);
                    (holder.pTableView.findViewById(R.id.element_background)).setLayoutParams(lp);
                } else {
                    (holder.pTableView.findViewById(R.id.element_background)).setBackground(null);
                    ((TextView)holder.pTableView.findViewById(R.id.element_atomic_number)).setText(null);
                    ((TextView)holder.pTableView.findViewById(R.id.element_symbol)).setText(null);
                    ((TextView)holder.pTableView.findViewById(R.id.element_name)).setText(null);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return elementSet.size();
    }
}


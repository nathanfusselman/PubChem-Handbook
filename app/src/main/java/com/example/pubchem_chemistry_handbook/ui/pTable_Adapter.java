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

import java.util.ArrayList;
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
        View background = (View) holder.pTableView.findViewById(R.id.element_background);
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
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Noble gas":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFEE8D1,0xFFFEE5CC});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Alkali metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFDD0D3,0xFFFCCCD0});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Alkaline earth metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFD8D8FF,0xFFD7D5FF});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Metalloid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFE5F0D1,0xFFE2EECB});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Post-transition metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFCEFFD3,0xFFC7FFCD});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Transition metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFCCE4FF,0xFFC5DFFF});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Lanthanide":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFC3FFFF,0xFFBCFFFF});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Actinide":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFC3FFED,0xFFBCFFEC});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
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
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Solid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFF2F2F3,0xFFF1F1F2});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Liquid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFDCED1,0xFFFCC6CA});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Expected to be a Solid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFCFCFC,0xFFFBFBFB});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Expected to be a Gas":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFEFFFFF,0xFFE6FEFE});
                            gd.setCornerRadius(5f);
                            gd.setStroke(1, Color.argb(50,0,0,0));
                            background.setBackgroundDrawable(gd);
                            break;
                    }
                    break;
                case 2:
                    int R_Bottom = 97;
                    int G_Bottom = 224;
                    int B_Bottom = 195;
                    int R_Top = 121;
                    int G_Top = 229;
                    int B_Top = 204;
                    double scale = elementSet.get(position).getAtomicMass() / (294.214-1.008);
                    int alpha = (int) (255*scale);
                    int bottom = Color.argb(alpha,R_Bottom,G_Bottom,B_Bottom);
                    int top = Color.argb(alpha,R_Top,G_Top,B_Top);

                    gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[] {bottom,top});
                    gd.setCornerRadius(5f);
                    gd.setStroke(1, Color.argb(50,0,0,0));
                    background.setBackgroundDrawable(gd);
                    break;
                case 3:
                    char config[] = {'s','s','s','s','p','p','p','p','p','p','s','s','p','p','p','p','p','p','s','s','d','d','d','d','d','d','d','d','d','d','p','p','p','p','p','p','s','s','d','d','d','d','d','d','d','d','d','d','p','p','p','p','p','p','s','s','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','d','d','d','d','d','d','d','d','d','p','p','p','p','p','p','s','s','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','d','d','d','d','d','d','d','d','d','p','p','p','p','p','p'};
                    if (elementSet.get(position).getAtomicNumber()>0) {
                        switch (config[elementSet.get(position).getAtomicNumber()-1]) {
                            case 's':
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFFDCDD0,0xFFFCC5C9});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                background.setBackgroundDrawable(gd);
                                break;
                            case 'p':
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFCEFFD3,0xFFC6FFCD});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                background.setBackgroundDrawable(gd);
                                break;
                            case 'd':
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFFFFFD5,0xFFFFFFCE});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                background.setBackgroundDrawable(gd);
                                break;
                            case 'f':
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFCCE3FF,0xFFC4DEFF});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                background.setBackgroundDrawable(gd);
                                break;
                        }
                    }
                    break;
                case 4:
                    int MaxR = 250;
                    int MaxG = 131;
                    int MaxB = 139;
                    int MidR = 255;
                    int MidG = 255;
                    int MidB = 255;
                    int MinR = 192;
                    int MinG = 255;
                    int MinB = 255;
                    List<Integer> colorfadeList = new ArrayList<>();
                    int point = 0;
                    try {
                        for (int i = 0; i < elementSet.get(position).getOxidationStates().length(); i++) {
                            if (elementSet.get(position).getOxidationStates().charAt(i) == '+') point = 1;
                            if (elementSet.get(position).getOxidationStates().charAt(i) == '-') point = -1;
                            if (elementSet.get(position).getOxidationStates().charAt(i) == ',') point = 0;
                            if (Character.isDigit(elementSet.get(position).getOxidationStates().charAt(i))) {
                                if (point == -1) {
                                    float colorscale = Float.parseFloat(String.valueOf(elementSet.get(position).getOxidationStates().charAt(i)))/4;
                                    int coloralpha = (int) (255*colorscale);
                                    colorfadeList.add(Color.argb(coloralpha, MinR, MinG, MinB));
                                } else {
                                    float colorscale = Float.parseFloat(String.valueOf(elementSet.get(position).getOxidationStates().charAt(i)))/9;
                                    int coloralpha = (int) (255*colorscale);
                                    colorfadeList.add(Color.argb(coloralpha, MaxR, MaxG, MaxB));
                                }
                            }
                        }
                    } catch (Exception ex) {

                    }
                    if (colorfadeList.size() == 0) {
                        colorfadeList.add(Color.argb(255, MidR, MidG, MidB));
                        colorfadeList.add(Color.argb(255, MidR, MidG, MidB));
                    }
                    if (colorfadeList.size() == 1) {
                        colorfadeList.add(colorfadeList.get(0));
                    }
                    int colors[] = new int[colorfadeList.size()];
                    int i = 0;
                    for (int color : colorfadeList) {
                        colors[i] = color;
                        i++;
                    }
                    gd = new GradientDrawable(
                            GradientDrawable.Orientation.BOTTOM_TOP,
                            colors);
                    gd.setCornerRadius(5f);
                    gd.setStroke(1, Color.argb(50,0,0,0));
                    background.setBackgroundDrawable(gd);
                    break;
                default:
                    gd = new GradientDrawable(
                            GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[] {0xFFC9F5EA,0xFFB5F1E3});
                    gd.setCornerRadius(5f);
                    gd.setStroke(1, Color.argb(50,0,0,0));
                    background.setBackgroundDrawable(gd);
                    break;
            }
            ((TextView)holder.pTableView.findViewById(R.id.element_atomic_number)).setText(Integer.toString(elementSet.get(position).getAtomicNumber()));
            ((TextView)holder.pTableView.findViewById(R.id.element_symbol)).setText(elementSet.get(position).getSymbol());
            ((TextView)holder.pTableView.findViewById(R.id.element_name)).setText(elementSet.get(position).getName());
        } else {
            if (elementSet.get(position).getAtomicNumber() == -1) {
                background.setBackground(null);
                ((TextView)holder.pTableView.findViewById(R.id.element_symbol)).setText(elementSet.get(position).getSymbol());
            } else {
                if (elementSet.get(position).getAtomicNumber() == -2) {
                    background.setBackground(null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(25, 25);
                    background.setLayoutParams(lp);
                } else {
                    background.setBackground(null);
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


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
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Noble gas":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFEE8D1,0xFFFEE5CC});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Alkali metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFDD0D3,0xFFFCCCD0});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Alkaline earth metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFD8D8FF,0xFFD7D5FF});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Metalloid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFE5F0D1,0xFFE2EECB});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Post-transition metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFCEFFD3,0xFFC7FFCD});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Transition metal":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFCCE4FF,0xFFC5DFFF});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Lanthanide":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFC3FFFF,0xFFBCFFFF});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Actinide":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFC3FFED,0xFFBCFFEC});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
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
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Solid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFF2F2F3,0xFFF1F1F2});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Liquid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFDCED1,0xFFFCC6CA});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Expected to be a Solid":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFFCFCFC,0xFFFBFBFB});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
                            background.setBackgroundDrawable(gd);
                            break;
                        case "Expected to be a Gas":
                            gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    new int[] {0xFFEFFFFF,0xFFE6FEFE});
                            gd.setCornerRadius(5f);
                            gd.setStroke(2, Color.argb(64,100,100,100));
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
                    gd.setStroke(2, Color.argb(64,100,100,100));
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
                                gd.setStroke(2, Color.argb(64,100,100,100));
                                background.setBackgroundDrawable(gd);
                                break;
                            case 'p':
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFCEFFD3,0xFFC6FFCD});
                                gd.setCornerRadius(5f);
                                gd.setStroke(2, Color.argb(64,100,100,100));
                                background.setBackgroundDrawable(gd);
                                break;
                            case 'd':
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFFFFFD5,0xFFFFFFCE});
                                gd.setCornerRadius(5f);
                                gd.setStroke(2, Color.argb(64,100,100,100));
                                background.setBackgroundDrawable(gd);
                                break;
                            case 'f':
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFCCE3FF,0xFFC4DEFF});
                                gd.setCornerRadius(5f);
                                gd.setStroke(2, Color.argb(64,100,100,100));
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
                    gd.setStroke(2, Color.argb(64,100,100,100));
                    background.setBackgroundDrawable(gd);
                    break;
                case 5:
                    int R_Bottom_5 = 97;
                    int G_Bottom_5 = 224;
                    int B_Bottom_5 = 195;
                    int R_Top_5 = 121;
                    int G_Top_5 = 229;
                    int B_Top_5 = 204;
                    double scale_5;
                    int alpha_5;
                    int bottom_5;
                    int top_5;
                    if (elementSet.get(position).getElectronegativity() != -10) {
                        scale_5 = elementSet.get(position).getElectronegativity() / 4;
                        alpha_5 = (int) (255*scale_5);
                        bottom_5 = Color.argb(alpha_5,R_Bottom_5,G_Bottom_5,B_Bottom_5);
                        top_5 = Color.argb(alpha_5,R_Top_5,G_Top_5,B_Top_5);
                    } else {
                        top_5 = 0xFFF2F2F2;
                        bottom_5 = 0xFFF2F2F2;
                    }

                    gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[] {bottom_5,top_5});
                    gd.setCornerRadius(5f);
                    gd.setStroke(2, Color.argb(64,100,100,100));
                    background.setBackgroundDrawable(gd);
                    break;
                case 6:
                    List<Integer> colors_6 = new ArrayList<>();
                    for (int i_clock = 0; i_clock < 5; i_clock++) {
                        colors_6.add(0xFF79E5CC);
                    }
                    for (int i_clock = 0; i_clock < 10; i_clock++) {
                        colors_6.add(0xFFF2F2F2);
                    }
                    int colors_6_array[] = new int[colors_6.size()];
                    int i_6 = 0;
                    for (int color : colors_6) {
                        colors_6_array[i_6] = color;
                        i_6++;
                    }
                    gd = new GradientDrawable();
                    gd.setGradientType(GradientDrawable.RADIAL_GRADIENT);
                    gd.setColors(colors_6_array);
                    gd.setGradientRadius(elementSet.get(position).getAtomicRadius()/3);
                    gd.setCornerRadius(5f);
                    gd.setStroke(2, Color.argb(64,100,100,100));
                    background.setBackgroundDrawable(gd);
                    break;
                case 7:
                    int R_Bottom_7 = 97;
                    int G_Bottom_7 = 224;
                    int B_Bottom_7 = 195;
                    int R_Top_7 = 121;
                    int G_Top_7 = 229;
                    int B_Top_7 = 204;
                    double scale_7;
                    int alpha_7;
                    int bottom_7;
                    int top_7;
                    if (elementSet.get(position).getIonizationEnergy() != -10) {
                        scale_7 = elementSet.get(position).getIonizationEnergy() / 25;
                        alpha_7 = (int) (255*scale_7);
                        bottom_7 = Color.argb(alpha_7,R_Bottom_7,G_Bottom_7,B_Bottom_7);
                        top_7 = Color.argb(alpha_7,R_Top_7,G_Top_7,B_Top_7);
                    } else {
                        top_7 = 0xFFF2F2F2;
                        bottom_7 = 0xFFF2F2F2;
                    }

                    gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[] {bottom_7,top_7});
                    gd.setCornerRadius(5f);
                    gd.setStroke(2, Color.argb(64,100,100,100));
                    background.setBackgroundDrawable(gd);
                    break;
                case 8:
                    int R_Bottom_8 = 97;
                    int G_Bottom_8 = 224;
                    int B_Bottom_8 = 195;
                    int R_Top_8 = 121;
                    int G_Top_8 = 229;
                    int B_Top_8 = 204;
                    double scale_8;
                    int alpha_8;
                    int bottom_8;
                    int top_8;
                    if (elementSet.get(position).getElectronAffinity() != -10) {
                        scale_8 = elementSet.get(position).getElectronAffinity() / 4;
                        alpha_8 = (int) (255*scale_8);
                        bottom_8 = Color.argb(alpha_8,R_Bottom_8,G_Bottom_8,B_Bottom_8);
                        top_8 = Color.argb(alpha_8,R_Top_8,G_Top_8,B_Top_8);
                    } else {
                        top_8 = 0xFFF2F2F2;
                        bottom_8 = 0xFFF2F2F2;
                    }

                    gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[] {bottom_8,top_8});
                    gd.setCornerRadius(5f);
                    gd.setStroke(2, Color.argb(64,100,100,100));
                    background.setBackgroundDrawable(gd);
                    break;
                case 9:
                    int R_Bottom_9 = 156;
                    int G_Bottom_9 = 251;
                    int B_Bottom_9 = 253;
                    int R_Top_9 = 169;
                    int G_Top_9 = 252;
                    int B_Top_9 = 253;
                    double scale_9;
                    int alpha_9;
                    int bottom_9;
                    int top_9;
                    if (elementSet.get(position).getMeltingPoint() != -10) {
                        scale_9 = elementSet.get(position).getMeltingPoint() / 4000;
                        alpha_9 = (int) (255*scale_9);
                        bottom_9 = Color.argb(alpha_9,R_Bottom_9,G_Bottom_9,B_Bottom_9);
                        top_9 = Color.argb(alpha_9,R_Top_9,G_Top_9,B_Top_9);
                    } else {
                        top_9 = 0xFFF2F2F2;
                        bottom_9 = 0xFFF2F2F2;
                    }

                    gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[] {bottom_9,top_9});
                    gd.setCornerRadius(5f);
                    gd.setStroke(2, Color.argb(64,100,100,100));
                    background.setBackgroundDrawable(gd);
                    break;
                case 10:
                    int R_Bottom_10 = 241;
                    int G_Bottom_10 = 115;
                    int B_Bottom_10 = 104;
                    int R_Top_10 = 242;
                    int G_Top_10 = 132;
                    int B_Top_10 = 123;
                    double scale_10;
                    int alpha_10;
                    int bottom_10;
                    int top_10;
                    if (elementSet.get(position).getBoilingPoint() != -10) {
                        scale_10 = elementSet.get(position).getBoilingPoint() / 6000;
                        alpha_10 = (int) (255*scale_10);
                        bottom_10 = Color.argb(alpha_10,R_Bottom_10,G_Bottom_10,B_Bottom_10);
                        top_10 = Color.argb(alpha_10,R_Top_10,G_Top_10,B_Top_10);
                    } else {
                        top_10 = 0xFFF2F2F2;
                        bottom_10 = 0xFFF2F2F2;
                    }

                    gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[] {bottom_10,top_10});
                    gd.setCornerRadius(5f);
                    gd.setStroke(2, Color.argb(64,100,100,100));
                    background.setBackgroundDrawable(gd);
                    break;
                case 11:
                    int R_Bottom_11 = 97;
                    int G_Bottom_11 = 224;
                    int B_Bottom_11 = 195;
                    int R_Top_11 = 121;
                    int G_Top_11 = 229;
                    int B_Top_11 = 204;
                    double scale_11;
                    int alpha_11;
                    int bottom_11;
                    int top_11;
                    if (elementSet.get(position).getDensity() != -10) {
                        scale_11 = elementSet.get(position).getDensity() / 23;
                        alpha_11 = (int) (255*scale_11);
                        bottom_11 = Color.argb(alpha_11,R_Bottom_11,G_Bottom_11,B_Bottom_11);
                        top_11 = Color.argb(alpha_11,R_Top_11,G_Top_11,B_Top_11);
                    } else {
                        top_11 = 0xFFF2F2F2;
                        bottom_11 = 0xFFF2F2F2;
                    }

                    gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[] {bottom_11,top_11});
                    gd.setCornerRadius(5f);
                    gd.setStroke(2, Color.argb(64,100,100,100));
                    background.setBackgroundDrawable(gd);
                    break;
                case 12:
                    int R_Bottom_12 = 97;
                    int G_Bottom_12 = 224;
                    int B_Bottom_12 = 195;
                    int R_Top_12 = 121;
                    int G_Top_12 = 229;
                    int B_Top_12 = 204;
                    double scale_12;
                    int alpha_12;
                    int bottom_12;
                    int top_12;
                    try {
                        scale_12 = (Double.parseDouble(elementSet.get(position).getYearDiscoverd()) / (1500-2050)) + 3.75;
                        alpha_12 = (int) (255*scale_12);
                        bottom_12 = Color.argb(alpha_12,R_Bottom_12,G_Bottom_12,B_Bottom_12);
                        top_12 = Color.argb(alpha_12,R_Top_12,G_Top_12,B_Top_12);
                    } catch (Exception ex) {
                        bottom_12 = Color.argb(255,R_Bottom_12,G_Bottom_12,B_Bottom_12);
                        top_12 = Color.argb(255,R_Top_12,G_Top_12,B_Top_12);
                    }

                    gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[] {bottom_12,top_12});
                    gd.setCornerRadius(5f);
                    gd.setStroke(2, Color.argb(64,100,100,100));
                    background.setBackgroundDrawable(gd);
                    break;
                default:
                    gd = new GradientDrawable(
                            GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[] {0xFFC9F5EA,0xFFB5F1E3});
                    gd.setCornerRadius(5f);
                    gd.setStroke(2, Color.argb(64,100,100,100));
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


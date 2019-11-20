package com.example.pubchem_chemistry_handbook.ui.pTable;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.pubchem_chemistry_handbook.MainActivity.pTablePosition;

public class ElementFragment extends Fragment {
    public static Boolean fragExists;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragExists=true;
        final View elementView = inflater.inflate(R.layout.fragment_element, container, false);

        final ConstraintLayout elementCard = elementView.findViewById(R.id.element_card_background);
        final TextView atomicNumber = elementView.findViewById(R.id.element_card_atomic_number);
        final TextView symbol = elementView.findViewById(R.id.element_card_symbol);
        final TextView name = elementView.findViewById(R.id.element_card_name);
        final TextView group = elementView.findViewById(R.id.element_card_group);
        final TextView mass = elementView.findViewById(R.id.element_card_mass);

        final TextView detailsStandardState = elementView.findViewById(R.id.element_delatils_Standard_State);
        final TextView detailsAtomicMass = elementView.findViewById(R.id.element_delatils_Atomic_Mass);
        final TextView detailsElectronConfiguration = elementView.findViewById(R.id.element_delatils_Electron_Configuration);
        final TextView detailsOxidationStates = elementView.findViewById(R.id.element_delatils_Oxidation_States);
        final TextView detailsElectronegativity = elementView.findViewById(R.id.element_delatils_Electronegativity);
        final TextView detailsAtomicRadius = elementView.findViewById(R.id.element_delatils_Atomic_Radius);
        final TextView detailsIonizationEnergy = elementView.findViewById(R.id.element_delatils_Ionization_Energy);
        final TextView detailsElectronAffinity = elementView.findViewById(R.id.element_delatils_Electron_Affinity);
        final TextView detailsMeltingPoint = elementView.findViewById(R.id.element_delatils_Melting_Point);
        final TextView detailsBoilingPoint = elementView.findViewById(R.id.element_delatils_Boiling_Point);
        final TextView detailsDensity = elementView.findViewById(R.id.element_delatils_Density);
        final TextView detailsYearDiscovered = elementView.findViewById(R.id.element_delatils_Year_Discovered);
        int position = pTablePosition;
        GradientDrawable gd = null;
        if (getActivity() != null) {
            if (((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicNumber() > 0) {
                switch (((MainActivity) getActivity()).getGlobal().getStyle()) {
                    case 0:
                        switch (((MainActivity) getActivity()).getGlobal().getElements().get(position).getChemicalGroupBlock()) {
                            case "Nonmetal":
                            case "Halogen":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFFFFFD4,0xFFFFFFCE});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Noble gas":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFFEE8D1,0xFFFEE5CC});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Alkali metal":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFFDD0D3,0xFFFCCCD0});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Alkaline earth metal":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFD8D8FF,0xFFD7D5FF});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Metalloid":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFE5F0D1,0xFFE2EECB});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Post-transition metal":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFCEFFD3,0xFFC7FFCD});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Transition metal":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFCCE4FF,0xFFC5DFFF});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Lanthanide":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFC3FFFF,0xFFBCFFFF});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Actinide":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFC3FFED,0xFFBCFFEC});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                        }
                        break;
                    case 1:
                        switch (((MainActivity) getActivity()).getGlobal().getElements().get(position).getStandardState()) {
                            case "Gas":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFCEFFFF,0xFFC7FFFF});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Solid":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFF2F2F3,0xFFF1F1F2});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Liquid":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFFDCED1,0xFFFCC6CA});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Expected to be a Solid":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFFCFCFC,0xFFFBFBFB});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
                                break;
                            case "Expected to be a Gas":
                                gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP,
                                        new int[] {0xFFEFFFFF,0xFFE6FEFE});
                                gd.setCornerRadius(5f);
                                gd.setStroke(1, Color.argb(50,0,0,0));
                                elementCard.setBackgroundDrawable(gd);
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
                        double scale = ((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicMass() / (294.214-1.008);
                        int alpha = (int) (255*scale);
                        int bottom = Color.argb(alpha,R_Bottom,G_Bottom,B_Bottom);
                        int top = Color.argb(alpha,R_Top,G_Top,B_Top);

                        gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[] {bottom,top});
                        gd.setCornerRadius(5f);
                        gd.setStroke(1, Color.argb(50,0,0,0));
                        elementCard.setBackgroundDrawable(gd);
                        break;
                    case 3:
                        char config[] = {'s','s','s','s','p','p','p','p','p','p','s','s','p','p','p','p','p','p','s','s','d','d','d','d','d','d','d','d','d','d','p','p','p','p','p','p','s','s','d','d','d','d','d','d','d','d','d','d','p','p','p','p','p','p','s','s','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','d','d','d','d','d','d','d','d','d','p','p','p','p','p','p','s','s','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','d','d','d','d','d','d','d','d','d','p','p','p','p','p','p'};
                        if (((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicNumber()>0) {
                            switch (config[((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicNumber()-1]) {
                                case 's':
                                    gd = new GradientDrawable(
                                            GradientDrawable.Orientation.BOTTOM_TOP,
                                            new int[] {0xFFFDCDD0,0xFFFCC5C9});
                                    gd.setCornerRadius(5f);
                                    gd.setStroke(1, Color.argb(50,0,0,0));
                                    elementCard.setBackgroundDrawable(gd);
                                    break;
                                case 'p':
                                    gd = new GradientDrawable(
                                            GradientDrawable.Orientation.BOTTOM_TOP,
                                            new int[] {0xFFCEFFD3,0xFFC6FFCD});
                                    gd.setCornerRadius(5f);
                                    gd.setStroke(1, Color.argb(50,0,0,0));
                                    elementCard.setBackgroundDrawable(gd);
                                    break;
                                case 'd':
                                    gd = new GradientDrawable(
                                            GradientDrawable.Orientation.BOTTOM_TOP,
                                            new int[] {0xFFFFFFD5,0xFFFFFFCE});
                                    gd.setCornerRadius(5f);
                                    gd.setStroke(1, Color.argb(50,0,0,0));
                                    elementCard.setBackgroundDrawable(gd);
                                    break;
                                case 'f':
                                    gd = new GradientDrawable(
                                            GradientDrawable.Orientation.BOTTOM_TOP,
                                            new int[] {0xFFCCE3FF,0xFFC4DEFF});
                                    gd.setCornerRadius(5f);
                                    gd.setStroke(1, Color.argb(50,0,0,0));
                                    elementCard.setBackgroundDrawable(gd);
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
                            for (int i = 0; i < ((MainActivity) getActivity()).getGlobal().getElements().get(position).getOxidationStates().length(); i++) {
                                if (((MainActivity) getActivity()).getGlobal().getElements().get(position).getOxidationStates().charAt(i) == '+') point = 1;
                                if (((MainActivity) getActivity()).getGlobal().getElements().get(position).getOxidationStates().charAt(i) == '-') point = -1;
                                if (((MainActivity) getActivity()).getGlobal().getElements().get(position).getOxidationStates().charAt(i) == ',') point = 0;
                                if (Character.isDigit(((MainActivity) getActivity()).getGlobal().getElements().get(position).getOxidationStates().charAt(i))) {
                                    if (point == -1) {
                                        float colorscale = Float.parseFloat(String.valueOf(((MainActivity) getActivity()).getGlobal().getElements().get(position).getOxidationStates().charAt(i)))/4;
                                        int coloralpha = (int) (255*colorscale);
                                        colorfadeList.add(Color.argb(coloralpha, MinR, MinG, MinB));
                                    } else {
                                        float colorscale = Float.parseFloat(String.valueOf(((MainActivity) getActivity()).getGlobal().getElements().get(position).getOxidationStates().charAt(i)))/9;
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
                        elementCard.setBackgroundDrawable(gd);
                        break;
                    default:
                        gd = new GradientDrawable(
                                GradientDrawable.Orientation.BOTTOM_TOP,
                                new int[] {0xFFC9F5EA,0xFFB5F1E3});
                        gd.setCornerRadius(5f);
                        gd.setStroke(1, Color.argb(50,0,0,0));
                        elementCard.setBackgroundDrawable(gd);
                        break;
                }
                atomicNumber.setText(Integer.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicNumber()));
                symbol.setText(((MainActivity) getActivity()).getGlobal().getElements().get(position).getSymbol());
                name.setText(((MainActivity) getActivity()).getGlobal().getElements().get(position).getName());
                group.setText(((MainActivity) getActivity()).getGlobal().getElements().get(position).getChemicalGroupBlock());
                mass.setText(Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicMass()));
                detailsStandardState.setText(((MainActivity) getActivity()).getGlobal().getElements().get(position).getStandardState());
                detailsAtomicMass.setText(Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicMass()) + " u");
                detailsElectronConfiguration.setText(((MainActivity) getActivity()).getGlobal().getElements().get(position).getElectronConfiguration());
                detailsOxidationStates.setText(((MainActivity) getActivity()).getGlobal().getElements().get(position).getOxidationStates());
                detailsElectronegativity.setText(Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getElectronegativity()));
                detailsAtomicRadius.setText(Integer.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicRadius()) + " pm");
                detailsIonizationEnergy.setText(Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getIonizationEnergy()) + " eV");
                detailsElectronAffinity.setText(Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getElectronAffinity()) + " eV");
                detailsMeltingPoint.setText(Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getMeltingPoint()) + " K");
                detailsBoilingPoint.setText(Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getBoilingPoint()) + " K");
                detailsDensity.setText(Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getDensity()) + " g/cm3");
                detailsYearDiscovered.setText(((MainActivity) getActivity()).getGlobal().getElements().get(position).getYearDiscoverd());
            }
        }
        return elementView;

    }

}

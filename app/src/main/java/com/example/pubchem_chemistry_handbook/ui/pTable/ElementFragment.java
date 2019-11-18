package com.example.pubchem_chemistry_handbook.ui.pTable;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
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
        if (getActivity() != null) {
            if (((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicNumber() > 0) {
                switch (((MainActivity) getActivity()).getGlobal().getElements().get(position).getChemicalGroupBlock()) {
                    case "Nonmetal":
                        elementCard.setBackgroundColor(Color.parseColor("#FEFECE"));
                        break;
                    case "Halogen":
                        elementCard.setBackgroundColor(Color.parseColor("#FEFED3"));
                        break;
                    case "Noble gas":
                        elementCard.setBackgroundColor(Color.parseColor("#FBE4C8"));
                        break;
                    case "Alkali metal":
                        elementCard.setBackgroundColor(Color.parseColor("#FACFCB"));
                        break;
                    case "Alkaline earth metal":
                        elementCard.setBackgroundColor(Color.parseColor("#D3D4FD"));
                        break;
                    case "Metalloid":
                        elementCard.setBackgroundColor(Color.parseColor("#E2EDC9"));
                        break;
                    case "Post-transition metal":
                        elementCard.setBackgroundColor(Color.parseColor("#DBFDD1"));
                        break;
                    case "Transition metal":
                        elementCard.setBackgroundColor(Color.parseColor("#CADDFD"));
                        break;
                    case "Lanthanide":
                        elementCard.setBackgroundColor(Color.parseColor("#D4FEFF"));
                        break;
                    case "Actinide":
                        elementCard.setBackgroundColor(Color.parseColor("#D7FEED"));
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

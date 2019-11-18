package com.example.pubchem_chemistry_handbook.ui.pTable;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.ui.pTable_Adapter;

public class pTableFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        pTable_Adapter mAdapter;

        if(getActivity()!=null){InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()){
            ((MainActivity) getActivity()).clearKeyboard();}}

        View view = inflater.inflate(R.layout.fragment_ptable, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        recyclerView = view.findViewById(R.id.pTable_Recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 18);
        recyclerView.setLayoutManager(layoutManager);

        final LinearLayout elementCard = view.findViewById(R.id.element_card_background);
        final TextView atomicNumber = view.findViewById(R.id.element_card_atomic_number);
        final TextView symbol = view.findViewById(R.id.element_card_symbol);
        final TextView name = view.findViewById(R.id.element_card_name);
        final TextView group = view.findViewById(R.id.element_card_group);
        final TextView mass = view.findViewById(R.id.element_card_mass);

        final TextView detailsStandardState = view.findViewById(R.id.element_delatils_Standard_State);
        final TextView detailsAtomicMass = view.findViewById(R.id.element_delatils_Atomic_Mass);
        final TextView detailsElectronConfiguration = view.findViewById(R.id.element_delatils_Electron_Configuration);
        final TextView detailsOxidationStates = view.findViewById(R.id.element_delatils_Oxidation_States);
        final TextView detailsElectronegativity = view.findViewById(R.id.element_delatils_Electronegativity);
        final TextView detailsAtomicRadius = view.findViewById(R.id.element_delatils_Atomic_Radius);
        final TextView detailsIonizationEnergy = view.findViewById(R.id.element_delatils_Ionization_Energy);
        final TextView detailsElectronAffinity = view.findViewById(R.id.element_delatils_Electron_Affinity);
        final TextView detailsMeltingPoint = view.findViewById(R.id.element_delatils_Melting_Point);
        final TextView detailsBoilingPoint = view.findViewById(R.id.element_delatils_Boiling_Point);
        final TextView detailsDensity = view.findViewById(R.id.element_delatils_Density);
        final TextView detailsYearDiscovered = view.findViewById(R.id.element_delatils_Year_Discovered);

        mAdapter = new pTable_Adapter(((MainActivity)getActivity()).getGlobal().getElements());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
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
                                detailsStandardState.setText("Standard State: " + ((MainActivity) getActivity()).getGlobal().getElements().get(position).getStandardState());
                                detailsAtomicMass.setText("Atomic Mass: " + Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicMass()) + " u");
                                detailsElectronConfiguration.setText("Electron Config: " + ((MainActivity) getActivity()).getGlobal().getElements().get(position).getElectronConfiguration());
                                detailsOxidationStates.setText("Oxidation States: " + ((MainActivity) getActivity()).getGlobal().getElements().get(position).getOxidationStates());
                                detailsElectronegativity.setText("Electronegativity (Pauling Scale): " + Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getElectronegativity()));
                                detailsAtomicRadius.setText("Atomic Radius (van der Waals): " + Integer.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicRadius()) + " pm");
                                detailsIonizationEnergy.setText("Ionization Energy: " + Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getIonizationEnergy()) + " eV");
                                detailsElectronAffinity.setText("Electron Affinity: " + Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getElectronAffinity()) + " eV");
                                detailsMeltingPoint.setText("Melting Point: " + Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getMeltingPoint()) + " K");
                                detailsBoilingPoint.setText("Boiling Point: " + Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getBoilingPoint()) + " K");
                                detailsDensity.setText("Density: " + Double.toString(((MainActivity) getActivity()).getGlobal().getElements().get(position).getDensity()) + " g/cm3");
                                detailsYearDiscovered.setText("Year Discovered: " + ((MainActivity) getActivity()).getGlobal().getElements().get(position).getYearDiscoverd());
                            }
                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        return view;
    }
}
package com.example.pubchem_chemistry_handbook.ui.pTable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.ui.RecyclerItemClickListener;
import com.example.pubchem_chemistry_handbook.ui.compound.CompoundFragment;
import com.example.pubchem_chemistry_handbook.ui.pTable_Adapter;

import static com.example.pubchem_chemistry_handbook.MainActivity.pTablePosition;

public class pTableFragment extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        final pTable_Adapter mAdapter;

        ((MainActivity)getActivity()).getGlobal().setStyle(0);

        final CharSequence[] items = {"Chemical Group Block", "Standard State", "Atomic Mass, u","Electron Configuration","Oxidation States","Electronegativity (Pauling Scale)", "Atomic Radius (van der Waals), pm", "Ionization Energy, eV","Electron Affinity, eV","Melting Point, K","Boiling Point, K", "Density, g/cm³", "Year Discovered","None"};

        if(getActivity()!=null){InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()){
            ((MainActivity) getActivity()).clearKeyboard();}}

        final View view = inflater.inflate(R.layout.fragment_ptable, container, false);

        final Button sortButton = view.findViewById(R.id.style_button);

        sortButton.setText(items[((MainActivity)getActivity()).getGlobal().getStyle()] + "  ▼");

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        recyclerView = view.findViewById(R.id.pTable_Recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 18);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new pTable_Adapter(((MainActivity)getActivity()).getGlobal().getElements(), ((MainActivity)getActivity()).getGlobal());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if (((MainActivity) getActivity()).getGlobal().getElements().get(position).getAtomicNumber() > 0) {
                            pTablePosition = position;
                            Fragment fragment = new ElementFragment();
                            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.ptable_frag, fragment);
                            transaction.commit();
                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        sortButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Select Style")
                        .setSingleChoiceItems(items,((MainActivity)getActivity()).getGlobal().getStyle(),null)
                        .setPositiveButton( "ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                ListView lw = ((AlertDialog)dialog).getListView();
                                if(getActivity()!=null) {
                                    ((MainActivity)getActivity()).getGlobal().setStyle(lw.getCheckedItemPosition());
                                    sortButton.setText(items[((MainActivity)getActivity()).getGlobal().getStyle()] + "  ▼");
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        return view;
    }
}
package com.example.pubchem_chemistry_handbook.ui.pTable;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


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
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        pTable_Adapter mAdapter;

        if(getActivity()!=null){InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()){
            ((MainActivity) getActivity()).clearKeyboard();}}

        final View view = inflater.inflate(R.layout.fragment_ptable, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        recyclerView = view.findViewById(R.id.pTable_Recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 18);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new pTable_Adapter(((MainActivity)getActivity()).getGlobal().getElements());
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
        return view;
    }
}
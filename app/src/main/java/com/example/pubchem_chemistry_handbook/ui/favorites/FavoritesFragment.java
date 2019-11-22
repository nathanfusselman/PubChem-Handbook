package com.example.pubchem_chemistry_handbook.ui.favorites;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;
import com.example.pubchem_chemistry_handbook.ui.RVAdapter;
import com.example.pubchem_chemistry_handbook.ui.compound.CompoundFragment;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {
    private RVAdapter rvAdapter;
    private List<Compound> currentList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if(getActivity()!=null){
            InputMethodManager imm = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isAcceptingText()){
                ((MainActivity) getActivity()).clearKeyboard();}}

        RecyclerView compound_rview;
        if(getActivity() != null) {currentList = new ArrayList<>(((MainActivity)getActivity()).getGlobal().getFav());}
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        compound_rview = view.findViewById(R.id.fav_recent_recyclerview);
        rvAdapter = new RVAdapter(currentList, ((MainActivity)getActivity()).getGlobal());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        compound_rview.setLayoutManager(layoutManager);
        compound_rview.setAdapter(rvAdapter);
        final Button favorites_button = view.findViewById(R.id.favorite);
        final Button recents_button = view.findViewById(R.id.recent);
        favorites_button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        recents_button.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        rvAdapter.notifyDataSetChanged();

        favorites_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favorites_button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                recents_button.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                currentList.clear();
                if(getActivity() != null) {currentList.addAll(((MainActivity)getActivity()).getGlobal().getFav());}
                rvAdapter.notifyDataSetChanged();
            }
        });

        recents_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favorites_button.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                recents_button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                currentList.clear();
                if(getActivity() != null) {currentList.addAll(((MainActivity)getActivity()).getGlobal().getRecents());}
                rvAdapter.notifyDataSetChanged();
            }
        });

        rvAdapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(final int position) {
                ((MainActivity)getActivity()).setCompViewInfo(rvAdapter.CompoundList.get(position),position);
                Fragment fragment= new CompoundFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.favorites_frag, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}

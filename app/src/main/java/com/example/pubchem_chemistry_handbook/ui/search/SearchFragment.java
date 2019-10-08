package com.example.pubchem_chemistry_handbook.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;
import com.example.pubchem_chemistry_handbook.ui.RVAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    RecyclerView compound_rview;
    List<Compound> list_compound;
    RVAdapter rvAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        loadCompound();
        compound_rview = (RecyclerView) view.findViewById(R.id.recyclerview);

        rvAdapter = new RVAdapter(getActivity(), list_compound);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        compound_rview.setLayoutManager(layoutManager);
        compound_rview.setAdapter(rvAdapter);
        return view;
    }

    void loadCompound() {
        list_compound = new ArrayList<>();
//        rvAdapter.notifyDataSetChanged();
    }
}
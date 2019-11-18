package com.example.pubchem_chemistry_handbook.ui.pSearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;
import com.example.pubchem_chemistry_handbook.ui.pTable.RecyclerItemClickListener;
import com.example.pubchem_chemistry_handbook.ui.pSearch.pSearchFragment;
import com.example.pubchem_chemistry_handbook.ui.pTable_Adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class pSearchFragment extends Fragment {

    private com.example.pubchem_chemistry_handbook.ui.pSearch.pSearchViewModel pSearchViewModel;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    pTable_Adapter mAdapter;
    final ArrayList<String> symbols = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()){
            ((MainActivity) getActivity()).clearKeyboard();}

        pSearchViewModel = ViewModelProviders.of(this).get(pSearchViewModel.class);
        View view = inflater.inflate(R.layout.fragment_ptable_search, container, false);
        final TextView currentQuery = view.findViewById(R.id.currentSearch);
        final ImageButton btnBack = view.findViewById(R.id.backspace_button);
        final ImageButton btnSearch = view.findViewById(R.id.search_button);
        final ImageButton btnClear = view.findViewById(R.id.clear_button);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        recyclerView = view.findViewById(R.id.pTable_search__Recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 18);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new pTable_Adapter(((MainActivity)getActivity()).getGlobal().getElements());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if (((MainActivity)getActivity()).getGlobal().getElements().get(position).getAtomicNumber() > 0) {
                            symbols.add(((MainActivity)getActivity()).getGlobal().getElements().get(position).getSymbol());
                            Log.d("PSearch", "Symbol Pressed " + symbols.get(symbols.size()-1).toString());
                            currentQuery.setText(symbols.toString());
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        final CharSequence[] items = {"1", "2", "3","4","5","6", "7", "8","9","10","11", "12", "13","14","15"};
                        final int cur_pos=position;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                .setTitle("Select Subscript")
                                .setSingleChoiceItems(items,position,null)
                                .setPositiveButton( "ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                ListView lw = ((AlertDialog)dialog).getListView();
                                Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                                symbols.add((((MainActivity)getActivity()).getGlobal().getElements().get(cur_pos).getSymbol())+checkedItem.toString());
                                Log.d("PSearch", "Symbol Long Pressed " + symbols.get(symbols.size()-1).toString());
                                currentQuery.setText(symbols.toString());
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
                })
        );

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(symbols.size()>0) {
                    symbols.remove(symbols.size() - 1);
                    currentQuery.setText(symbols.toString());
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("search button", "on click now");
                writeToString(symbols);
                Log.d("check for pSearch",((MainActivity) getActivity()).getPSearchQuery());
                getActivity().onBackPressed();                                             //this works but is bad hahaha, this is also what creates the news bug on second pSearch
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                symbols.clear();
                currentQuery.setText(symbols.toString());
            }
        });

        //the layout on which you are working
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.ptable__search_relative_frag);
        return view;
    }

    public void writeToString(ArrayList<String> arr)
    {
        StringBuilder sb = new StringBuilder();
        for(String i : arr){
            sb=sb.append(i);
        }
        ((MainActivity)getActivity()).setPSearchQuery(sb.toString());
    }
}

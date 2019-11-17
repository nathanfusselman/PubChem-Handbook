package com.example.pubchem_chemistry_handbook.ui.pSearch;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.ui.pTable.RecyclerItemClickListener;
import com.example.pubchem_chemistry_handbook.ui.pTable.pTableViewModel;
import com.example.pubchem_chemistry_handbook.ui.pTable_Adapter;

import java.util.ArrayList;

public class pSearchFragment extends Fragment {

    private com.example.pubchem_chemistry_handbook.ui.pTable.pTableViewModel pTableViewModel;
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

        pTableViewModel = ViewModelProviders.of(this).get(pTableViewModel.class);
        View view = inflater.inflate(R.layout.fragment_ptable, container, false);

        final TextView currentQuery = view.findViewById(R.id.element_delatils_Electron_Affinity);
        currentQuery.setTextSize(12);
        final TextView queryTitle = view.findViewById(R.id.element_delatils_Ionization_Energy);
        queryTitle.setText("Current Query:");
        currentQuery.setTextSize(14);

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

        //the layout on which you are working
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.ptable_relative_frag);

        //set the properties for button
        Button btnCoef = new Button(getContext());
        btnCoef.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnCoef.setText("Coeffecients");
        btnCoef.setTranslationX(220);
        btnCoef.setTranslationY(200);
        //add button to the layout
        layout.addView(btnCoef);
        btnCoef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"1", "2", "3","4","5","6", "7", "8","9","10","11", "12", "13","14","15"};
                int position=0;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Select Subscript")
                        .setSingleChoiceItems(items,position,null)
                        .setPositiveButton( "ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                ListView lw = ((AlertDialog)dialog).getListView();
                                Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                                symbols.add(checkedItem.toString());
                                Log.d("PSearch", "coeff added " + symbols.get(symbols.size()-1).toString());
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
        });

        Button btnClr = new Button(getContext());
        btnClr.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnClr.setText("clear");
        btnClr.setTranslationX(550);
        btnClr.setTranslationY(200);
        //add button to the layout
        layout.addView(btnClr);
        btnClr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            symbols.clear();
            currentQuery.setText(symbols.toString());
            }
        });
        Button btnBack = new Button(getContext());
        btnBack.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnBack.setText("backSpace");
        btnBack.setTranslationX(790);
        btnBack.setTranslationY(200);
        //add button to the layout
        layout.addView(btnBack);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(symbols.size()>0) {
                    symbols.remove(symbols.size() - 1);
                    currentQuery.setText(symbols.toString());
                }
            }
        });

        ImageButton btnSearch = new ImageButton(getContext());
        btnSearch.setLayoutParams(new ViewGroup.LayoutParams(130,130));
        btnSearch.setImageResource(R.drawable.ic_search_black_24dp);
        btnSearch.setPadding(75,75,75,75);
        btnSearch.setTranslationX(1070);
        btnSearch.setTranslationY(200);
        //add button to the layout
        layout.addView(btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("search button", "on click now");
                writeToString(symbols);
                Log.d("check for pSearch",((MainActivity) getActivity()).getPSearchQuery());
                getActivity().onBackPressed();                                             //this works but is bad hahaha, this is also what creates the news bug on second pSearch
            }
        });
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

package com.example.pubchem_chemistry_handbook.ui.pSearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.ui.RecyclerItemClickListener;
import com.example.pubchem_chemistry_handbook.ui.pTable_Adapter;

import java.util.ArrayList;

public class pSearchFragment extends Fragment {


    private final ArrayList<String> symbols = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        pTable_Adapter mAdapter;

        View view = inflater.inflate(R.layout.fragment_ptable_search, container, false);

        if(getActivity()!=null){getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);}
        recyclerView = view.findViewById(R.id.pTable_search__Recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 18);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new pTable_Adapter(((MainActivity)getActivity()).getGlobal().getElements());
        final TextView currentQuery = view.findViewById(R.id.currentSearch);
        final ImageButton btnBack = view.findViewById(R.id.backspace_button);
        final ImageButton btnClear = view.findViewById(R.id.clear_button);
        final ImageButton btnSearch = view.findViewById(R.id.search_button);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(getActivity()!=null){
                        if (((MainActivity)getActivity()).getGlobal().getElements().get(position).getAtomicNumber() > 0) {
                            boolean exists = false;
                            /* STYLE ONE
                            int on = 0;
                            for (String symbol : symbols) {
                                if (symbol.contains(((MainActivity)getActivity()).getGlobal().getElements().get(position).getSymbol())) {
                                    System.out.println("TRUE AT: " + symbol);
                                    exists = true;
                                    int mult = 1;
                                    int total = 0;
                                    boolean first = true;
                                    for (int i = symbol.length()-1; i >= 0; i--) {
                                        if (Character.isDigit(symbol.charAt(i))) {
                                            //System.out.println("ADDING: " + mult*Integer.parseInt(String.valueOf(symbol.charAt(i))) + " AT: " + symbol.charAt(i));
                                            total += mult*Integer.parseInt(String.valueOf(symbol.charAt(i)));
                                            mult = mult*10;
                                            first = false;
                                        }
                                    }
                                    //System.out.println("Old Total: " + total);
                                    total++;
                                    if (first) {
                                        total++;
                                    }
                                    //System.out.println("New Total: " + total);
                                    symbols.set(on, ((MainActivity)getActivity()).getGlobal().getElements().get(position).getSymbol() + total);
                                    Log.d("PSearch", "Symbol Pressed " + symbols.get(on));
                                }
                                on++;
                            }
                             */

                            //STYLE TWO
                            if (symbols.size() > 0) {
                                if (symbols.get(symbols.size()-1).contains(((MainActivity)getActivity()).getGlobal().getElements().get(position).getSymbol())) {
                                    System.out.println("TRUE AT: " + symbols.get(symbols.size()-1));
                                    exists = true;
                                    int mult = 1;
                                    int total = 0;
                                    boolean first = true;
                                    for (int i = symbols.get(symbols.size()-1).length()-1; i >= 0; i--) {
                                        if (Character.isDigit(symbols.get(symbols.size()-1).charAt(i))) {
                                            //System.out.println("ADDING: " + mult*Integer.parseInt(String.valueOf(symbol.charAt(i))) + " AT: " + symbol.charAt(i));
                                            total += mult*Integer.parseInt(String.valueOf(symbols.get(symbols.size()-1).charAt(i)));
                                            mult = mult*10;
                                            first = false;
                                        }
                                    }
                                    //System.out.println("Old Total: " + total);
                                    total++;
                                    if (first) {
                                        total++;
                                    }
                                    //System.out.println("New Total: " + total);
                                    symbols.set(symbols.size()-1, ((MainActivity)getActivity()).getGlobal().getElements().get(position).getSymbol() + total);
                                    Log.d("PSearch", "Symbol Pressed " + symbols.get(symbols.size()-1));
                                }
                            }

                            if (!exists) {
                                symbols.add(((MainActivity)getActivity()).getGlobal().getElements().get(position).getSymbol());
                                Log.d("PSearch", "Symbol Pressed " + symbols.get(symbols.size()-1));
                            }
                            currentQuery.setText((writeToString(symbols)));
                        }}
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
                                if(getActivity()!=null){symbols.add((((MainActivity)getActivity()).getGlobal().getElements().get(cur_pos).getSymbol())+checkedItem.toString());}
                                Log.d("PSearch", "Symbol Long Pressed " + symbols.get(symbols.size()-1));
                                currentQuery.setText(writeToString(symbols));
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

        btnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            symbols.clear();
            currentQuery.setText((writeToString(symbols)));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(symbols.size()>0) {
                    symbols.remove(symbols.size() - 1);
                    currentQuery.setText((writeToString(symbols)));
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("search button", "on click now");

                if(getActivity()!=null){((MainActivity)getActivity()).setPSearchQuery( writeToString(symbols));}
                Log.d("check for pSearch",((MainActivity) getActivity()).getPSearchQuery());
                getActivity().onBackPressed();}

        });
        return view;
    }

    private String writeToString(ArrayList<String> arr)
    {
        StringBuilder sb = new StringBuilder();
        for(String i : arr){
            sb=sb.append(i);
        }
        return sb.toString();
    }

}

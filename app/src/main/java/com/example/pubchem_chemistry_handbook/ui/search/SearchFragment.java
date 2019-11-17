package com.example.pubchem_chemistry_handbook.ui.search;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.pubchem_chemistry_handbook.ui.RVAdapter;
import com.example.pubchem_chemistry_handbook.ui.pSearch.pSearchFragment;

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

public class SearchFragment extends Fragment {
    private RVAdapter rvAdapter;
    private TextView resutlsNumb;
    private String search = "";
    private boolean webSearch = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final RecyclerView compound_rview;
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        compound_rview = view.findViewById(R.id.recyclerview);
        ((MainActivity)getActivity()).getGlobal().getCompounds().clear();
        ((MainActivity)getActivity()).getGlobal().getCompounds().addAll(((MainActivity)getActivity()).getGlobal().getCompoundListFull());
        rvAdapter = new RVAdapter(((MainActivity)getActivity()).getGlobal().getCompounds(), ((MainActivity)getActivity()).getGlobal());
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        compound_rview.setLayoutManager(layoutManager);
        compound_rview.setAdapter(rvAdapter);
        rvAdapter.getFilter().filter("");
        resutlsNumb = view.findViewById(R.id.resultsNumb);
        CheckBox search_type_startsWith = view.findViewById(R.id.search_type_startsWith);
        final SearchView searchView = view.findViewById(R.id.searchView);
        resutlsNumb.setText("Results: " + ((MainActivity)getActivity()).getGlobal().getResults());
        Button pSearchButton = view.findViewById(R.id.pSearchButton);
        resutlsNumb.setText("Results: " + ((MainActivity)getActivity()).getGlobal().getResults());

        pSearchButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             InputMethodManager imm = (InputMethodManager) getActivity()
                     .getSystemService(Context.INPUT_METHOD_SERVICE);
             if (imm.isAcceptingText()){
                 ((MainActivity) getActivity()).clearKeyboard();}
             Fragment fragment = new pSearchFragment();
             FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
             transaction.addToBackStack(null);
             searchView.setFocusable(false);
             searchView.clearFocus();
             transaction.add(R.id.search_frag,fragment);
             transaction.commit();

            }
        });
        search_type_startsWith.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    ((MainActivity)getActivity()).getGlobal().setSearch_type_startsWith(1);
                } else {
                    ((MainActivity)getActivity()).getGlobal().setSearch_type_startsWith(0);
                }
                rvAdapter.getFilter().filter(search);
                resutlsNumb.setText("Results: " + "...");
                ((MainActivity)getActivity()).updating = true;
                int lastNum = ((MainActivity)getActivity()).getGlobal().getResults();
                int count = 0;
                while (((MainActivity)getActivity()).updating && count < 25000000) {
                    count++;
                    if (((MainActivity)getActivity()).getGlobal().getResults() != lastNum) {
                        resutlsNumb.setText("Results: " + ((MainActivity)getActivity()).getGlobal().getResults());
                        System.out.println("Count: " + count);
                        ((MainActivity)getActivity()).updating = false;
                    }
                }
            }
        }
        );
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "LOADING RESULTS", Toast.LENGTH_SHORT).show();
                int downloadId = PRDownloader.download("https://pubchem.ncbi.nlm.nih.gov/sdq/sdqagent.cgi?infmt=json&outfmt=csv&query={%22download%22:%22*%22,%22collection%22:%22compound%22,%22where%22:{%22ands%22:[{%22*%22:%22" + s + "%22}]},%22order%22:[%22relevancescore,desc%22],%22start%22:1,%22limit%22:10000000,%22downloadfilename%22:%22search%22}", getActivity().getFilesDir().toString(), "search.csv")
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {

                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {

                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {

                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {

                            }
                        })
                        .start(new OnDownloadListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onDownloadComplete() {
                                webSearch = true;
                                List<Compound> searchList = new ArrayList<>();
                                int on = 0;
                                File file = new File(getActivity().getApplication().getFilesDir().toString() + "/search.csv");
                                InputStream is = null;
                                try {
                                    is = new FileInputStream(file);
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                                    String line = "";

                                    try {
                                        reader.readLine();
                                        while (((line = reader.readLine()) != null)) {
                                            //System.out.println(line);
                                            List<String> tokens = split(line);
                                            try {
                                                searchList.add(new Compound(Integer.parseInt(tokens.get(0)), tokens.get(1), tokens.get(4)));
                                                if (tokens.get(1).startsWith("\"")) {
                                                    searchList.get(on).setName(tokens.get(1).substring(1, tokens.get(1).length() - 1));
                                                }
                                                if (tokens.get(4).startsWith("\"")) {
                                                    searchList.get(on).setFormula(tokens.get(4).substring(1, tokens.get(4).length() - 1));
                                                }
                                                on++;
                                            } catch (IndexOutOfBoundsException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (IOException e) {
                                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                                        e.printStackTrace();
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    ((MainActivity)getActivity()).getGlobal().getCompounds().clear();
                                    ((MainActivity)getActivity()).getGlobal().getCompounds().addAll(searchList);
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    is.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                rvAdapter.notifyDataSetChanged();
                                resutlsNumb.setText("Results: " + ((MainActivity)getActivity()).getGlobal().getCompounds().size());
                                Toast.makeText(getActivity(), ((MainActivity)getActivity()).getGlobal().getCompounds().size() + " results loaded", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Error error) {
                                //Toast.makeText(getActivity(), "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
                                Log.d("PRDownloader", "onError: " + error.toString());
                            }
                        });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (webSearch) {
                    ((MainActivity)getActivity()).getGlobal().getCompounds().clear();
                    ((MainActivity)getActivity()).getGlobal().getCompounds().addAll(((MainActivity)getActivity()).getGlobal().getCompoundListFull());
                    rvAdapter.notifyDataSetChanged();
                    webSearch = false;
                }
                search = s;
                rvAdapter.getFilter().filter(search);
                resutlsNumb.setText("Results: " + "...");
                ((MainActivity)getActivity()).updating = true;
                int lastNum = ((MainActivity)getActivity()).getGlobal().getResults();
                int count = 0;
                while (((MainActivity)getActivity()).updating && count < 25000000) {
                    count++;
                    if (((MainActivity)getActivity()).getGlobal().getResults() != lastNum) {
                        resutlsNumb.setText("Results: " + ((MainActivity)getActivity()).getGlobal().getResults());
                        System.out.println("Count: " + count);
                        ((MainActivity)getActivity()).updating = false;
                    }
                }
                return false;
            }
        });
        rvAdapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ((MainActivity)getActivity()).setCompViewInfo(rvAdapter.CompoundList.get(position),position);

                Fragment fragment = new CompFragment();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.search_frag, fragment);
                transaction.commit();
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isAcceptingText()){
                    ((MainActivity) getActivity()).clearKeyboard();}
}
        });

        if(!(((MainActivity)getActivity()).getPSearchQuery().equals(""))) {
            searchView.post(new Runnable() {
                @Override
                public void run() {
                    //MenuItemCompat.expandActionView(searchMenuItem);
                    searchView.setQuery(((MainActivity)getActivity()).getPSearchQuery(), false);
                    searchView.setFocusable(false);
                    searchView.setIconified(false);
                    searchView.clearFocus();
                    ((MainActivity)getActivity()).setPSearchQuery("");
                }
            });
        }

        return view;

    }

    public List<String> split(String input) {
        boolean inP = false;
        int last = -1;
        List<String> out = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '\"') {
                if (inP == true) {
                    inP = false;
                } else {
                    if (inP == false) {
                        inP = true;
                    }
                }

            }
            if (input.charAt(i) == ',' && inP == false) {
                //System.out.println(input.substring(last + 1, i));
                out.add(input.substring(last + 1, i));
                last = i;
            }
        }
        //System.out.println(input.substring(last + 1, input.length() - 1));
        out.add(input.substring(last + 1, input.length()-1));
        return out;
    }

}
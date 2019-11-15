package com.example.pubchem_chemistry_handbook.ui.search;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.example.pubchem_chemistry_handbook.data.Element;
import com.example.pubchem_chemistry_handbook.data.SafetyItem;
import com.example.pubchem_chemistry_handbook.data.global;
import com.example.pubchem_chemistry_handbook.ui.AsyncTaskLoadImage;
import com.example.pubchem_chemistry_handbook.ui.RVAdapter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    RecyclerView compound_rview;
    RVAdapter rvAdapter;
    TextView resutlsNumb;
    String search = "";
    int current_pos = 0;
    Compound currentCompound = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        compound_rview = view.findViewById(R.id.recyclerview);
        ((MainActivity)getActivity()).getGlobal().getCompounds().clear();
        ((MainActivity)getActivity()).getGlobal().getCompounds().addAll(((MainActivity)getActivity()).getGlobal().getCompoundListFull());
        rvAdapter = new RVAdapter(getActivity(), ((MainActivity)getActivity()).getGlobal().getCompounds(), ((MainActivity)getActivity()).getGlobal());
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        compound_rview.setLayoutManager(layoutManager);
        compound_rview.setAdapter(rvAdapter);
        rvAdapter.getFilter().filter("");
        resutlsNumb = view.findViewById(R.id.resultsNumb);
        CheckBox search_type_startsWith = view.findViewById(R.id.search_type_startsWith);
        final SearchView searchView = view.findViewById(R.id.searchView);
        final ImageView[] HazardImages = new ImageView[9];
        final TextView[] HazardTexts = new TextView[9];
        resutlsNumb.setText("Results: " + ((MainActivity)getActivity()).getGlobal().getResults());
        HazardImages[0] = view.findViewById(R.id.SafetyItems_Images_GHS01);
        HazardTexts[0] = view.findViewById(R.id.SafetyItems_Text_GHS01);
        HazardImages[1] = view.findViewById(R.id.SafetyItems_Images_GHS02);
        HazardTexts[1] = view.findViewById(R.id.SafetyItems_Text_GHS02);
        HazardImages[2] = view.findViewById(R.id.SafetyItems_Images_GHS03);
        HazardTexts[2] = view.findViewById(R.id.SafetyItems_Text_GHS03);
        HazardImages[3] = view.findViewById(R.id.SafetyItems_Images_GHS04);
        HazardTexts[3] = view.findViewById(R.id.SafetyItems_Text_GHS04);
        HazardImages[4] = view.findViewById(R.id.SafetyItems_Images_GHS05);
        HazardTexts[4] = view.findViewById(R.id.SafetyItems_Text_GHS05);
        HazardImages[5] = view.findViewById(R.id.SafetyItems_Images_GHS06);
        HazardTexts[5] = view.findViewById(R.id.SafetyItems_Text_GHS06);
        HazardImages[6] = view.findViewById(R.id.SafetyItems_Images_GHS07);
        HazardTexts[6] = view.findViewById(R.id.SafetyItems_Text_GHS07);
        HazardImages[7] = view.findViewById(R.id.SafetyItems_Images_GHS08);
        HazardTexts[7] = view.findViewById(R.id.SafetyItems_Text_GHS08);
        HazardImages[8] = view.findViewById(R.id.SafetyItems_Images_GHS09);
        HazardTexts[8] = view.findViewById(R.id.SafetyItems_Text_GHS09);
        final ImageView[] StructureImages = new ImageView[3];
        final TextView[] StructureTexts = new TextView[3];
        StructureImages[0] = view.findViewById(R.id.compoundView_2dImage);
        StructureTexts[0] = view.findViewById(R.id.compoundView_images_names_2d);
        StructureImages[1] = view.findViewById(R.id.compoundView_3dImage);
        StructureTexts[1] = view.findViewById(R.id.compoundView_images_names_3d);
        StructureImages[2] = view.findViewById(R.id.compoundView_crystal);
        StructureTexts[2] = view.findViewById(R.id.compoundView_images_names_crystal);
        resutlsNumb.setText("Results: " + ((MainActivity)getActivity()).getGlobal().getResults());
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
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resutlsNumb.setText("Results: " + ((MainActivity)getActivity()).getGlobal().getResults());
                    }
                }, 500);
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
                ((MainActivity)getActivity()).getGlobal().getCompounds().clear();
                ((MainActivity)getActivity()).getGlobal().getCompounds().addAll(((MainActivity)getActivity()).getGlobal().getCompoundListFull());
                rvAdapter.notifyDataSetChanged();
                search = s;
                rvAdapter.getFilter().filter(search);
                resutlsNumb.setText("Results: " + "...");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resutlsNumb.setText("Results: " + ((MainActivity)getActivity()).getGlobal().getResults());
                    }
                }, 500);
                return false;
            }
        });
        rvAdapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ((MainActivity)getActivity()).setCompViewInfo(rvAdapter.CompoundList.get(current_pos),current_pos);
                Fragment fragment= new CompFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });


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
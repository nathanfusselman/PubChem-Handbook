package com.example.pubchem_chemistry_handbook.ui.search;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
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
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;
import com.example.pubchem_chemistry_handbook.data.SafetyItem;
import com.example.pubchem_chemistry_handbook.data.global;
import com.example.pubchem_chemistry_handbook.ui.AsyncTaskLoadImage;
import com.example.pubchem_chemistry_handbook.ui.RVAdapter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    RecyclerView compound_rview;
    List<Compound> list_compound;
    RVAdapter rvAdapter;
    TextView resutlsNumb;
    com.example.pubchem_chemistry_handbook.data.global global = new global(0,0);
    String search = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        loadCompound();
        compound_rview = view.findViewById(R.id.recyclerview);
        rvAdapter = new RVAdapter(getActivity(), list_compound, global);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        compound_rview.setLayoutManager(layoutManager);
        compound_rview.setAdapter(rvAdapter);
        rvAdapter.getFilter().filter("");
        resutlsNumb = view.findViewById(R.id.resultsNumb);
        final ScrollView compoundView = view.findViewById(R.id.compound_scrollView);
        compoundView.setBackgroundColor(0xFFFFFFFF);
        compoundView.setVisibility(View.INVISIBLE);
        final TextView compoundView_name = view.findViewById(R.id.compoundView_name);
        final TextView compoundView_formula = view.findViewById(R.id.compoundView_formula);
        final ImageView compoundView_2dImage = view.findViewById(R.id.compoundView_2dImage);
        final ImageView compoundView_3dImage = view.findViewById(R.id.compoundView_3dImage);
        final Button compoundView_backButton = view.findViewById(R.id.closeButton);
        CheckBox search_type_startsWith = view.findViewById(R.id.search_type_startsWith);
        final LinearLayout SafetyItems_Images = view.findViewById(R.id.SafetyItems_Images);
        final LinearLayout SafetyItems_Text = view.findViewById(R.id.SafetyItems_Text);
        final SearchView searchView = view.findViewById(R.id.searchView);
        resutlsNumb.setText("Results: " + global.getResults());
        compoundView_backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                compoundView.setVisibility(View.INVISIBLE);
            }
        });
        search_type_startsWith.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    global.setSearch_type_startsWith(1);
                } else {
                    global.setSearch_type_startsWith(0);
                }
                rvAdapter.getFilter().filter(search);
                resutlsNumb.setText("Results: " + "...");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resutlsNumb.setText("Results: " + global.getResults());
                    }
                }, 500);
            }
        }
        );
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search = s;
                rvAdapter.getFilter().filter(search);
                resutlsNumb.setText("Results: " + "...");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resutlsNumb.setText("Results: " + global.getResults());
                    }
                }, 500);
                return false;
            }
        });
        rvAdapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                int downloadId = PRDownloader.download("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + list_compound.get(position).getEID() + "/JSON/?response_type=save&response_basename=compound_CID_" + list_compound.get(position).getEID(), getActivity().getFilesDir().toString(), "compound-" + list_compound.get(position).getEID() + ".json")
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
                                JSONParser jsonParser = new JSONParser();

                                try (FileReader reader = new FileReader(getActivity().getFilesDir().toString() + "/compound-" + list_compound.get(position).getEID() + ".json"))
                                {
                                    //Read JSON file
                                    JSONObject obj = (JSONObject) jsonParser.parse(reader);
                                    JSONObject record = (JSONObject) obj.get("Record");
                                    String RecordTitle = (String) record.get("RecordTitle");
                                    list_compound.get(position).setName(RecordTitle);
                                    //Toast.makeText(getActivity(), "RecordTitle: " + RecordTitle, Toast.LENGTH_LONG).show();
                                    long RecordNumber = (Long) record.get("RecordNumber");
                                    list_compound.get(position).setEID((int) RecordNumber);
                                    //Toast.makeText(getActivity(), "RecordNumber: " + RecordNumber, Toast.LENGTH_LONG).show();
                                    JSONArray section = (JSONArray) record.get("Section");
                                    JSONObject section_2 = (JSONObject) section.get(2);
                                    JSONArray sub_section_2 = (JSONArray) section_2.get("Section");
                                    JSONObject sub_section_2_2 = (JSONObject) sub_section_2.get(2);
                                    JSONArray Information = (JSONArray) sub_section_2_2.get("Information");
                                    JSONObject sub_Information = (JSONObject) Information.get(0);
                                    JSONObject Value = (JSONObject) sub_Information.get("Value");
                                    JSONArray StringWithMarkup = (JSONArray) Value.get("StringWithMarkup");
                                    JSONObject sub_StringWithMarkup = (JSONObject) StringWithMarkup.get(0);
                                    String RecordFormula = (String) sub_StringWithMarkup.get("String");
                                    list_compound.get(position).setFormula(RecordFormula);
                                    //Toast.makeText(getActivity(), "RecordFormula: " + RecordFormula, Toast.LENGTH_LONG).show();
                                    JSONObject section_1 = (JSONObject) section.get(1);
                                    JSONArray Information_1 = (JSONArray) section_1.get("Information");
                                    JSONObject sub_Information_1 = (JSONObject) Information_1.get(0);
                                    JSONObject Value_1 = (JSONObject) sub_Information_1.get("Value");
                                    JSONArray StringWithMarkup_1 = (JSONArray) Value_1.get("StringWithMarkup");
                                    JSONObject sub_StringWithMarkup_1 = (JSONObject) StringWithMarkup_1.get(0);
                                    JSONArray Markup = (JSONArray) sub_StringWithMarkup_1.get("Markup");
                                    boolean[] safety = new boolean[9];
                                    for (int i = 0; i < 9; i++) {
                                        safety[i] = false;
                                    }
                                    for (int i = 0; i < Markup.size(); i++) {
                                        JSONObject sub_Markup = (JSONObject) Markup.get(i);
                                        String url = (String) sub_Markup.get("URL");
                                        String name = (String) sub_Markup.get("Extra");
                                        list_compound.get(position).addSafetyItem(name, url);
                                        //System.out.println("Added Safety: " + name + ", " + url);
                                    }
                                    for (SafetyItem item : list_compound.get(position).getSafetyItems()) {
                                        int n = Integer.parseInt(String.valueOf(item.getUrl().charAt(48)));
                                        safety[n] = true;
                                    }
                                    for (int i = 8; i >= 0; i--) {
                                        if (!safety[i]) {
                                            //System.out.println("Number of Items: " + SafetyItems_Images.getChildCount());
                                            //System.out.println("Removing Item: " + i);
                                            if (i <= SafetyItems_Images.getChildCount()) {
                                                SafetyItems_Images.removeViewAt(i);
                                                SafetyItems_Text.removeViewAt(i);
                                            }
                                        }
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Error error) {
                                Toast.makeText(getActivity(), "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
                                Log.d("PRDownloader", "onError: " + error.toString());
                            }
                        });
                compoundView_name.setText(" " + list_compound.get(position).getName());
                compoundView_formula.setText("  " + list_compound.get(position).getFormula());
                AsyncTaskLoadImage image_Loader_2d = new AsyncTaskLoadImage(compoundView_2dImage);
                image_Loader_2d.execute("https://pubchem.ncbi.nlm.nih.gov/image/imgsrv.fcgi?cid=" + list_compound.get(position).getEID() + "&t=s");
                AsyncTaskLoadImage image_Loader_3d = new AsyncTaskLoadImage(compoundView_3dImage);
                image_Loader_3d.execute("https://pubchem.ncbi.nlm.nih.gov/image/img3d.cgi?&cid=" + list_compound.get(position).getEID() + "&t=s");
                compoundView.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    void loadCompound() {
        list_compound = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.compound);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while (((line = reader.readLine()) != null)) {
                String[] tokens = line.split(";");
                if (!tokens[2].substring(1, tokens[2].length()-1).isEmpty() && !tokens[2].substring(1, tokens[2].length()-1).contentEquals(" ")) {
                    list_compound.add(new Compound(Integer.parseInt(tokens[0]), tokens[1].substring(1, tokens[1].length() - 1), tokens[2].substring(1, tokens[2].length() - 1)));
                }
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }
}
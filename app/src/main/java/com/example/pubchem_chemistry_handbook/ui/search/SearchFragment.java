package com.example.pubchem_chemistry_handbook.ui.search;

import android.content.Context;
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
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.example.pubchem_chemistry_handbook.data.global;
import com.example.pubchem_chemistry_handbook.ui.AsyncTaskLoadImage;
import com.example.pubchem_chemistry_handbook.ui.RVAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
        //final ImageView compoundView_CrystalImage = view.findViewById(R.id.compoundView_CrystalImage);
        final Button compoundView_backButton = view.findViewById(R.id.closeButton);
        CheckBox search_type_startsWith = view.findViewById(R.id.search_type_startsWith);
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
                            @Override
                            public void onDownloadComplete() {
                                Toast.makeText(getActivity(), "Download OK", Toast.LENGTH_LONG).show();
                                Log.d("PRDownloader", "onDownloadComplete: " + getActivity().getFilesDir().toString());

                                try {
                                    JSONArray jArray = new JSONArray(readJSONFromAsset(position));
                                    for (int i = 0; i < jArray.length(); ++i) {
                                        String name = jArray.getJSONObject(i).getString("RecordTitle");// name of the country
                                        String dial_code = jArray.getJSONObject(i).getString("dial_code"); // dial code of the country
                                        String code = jArray.getJSONObject(i).getString("code"); // code of the country
                                        list_compound.get(position).setName(name);
                                    }
                                } catch (JSONException e) {
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

    public String readJSONFromAsset(int pos) {
        String json = null;
        try {
            File isFile = new File(getActivity().getFilesDir().toString() + "/" +  "compound-" + list_compound.get(pos).getEID() + ".json");
            InputStream is = new FileInputStream(isFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
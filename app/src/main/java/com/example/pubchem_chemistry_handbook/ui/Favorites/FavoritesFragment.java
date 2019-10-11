package com.example.pubchem_chemistry_handbook.ui.Favorites;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    RecyclerView compound_rview;
    RVAdapter rvAdapter;
    List<Compound> list_compound;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                ViewModelProviders.of(this).get(FavoritesViewModel.class);
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        compound_rview = view.findViewById(R.id.fav_recent_recyclerview);
        loadCompound();
        rvAdapter = new RVAdapter(getActivity(), list_compound, ((MainActivity)getActivity()).getGlobal());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        compound_rview.setLayoutManager(layoutManager);
        compound_rview.setAdapter(rvAdapter);
        final ScrollView compoundView = view.findViewById(R.id.compound_scrollView);
        compoundView.setBackgroundColor(0xFFFFFFFF);
        compoundView.setVisibility(View.INVISIBLE);
        final Button favorites_button = view.findViewById(R.id.favorite);
        final Button recents_button = view.findViewById(R.id.recent);
        favorites_button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        recents_button.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        final TextView compoundView_name = view.findViewById(R.id.compoundView_name);
        final TextView compoundView_formula = view.findViewById(R.id.compoundView_formula);
        final ImageView compoundView_2dImage = view.findViewById(R.id.compoundView_2dImage);
        final ImageView compoundView_3dImage = view.findViewById(R.id.compoundView_3dImage);
        final ImageView compoundView_crystal = view.findViewById(R.id.compoundView_crystal);
        final Button compoundView_backButton = view.findViewById(R.id.closeButton);
        final LinearLayout SafetyItems_Images = view.findViewById(R.id.SafetyItems_Images);
        final LinearLayout SafetyItems_Text = view.findViewById(R.id.SafetyItems_Text);
        final ImageView[] HazardImages = new ImageView[9];
        final TextView[] HazardTexts = new TextView[9];
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
        final LinearLayout StructureImageLayout = view.findViewById(R.id.compoundView_images);
        final LinearLayout StructureTextLayout = view.findViewById(R.id.compoundView_images_names);
        final ImageView[] StructureImages = new ImageView[3];
        final TextView[] StructureTexts = new TextView[3];
        StructureImages[0] = view.findViewById(R.id.compoundView_2dImage);
        StructureTexts[0] = view.findViewById(R.id.compoundView_images_names_2d);
        StructureImages[1] = view.findViewById(R.id.compoundView_3dImage);
        StructureTexts[1] = view.findViewById(R.id.compoundView_images_names_3d);
        StructureImages[2] = view.findViewById(R.id.compoundView_crystal);
        StructureTexts[2] = view.findViewById(R.id.compoundView_images_names_crystal);
        compoundView_backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                compoundView.setVisibility(View.INVISIBLE);
                favorites_button.setVisibility(View.VISIBLE);
                recents_button.setVisibility(View.VISIBLE);
            }
        });

        favorites_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favorites_button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                recents_button.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        });

        recents_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favorites_button.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                recents_button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
        });

        rvAdapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
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
                                    JSONObject section_0 = (JSONObject) section.get(0);
                                    JSONArray structure_section = (JSONArray) section_0.get("Section");
                                    /*
                                    String RecordFormula = "";
                                    try {
                                        structure_section = (JSONArray) section_0.get("Section");
                                        JSONObject section_2 = (JSONObject) section.get(2);
                                        JSONArray sub_section_2 = (JSONArray) section_2.get("Section");
                                        JSONObject sub_section_2_2 = (JSONObject) sub_section_2.get(2);
                                        JSONArray Information = (JSONArray) sub_section_2_2.get("Information");
                                        JSONObject sub_Information = (JSONObject) Information.get(0);
                                        JSONObject Value = (JSONObject) sub_Information.get("Value");
                                        JSONArray StringWithMarkup = (JSONArray) Value.get("StringWithMarkup");
                                        JSONObject sub_StringWithMarkup = (JSONObject) StringWithMarkup.get(0);
                                        RecordFormula = (String) sub_StringWithMarkup.get("String");
                                    } catch (ArrayIndexOutOfBoundsException a) {
                                        section = (JSONArray) record.get("Section");
                                        JSONObject section_0 = (JSONObject) section.get(0);
                                        structure_section = (JSONArray) section_0.get("Section");
                                        JSONObject section_1 = (JSONObject) section.get(1);
                                        JSONArray sub_section_1 = (JSONArray) section_1.get("Section");
                                        JSONObject sub_section_1_1 = (JSONObject) sub_section_1.get(1);
                                        JSONArray Information = (JSONArray) sub_section_1_1.get("Information");
                                        JSONObject sub_Information = (JSONObject) Information.get(0);
                                        JSONObject Value = (JSONObject) sub_Information.get("Value");
                                        JSONArray StringWithMarkup = (JSONArray) Value.get("StringWithMarkup");
                                        JSONObject sub_StringWithMarkup = (JSONObject) StringWithMarkup.get(0);
                                        RecordFormula = (String) sub_StringWithMarkup.get("String");
                                    } catch (IndexOutOfBoundsException i) {
                                        section = (JSONArray) record.get("Section");
                                        JSONObject section_0 = (JSONObject) section.get(0);
                                        structure_section = (JSONArray) section_0.get("Section");
                                        JSONObject section_1 = (JSONObject) section.get(1);
                                        JSONArray sub_section_1 = (JSONArray) section_1.get("Section");
                                        JSONObject sub_section_1_1 = (JSONObject) sub_section_1.get(1);
                                        JSONArray Information = (JSONArray) sub_section_1_1.get("Information");
                                        JSONObject sub_Information = (JSONObject) Information.get(0);
                                        JSONObject Value = (JSONObject) sub_Information.get("Value");
                                        JSONArray StringWithMarkup = (JSONArray) Value.get("StringWithMarkup");
                                        JSONObject sub_StringWithMarkup = (JSONObject) StringWithMarkup.get(0);
                                        RecordFormula = (String) sub_StringWithMarkup.get("String");
                                    }
                                    list_compound.get(position).setFormula(RecordFormula);
                                    //Toast.makeText(getActivity(), "RecordFormula: " + RecordFormula, Toast.LENGTH_LONG).show();

                                     */
                                    StructureImageLayout.removeAllViews();
                                    StructureTextLayout.removeAllViews();
                                    try {
                                        for (int i = 0; i < 3; i++) {
                                            if (structure_section.size() > i) {
                                                JSONObject struct_1 = (JSONObject) structure_section.get(i);
                                                String struct_name = (String) struct_1.get("TOCHeading");
                                                if (struct_name.equals("2D Structure")) {
                                                    StructureImageLayout.addView(StructureImages[0]);
                                                    StructureTextLayout.addView(StructureTexts[0]);
                                                    AsyncTaskLoadImage image_Loader = new AsyncTaskLoadImage(compoundView_2dImage);
                                                    image_Loader.execute("https://pubchem.ncbi.nlm.nih.gov/image/imgsrv.fcgi?cid=" + list_compound.get(position).getEID() + "&t=s");
                                                }
                                                if (struct_name.equals("3D Conformer")) {
                                                    StructureImageLayout.addView(StructureImages[1]);
                                                    StructureTextLayout.addView(StructureTexts[1]);
                                                    AsyncTaskLoadImage image_Loader = new AsyncTaskLoadImage(compoundView_3dImage);
                                                    image_Loader.execute("https://pubchem.ncbi.nlm.nih.gov/image/img3d.cgi?cid=" + list_compound.get(position).getEID() + "&t=s");
                                                }
                                                if (struct_name.equals("Crystal Structures")) {
                                                    JSONArray temp = (JSONArray) struct_1.get("Section");
                                                    JSONObject temp_1 = (JSONObject) temp.get(1);
                                                    JSONArray temp_2 = (JSONArray) temp_1.get("Information");
                                                    JSONObject temp_3 = (JSONObject) temp_2.get(1);
                                                    JSONObject value = (JSONObject) temp_3.get("Value");
                                                    JSONArray contents = (JSONArray) value.get("ExternalDataURL");
                                                    String ExternalURLData = (String) contents.get(0);
                                                    StructureImageLayout.addView(StructureImages[2]);
                                                    StructureTextLayout.addView(StructureTexts[2]);
                                                    AsyncTaskLoadImage image_Loader = new AsyncTaskLoadImage(compoundView_crystal);
                                                    image_Loader.execute(ExternalURLData);
                                                }
                                            }
                                        }
                                    } catch (NullPointerException e) {
                                    } catch (IndexOutOfBoundsException i) {
                                    }
                                    SafetyItems_Images.removeAllViews();
                                    SafetyItems_Text.removeAllViews();
                                    try {
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
                                            safety[n-1] = true;
                                        }
                                        for (int i = 0; i < 9; i++) {
                                            if (safety[i]) {
                                                SafetyItems_Images.addView(HazardImages[i]);
                                                SafetyItems_Text.addView(HazardTexts[i]);
                                            }
                                        }
                                    } catch (NullPointerException e) {
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                } catch (IndexOutOfBoundsException e) {
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
                favorites_button.setVisibility(View.INVISIBLE);
                recents_button.setVisibility(View.INVISIBLE);
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
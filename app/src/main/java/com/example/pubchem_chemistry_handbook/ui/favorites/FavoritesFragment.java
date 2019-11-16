package com.example.pubchem_chemistry_handbook.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.pubchem_chemistry_handbook.ui.AsyncTaskLoadImage;
import com.example.pubchem_chemistry_handbook.ui.RVAdapter;
import com.example.pubchem_chemistry_handbook.ui.search.CompFragment;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    RecyclerView compound_rview;
    RVAdapter rvAdapter;
    List<Compound> currentList;
    int current_pos = 0;
    Compound current_Compound = null;
    int current_state = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()){
            ((MainActivity) getActivity()).clearKeyboard();}

        currentList = new ArrayList<Compound>(((MainActivity)getActivity()).getGlobal().getFav());
        current_state = 1;
        //favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        compound_rview = view.findViewById(R.id.fav_recent_recyclerview);
        rvAdapter = new RVAdapter(getActivity(), currentList, ((MainActivity)getActivity()).getGlobal());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        compound_rview.setLayoutManager(layoutManager);
        compound_rview.setAdapter(rvAdapter);
        final Button favorites_button = view.findViewById(R.id.favorite);
        final Button recents_button = view.findViewById(R.id.recent);
        favorites_button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        recents_button.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
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
        final ImageView[] StructureImages = new ImageView[3];
        final TextView[] StructureTexts = new TextView[3];
        StructureImages[0] = view.findViewById(R.id.compoundView_2dImage);
        StructureTexts[0] = view.findViewById(R.id.compoundView_images_names_2d);
        StructureImages[1] = view.findViewById(R.id.compoundView_3dImage);
        StructureTexts[1] = view.findViewById(R.id.compoundView_images_names_3d);
        StructureImages[2] = view.findViewById(R.id.compoundView_crystal);
        StructureTexts[2] = view.findViewById(R.id.compoundView_images_names_crystal);

        rvAdapter.notifyDataSetChanged();

        favorites_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favorites_button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                recents_button.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                currentList.clear();
                currentList.addAll(((MainActivity)getActivity()).getGlobal().getFav());
                rvAdapter.notifyDataSetChanged();
                current_state = 1;
            }
        });

        recents_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favorites_button.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                recents_button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                currentList.clear();
                currentList.addAll(((MainActivity)getActivity()).getGlobal().getRecents());
                rvAdapter.notifyDataSetChanged();
                current_state = 2;
            }
        });

        rvAdapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(final int position) {
                ((MainActivity)getActivity()).setCompViewInfo(rvAdapter.CompoundList.get(position),position);
                Fragment fragment= new CompFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.favorites_frag, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}
package com.example.pubchem_chemistry_handbook.ui.pTable;

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
import android.widget.Button;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.pubchem_chemistry_handbook.ui.AsyncTaskLoadImage;
import com.example.pubchem_chemistry_handbook.ui.RVAdapter;
import com.example.pubchem_chemistry_handbook.ui.news.RSS_Adapter;
import com.example.pubchem_chemistry_handbook.ui.pTable_Adapter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class pTableFragment extends Fragment {

    private pTableViewModel pTableViewModel;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    pTable_Adapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        pTableViewModel = ViewModelProviders.of(this).get(pTableViewModel.class);
        View view = inflater.inflate(R.layout.fragment_ptable, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        recyclerView = view.findViewById(R.id.pTable_Recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 18);
        recyclerView.setLayoutManager(layoutManager);
        System.out.println(((MainActivity)getActivity()).getGlobal().getElements());
        mAdapter = new pTable_Adapter(((MainActivity)getActivity()).getGlobal().getElements());
        recyclerView.setAdapter(mAdapter);
        return view;
    }
}
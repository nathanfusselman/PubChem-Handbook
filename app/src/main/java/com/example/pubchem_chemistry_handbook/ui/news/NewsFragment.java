package com.example.pubchem_chemistry_handbook.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Event;

import java.util.Arrays;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        final RecyclerView RSS_Feed = (RecyclerView) root.findViewById(R.id.RSS_recycler_view);
        final TextView textDate = root.findViewById(R.id.news_date);
        final TextView textDay = root.findViewById(R.id.news_day);

        RSS_Feed.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        List<Event> events = Arrays.asList( new Event("Title", "Desc", "Link"),
                new Event("Title1", "Desc1", "Link1"),
                new Event("Title2", "Desc2", "Link2"),
                new Event("Title3", "Desc3", "Link3"),
                new Event("Title4", "Desc4", "Link4"),
                new Event("Title5", "Desc5", "Link5"));
        RSS_Adapter mAdapter = new RSS_Adapter(events);
        RSS_Feed.setAdapter(mAdapter);
        RSS_Feed.setItemAnimator(new DefaultItemAnimator());
        
        
        

        newsViewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textDate.setText(s);
            }
        });

        newsViewModel.getDay().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textDay.setText(s);
            }
        });


        return root;
    }


}
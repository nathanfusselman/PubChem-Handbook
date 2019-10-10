package com.example.pubchem_chemistry_handbook.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Event;

import java.util.List;

public class RSS_Adapter extends RecyclerView.Adapter<RSS_Adapter.RSSViewHolder>{
    private List<Event> eventSet;

    public static class RSSViewHolder extends RecyclerView.ViewHolder{
        public View RSSView;

        public RSSViewHolder(View v) {
            super(v);
            RSSView = v;
        }
}

    public RSS_Adapter(List<Event> events) {
        eventSet = events;
    }

    @Override
    public RSS_Adapter.RSSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_feed, parent, false);
        RSSViewHolder vh = new RSSViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RSSViewHolder holder, int position) {
        final Event rssModel = eventSet.get(position);
        ((TextView)holder.RSSView.findViewById(R.id.titleText)).setText(rssModel.getTitle());
        ((TextView)holder.RSSView.findViewById(R.id.descriptionText)).setText(rssModel.getDescription());
        ((TextView)holder.RSSView.findViewById(R.id.linkText)).setText(rssModel.getLink());

    }

    @Override
    public int getItemCount() {
        return eventSet.size();
    }
}


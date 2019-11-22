package com.example.pubchem_chemistry_handbook.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RSS_Adapter extends RecyclerView.Adapter<RSS_Adapter.RSSViewHolder>{
    private List<Event> eventSet;

    static class RSSViewHolder extends RecyclerView.ViewHolder{
        View RSSView;
        RSSViewHolder(View v) {
            super(v);
            RSSView = v;
        }
}

     RSS_Adapter(List<Event> events) {
        eventSet = events;
    }

    @NonNull
    @Override
    public RSS_Adapter.RSSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_feed, parent, false);
        return new RSSViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RSSViewHolder holder, int position) {
        final Event rssModel = eventSet.get(position);

        String fixedDate= null;
        try {
            fixedDate = fixDate(rssModel.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ((TextView)holder.RSSView.findViewById(R.id.titleText)).setText(rssModel.getTitle());
        ((TextView)holder.RSSView.findViewById(R.id.pubDate)).setText(fixedDate);
        ((TextView)holder.RSSView.findViewById(R.id.descriptionText)).setText(rssModel.getDescription());
        ((TextView)holder.RSSView.findViewById(R.id.linkText)).setText(rssModel.getLink());

    }

    @Override
    public int getItemCount() {
        return eventSet.size();
    }

    @SuppressWarnings("ConstantConditions")
    private String fixDate( String rssDateStr) throws ParseException {
        DateFormat format= new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        Date date;
        date = new Date(format.parse(rssDateStr).getTime());
        DateFormat prefer = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm a z", Locale.ENGLISH);
        return prefer.format(date);
    }
}


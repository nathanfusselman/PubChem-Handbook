package com.example.pubchem_chemistry_handbook.ui.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    static List<Event> events = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        final TextView textDate = root.findViewById(R.id.news_date);
        final TextView textDay = root.findViewById(R.id.news_day);

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

        new getXML(root).execute("https://rss.sciencedaily.com/matter_energy/chemistry.xml");

        return root;


    }

    //////
//////
//////
    static final String PUB_DATE = "pubDate";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LINK = "link";
    static final String TITLE = "title";
    static final String ITEM = "item";

    static private class getXML extends AsyncTask<String, String, List<Event>> {
        View view;
        public getXML(View view){
            this.view=view;
        }
        @Override
        protected List<Event> doInBackground(String... strings) {
            String rssFeed = strings[0];
            List<Event> list = new ArrayList<Event>();
            XmlPullParser parser = Xml.newPullParser();
            InputStream stream = null;
            try {
                // auto-detect the encoding from the stream
                stream = new URL(rssFeed).openConnection().getInputStream();
                parser.setInput(stream, null);
                int eventType = parser.getEventType();
                boolean done = false;
                Event item = null;
                while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                    String name = null;
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if (name.equalsIgnoreCase(ITEM)) {
                                Log.i("new item", "Create new item");
                                item = new Event();
                            } else if (item != null) {
                                if (name.equalsIgnoreCase(LINK)) {
                                    Log.i("Attribute", "setLink");
                                    item.setLink(parser.nextText());
                                } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                                    Log.i("Attribute", "description");
                                    item.setDescription(parser.nextText().trim());
                                } else if (name.equalsIgnoreCase(PUB_DATE)) {
                                    Log.i("Attribute", "date");
                                    item.setDate(parser.nextText());
                                } else if (name.equalsIgnoreCase(TITLE)) {
                                    Log.i("Attribute", "title");
                                    item.setTitle(parser.nextText().trim());
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            name = parser.getName();
                            Log.i("End tag", name);
                            if (name.equalsIgnoreCase(ITEM) && item != null) {
                                Log.i("Added", item.toString());
                                list.add(item);
                            } else if (name.equalsIgnoreCase(CHANNEL)) {
                                done = true;
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Event> res) {
            events=res;
            RecyclerView RSS_Feed = view.findViewById(R.id.RSS_recycler_view);
            RSS_Feed.setLayoutManager(new LinearLayoutManager(view.getContext()));
            RSS_Adapter mAdapter = new RSS_Adapter(events);
            RSS_Feed.setAdapter(mAdapter);
            RSS_Feed.setItemAnimator(new DefaultItemAnimator());
        }
    }
}
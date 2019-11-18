package com.example.pubchem_chemistry_handbook.ui.news;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Event;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if(getActivity()!=null){
            InputMethodManager imm = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isAcceptingText()){
                ((MainActivity) getActivity()).clearKeyboard();}}

        NewsViewModel newsViewModel;
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        if(getActivity() != null) {getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);}

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
        WeakReference<View> weakView;
        weakView = new WeakReference<>(root);
        new getXML(weakView).execute("https://rss.sciencedaily.com/matter_energy/chemistry.xml");
        mSwipeRefreshLayout = root.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return root;


    }
    @Override
    public void onRefresh() {
        WeakReference<View> weakView;
        weakView = new WeakReference<>(mSwipeRefreshLayout.getRootView());
        new getXML(weakView).execute("https://rss.sciencedaily.com/matter_energy/chemistry.xml");
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

//section to parse the rss feed and update recycler view
    private static final String PUB_DATE = "pubDate";
    private static final String DESCRIPTION = "description";
    private static final String CHANNEL = "channel";
    private static final String LINK = "link";
    private static final String TITLE = "title";
    private static final String ITEM = "item";


    //parse it
    static private class getXML extends AsyncTask<String, String, List<Event>> {
        WeakReference<View> view;
        getXML(WeakReference<View> view){
            this.view=view;
        }
        @Override
        protected List<Event> doInBackground(String... strings) {
            String rssFeed = strings[0];
            List<Event> list = new ArrayList<>();
            XmlPullParser parser = Xml.newPullParser();
            InputStream stream = null;
            try {
                stream = new URL(rssFeed).openConnection().getInputStream();
                parser.setInput(stream, null);
                int eventType = parser.getEventType();
                boolean done = false;
                Event item = null;
                while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                    String name;
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if (name.equalsIgnoreCase(ITEM)) {
                                item = new Event();
                            } else if (item != null) {
                                if (name.equalsIgnoreCase(LINK)) {
                                    item.setLink(parser.nextText());
                                } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                                    item.setDescription(parser.nextText().trim());
                                } else if (name.equalsIgnoreCase(PUB_DATE)) {
                                    item.setDate(parser.nextText());
                                } else if (name.equalsIgnoreCase(TITLE)) {
                                    item.setTitle(parser.nextText().trim());
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            name = parser.getName();
                            if (name.equalsIgnoreCase(ITEM) && item != null) {
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
//update recycler view
        @Override
        protected void onPostExecute(List<Event> res) {
            RecyclerView RSS_Feed = view.get().findViewById(R.id.RSS_recycler_view);
            RSS_Feed.setLayoutManager(new LinearLayoutManager(view.get().getContext()));
            RSS_Adapter rssAdapter = new RSS_Adapter(res);
            RSS_Feed.setAdapter(rssAdapter);
            RSS_Feed.setItemAnimator(new DefaultItemAnimator());

            String eventsLoaded = (rssAdapter.getItemCount())+" events loaded.";
            ((TextView)view.get().findViewById(R.id.itemsLoaded)).setText(eventsLoaded);
        }
    }
}
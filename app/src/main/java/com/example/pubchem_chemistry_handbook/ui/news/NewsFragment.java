package com.example.pubchem_chemistry_handbook.ui.news;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String fullPath=null;
    private TextView loadingText;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final File directory = getContext().getExternalFilesDir(getActivity().getApplication().getFilesDir().toString());
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        fullPath= directory.toString() + "/RSSFeed_" + date + ".xml";
        File toCheck = new File(fullPath);

        if(!toCheck.exists())
        {
            deleteFiles(directory.toString()); //cleans up old RSSFeeds
        }

        if(getActivity()!=null){
            InputMethodManager imm = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && imm.isAcceptingText()) {
                ((MainActivity) getActivity()).clearKeyboard();
            }
        }

        NewsViewModel newsViewModel;
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        loadingText = root.findViewById(R.id.loadingText);
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
        WeakReference<TextView> weakTextView = new WeakReference<>(loadingText);
        new getXML(weakView, weakTextView).execute("https://rss.sciencedaily.com/matter_energy/chemistry.xml",fullPath);
        mSwipeRefreshLayout = root.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return root;
    }
    @Override
    public void onRefresh() {
        WeakReference<View> weakView;
        weakView = new WeakReference<>(mSwipeRefreshLayout.getRootView());
        WeakReference<TextView> weakTextView = new WeakReference<>(loadingText);
        new getXML(weakView, weakTextView).execute("https://rss.sciencedaily.com/matter_energy/chemistry.xml",fullPath);
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
        WeakReference<TextView> loadingViewNotif;
        getXML(WeakReference<View> view, WeakReference<TextView> textView){
            this.view=view;
            this.loadingViewNotif=textView;
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected List<Event> doInBackground(String... strings) {
            String rssFeed = strings[0];
            File localFeed = null;
            try {
                localFeed = download_XML(rssFeed, strings[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Event> list = new ArrayList<>();
            XmlPullParser parser = Xml.newPullParser();
            InputStream stream = null;
            try {
                try {
                    assert localFeed != null;
                    stream = new FileInputStream(localFeed);
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
            }catch (Exception e){
                e.printStackTrace(); }
            return list;
        }
//update recycler view
        @Override
        protected void onPostExecute(List<Event> res) {
            loadingViewNotif.get().setVisibility(View.INVISIBLE);
            RecyclerView RSS_Feed = view.get().findViewById(R.id.RSS_recycler_view);
            RSS_Feed.setLayoutManager(new LinearLayoutManager(view.get().getContext()));
            RSS_Adapter rssAdapter = new RSS_Adapter(res);
            RSS_Feed.setAdapter(rssAdapter);
            RSS_Feed.setItemAnimator(new DefaultItemAnimator());

            String eventsLoaded = (rssAdapter.getItemCount())+" events loaded.";
            if(rssAdapter.getItemCount()==0)
            {
                loadingViewNotif.get().setText("No internet connection or other connection issue.");
                loadingViewNotif.get().setVisibility(View.VISIBLE);
            }
            ((TextView)view.get().findViewById(R.id.itemsLoaded)).setText(eventsLoaded);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private File download_XML(String urlString, String pathToSave) throws IOException {
            File file = new File(pathToSave);
            if(!file.exists()) {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                Log.v("Start Query", "Stream");
                conn.connect();
                Log.v("End Query", "Stream");
                //read the result from the server
                BufferedReader rdr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sbr = new StringBuilder();
                String line;
                while ((line = rdr.readLine()) != null) {
                    sbr.append(line).append('\n');
                }

                //Log.v(sbr.toString(), "Stream");
                try {
                    try (FileOutputStream stream = new FileOutputStream(file)) {
                        stream.write(sbr.toString().getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }
    }
    private void deleteFiles(String folder)
    {
        File dir = new File(folder);
        if (!dir.exists())
            return;
        File[] files = dir.listFiles(new GenericExtFilter(".xml"));
        assert files != null;
        for (File file : files)
        {
            if (!file.isDirectory())
            {
                boolean result = file.delete();
                Log.d("TAG", "Deleted:" + result);
            }
        }
    }

    class GenericExtFilter implements FilenameFilter {

        private String ext;

        private GenericExtFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(ext));
        }
    }
}
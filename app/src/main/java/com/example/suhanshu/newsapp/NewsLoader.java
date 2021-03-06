package com.example.suhanshu.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;
    public NewsLoader(Context context, String mUrl) {
        super(context);
        this.mUrl=mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<News> news = QueryUtils.fetchEarthquakeData(mUrl);
        return news;

    }
}

package com.example.suhanshu.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suhanshu.newsapp.AdapterClass.NewsAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final int CONSTANT = 1;
    private ListView listView;
    private ProgressBar progressBar;
    private TextView textView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        progressBar = findViewById(R.id.loading_indicator);
        textView = findViewById(R.id.empty_view);
        listView.setEmptyView(textView);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(CONSTANT, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            progressBar.setVisibility(View.GONE);
            textView.setText(R.string.no_internet_connection);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsAdapter.getItem(position);
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getWebUrl()));
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String search = sharedPrefs.getString(
                getString(R.string.search_key),
                getString(R.string.debates_default_value));

        String orderBy = sharedPrefs.getString(
                getString(R.string.orderkey),
                getString(R.string.relevance_value)
        );
        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(BuildConfig.THE_API_TOKEN);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `format=geojson`
        uriBuilder.appendQueryParameter(getString(R.string.format), getString(R.string.json));
        uriBuilder.appendQueryParameter(getString(R.string.showtags), getString(R.string.contributor));
        uriBuilder.appendQueryParameter(getString(R.string.orderbys), orderBy);
        uriBuilder.appendQueryParameter(getString(R.string.q), search);
        uriBuilder.appendQueryParameter(getString(R.string.api), getString(R.string.apikey));
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        progressBar.setVisibility(View.INVISIBLE);
        textView.setText(R.string.no_more);
        newsAdapter = new NewsAdapter(this, data);
        listView.setAdapter(newsAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

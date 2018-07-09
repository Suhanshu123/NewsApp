package com.example.suhanshu.newsapp.AdapterClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suhanshu.newsapp.News;
import com.example.suhanshu.newsapp.R;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_layout, parent, false);
        }
        News news = getItem(position);
        TextView sectionName, webTitle, publicationDate,publicationTime,authorName;
        sectionName = view.findViewById(R.id.section_Name);
        authorName=view.findViewById(R.id.author_Name);
        webTitle = view.findViewById(R.id.heading);
        publicationDate = view.findViewById(R.id.publication_Date);
        publicationTime=view.findViewById(R.id.publication_time);
        String fullDate=news.getPublicationDate();
        String author=news.getAuthor();
        authorName.setText(author);
        int index=fullDate.indexOf('T');
        int index2=fullDate.indexOf('Z');
        String date=fullDate.substring(0,index);
        String time=fullDate.substring(index+1,index2);
        sectionName.setText(news.getSectionName());
        publicationDate.setText(date);
        publicationTime.setText(time);
        webTitle.setText(news.getWebTitle());
        return view;

    }
}

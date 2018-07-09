package com.example.suhanshu.newsapp;

//  Model Class
public class News {
    private String sectionName,publicationDate,webTitle,webUrl,author;

    public News(String sectionName, String publicationDate, String webTitle, String webUrl, String author) {
        this.sectionName = sectionName;
        this.publicationDate = publicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.author = author;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getAuthor() {
        return author;
    }
}
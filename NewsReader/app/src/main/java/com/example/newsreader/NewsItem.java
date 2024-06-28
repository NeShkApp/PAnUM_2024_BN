package com.example.newsreader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsItem {
    private String title;
    private String description;
    private String link;
    private Date date;
    private String imageUrl;

    public NewsItem(String title, String description, String link, String dateString, String imageUrl) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.imageUrl = imageUrl;
        this.date = parseDate(dateString);
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public Date getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

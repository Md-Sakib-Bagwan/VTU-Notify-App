package com.adityamshidlyali.vtunotify.model;

public class NotificationItem {
    public String date;
    public String monthYear;
    public String text;
    public String link;

    public NotificationItem(String date, String monthYear, String text, String link) {
        this.date = date;
        this.monthYear = monthYear;
        this.text = text;
        this.link = link;
    }
}
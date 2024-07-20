package com.adityamshidlyali.vtunotify.scraper;

import com.adityamshidlyali.vtunotify.model.NotificationItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VtuScraper {

    private ArrayList<NotificationItem> notificationList = new ArrayList<>();

    public List<NotificationItem> scrapeNotifications(String responseString) {

        String date = "";
        String monthYear = "";
        String text = "";
        String link = "";

        Document document = Jsoup.parse(responseString);

        Elements notificationArticles = document.getElementsByTag("article");

        for (Element articles :
                notificationArticles) {

            // get the article meta date
            Elements articleDays = articles.getElementsByClass("entry-day");
            for (Element articleDay : articleDays) {
                date = articleDay.text().toString() + " ";
            }

            // get the article meta month
            Elements articleMonths = articles.getElementsByClass("entry-month");
            for (Element articleMonth : articleMonths) {
                monthYear = articleMonth.text().toString();
            }

            // get the article text with text
            Elements notificationTextWithLinks = articles.getElementsByClass("entry-title");
            for (Element notificationText : notificationTextWithLinks) {
                text = notificationText.text();
            }

            // get the article links
            Elements articleLinks = articles.getElementsByClass("readmore");
            for (Element articleLink : articleLinks) {
                link = Objects.requireNonNull(articleLink.select("a").first()).attr("href").toString();
            }

            notificationList.add(new NotificationItem(date, monthYear, text, link));
        }

        return notificationList;
    }
}

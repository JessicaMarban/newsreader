package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.beans.Article;

import java.io.*;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Controller {

    public static final String APIKEY = "4e65418020314562b922f4f33fcd2cd9";  //TODO add your api key

    public void process(NewsApi newsApi) {
        System.out.println("Start process");

        NewsResponse newsResponse = null;
        List<Article> articles = null;

        //TODO implement Error handling
        try {
            newsResponse = newsApi.getNews();
            articles = newsResponse.getArticles();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //TODO load the news based on the parameters
        if (newsResponse != null && !articles.isEmpty()) {
            articles = newsResponse.getArticles();
            articles.stream().forEach(article -> System.out.println(article.toString()));


            //TODO implement methods for analysis - task 5

            //5a
            System.out.println("\nNumber of articles: " + articles.size() + "\n");


            //articles.stream().forEach(article -> System.out.println(article.getSource().getName()));

            //5.b get the name of provider and the number of articles
            String provider = articles.stream()
                    .collect(Collectors.groupingBy(article -> article.getSource().getName(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingInt(t -> t.getValue().intValue())).get().getKey();
            //max compares values and then gets the name aka the key

            System.out.println("\n \n The provider with the most articles is " + provider + "\n \n");


            //5.c
            List shortestName = articles.stream()
                    .map(Article::getAuthor)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(String::length))
                    .collect(Collectors.toList());

            System.out.println("\n The shortest author name is " + shortestName.get(0) + "\n");

            //5d sort the list after the longest name alphabetically
            System.out.println("SORTED LIST\n");
            List<Article> result = articles.stream().sorted((object1, object2) -> object1.getTitle().compareTo(object2.getTitle())).collect(Collectors.toList());
            result.stream().forEach(article -> System.out.println("\n" + article.toString() + "\n"));

            if (result != null) {
                System.out.println(result.get(0));
            }

            // extension: download all articles
            for (Article downloadedArticle : articles) {
                try {
                    URL url = new URL(downloadedArticle.getUrl());
                    InputStream input = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    //writer creates a new html file - substring is the number of characters that are gonna be the name of the file
                    BufferedWriter writer = new BufferedWriter(new FileWriter(downloadedArticle.getTitle().substring(0, 5) + ".html"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                    }
                    reader.close();
                    writer.close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } else {
            System.err.println("No news for you, go home");
        }

        System.out.println("End process");
    }


    public Object getData() {

        return null;
    }
}

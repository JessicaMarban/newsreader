package at.ac.fhcampuswien.newsanalyzer.ui;


import at.ac.fhcampuswien.newsanalyzer.ctrl.Controller;
import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static at.ac.fhcampuswien.newsanalyzer.ctrl.Controller.APIKEY;

public class UserInterface {
    private Controller ctrl = new Controller();

    public void getDataFromCtrl1() {

        NewsApi newsApi = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ("Fußball")
                .setEndPoint(Endpoint.TOP_HEADLINES)
                .setSourceCountry(Country.at)
                .setSourceCategory(Category.sports)
                .createNewsApi();

        ctrl.process(newsApi);
    }

    public void getDataFromCtrl2() {
        // TODO implement me

        NewsApi newsApi = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ("Corona")
                .setEndPoint(Endpoint.TOP_HEADLINES)
                .setSourceCountry(Country.at)
                .setSourceCategory(Category.health)
                .createNewsApi();

        ctrl.process(newsApi);

    }

    public void getDataFromCtrl3() {
        // TODO implement me

        NewsApi newsApi = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ("Queen")
                .setEndPoint(Endpoint.TOP_HEADLINES)
                .setSourceCountry(Country.at)
                .setSourceCategory(Category.entertainment)
                .createNewsApi();

        ctrl.process(newsApi);
    }


    public void getDataForCustomInput() {
        // TODO implement me

        Scanner scan = new Scanner(System.in);

        //all categories are displayed and the user has to choose one
        int number = 1;
        System.out.println("Please choose a category.");
        for (Category cat : Category.values()) {
            System.out.println(cat);
        }
        System.out.println("Please type your chosen category.");
        String chosenCategory = scan.next();


        System.out.println("What do you want to know more about?");
        String chosenTopic = scan.next();


        for (Category cat : Category.values()) {

            if(chosenCategory.equals(cat.toString())) {
                NewsApi newsApi = new NewsApiBuilder()
                        .setApiKey(APIKEY)
                        .setQ(chosenTopic)
                        .setEndPoint(Endpoint.TOP_HEADLINES)
                        .setSourceCountry(Country.at)
                        .setSourceCategory(Category.valueOf(chosenCategory))
                        .createNewsApi();

                ctrl.process(newsApi);
                break; //we don't need the loop, if we found our category
            }
        }
    }

        public void start () {
            Menu<Runnable> menu = new Menu<>("User Interface");
            menu.setTitle("Wählen Sie aus:");
            menu.insert("1", "Choice EM", this::getDataFromCtrl1);
            menu.insert("2", "Choice COVID", this::getDataFromCtrl2);
            menu.insert("3", "Choice Tech", this::getDataFromCtrl3);
            menu.insert("4", "Choice User Input:", this::getDataForCustomInput);
            menu.insert("q", "Quit", null);
            Runnable choice;
            while ((choice = menu.exec()) != null) {
                choice.run();
            }
            System.out.println("Program finished");
        }


        protected String readLine () {
            String value = "\0";
            BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                value = inReader.readLine();
            } catch (IOException ignored) {
            }
            return value.trim();
        }

        protected Double readDouble ( int lowerlimit, int upperlimit){
            Double number = null;
            while (number == null) {
                String str = this.readLine();
                try {
                    number = Double.parseDouble(str);
                } catch (NumberFormatException e) {
                    number = null;
                    System.out.println("Please enter a valid number:");
                    continue;
                }
                if (number < lowerlimit) {
                    System.out.println("Please enter a higher number:");
                    number = null;
                } else if (number > upperlimit) {
                    System.out.println("Please enter a lower number:");
                    number = null;
                }
            }
            return number;
        }
    }

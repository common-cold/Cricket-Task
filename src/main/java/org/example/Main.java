package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        try {
            URL url = new URL("https://api.cuvora.com/car/partner/cricket-data");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();






        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
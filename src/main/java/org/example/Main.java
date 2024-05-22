package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        try {
            URL url = new URL("https://api.cuvora.com/car/partner/cricket-data");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("apiKey", "test-creds@2320");

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer result = new StringBuffer();
            while((line = input.readLine()) != null){
                result.append(line);
            }

            input.close();

            Jso

            System.out.println(result);







        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
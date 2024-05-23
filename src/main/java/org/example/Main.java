package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Main {

    public static String executeURL(String inputURL){
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(inputURL)
                .addHeader("Content-Type", "application/json")
                .addHeader("apiKey","test-creds@2320")
                .build();
        try {
            Response response = httpClient.newCall(request).execute();

            //Check if the response is valid
            if(response.code() != 200)
                throw new RuntimeException(response.message());

            return response.body().string();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static int scoreConverter(String score){
        return Integer.valueOf(score.split("/")[0]);
    }

    public static void main(String[] args) throws JsonProcessingException {
        int maxScore = -1;
        String teamName = null;
        int count = 0;
        String url = "https://api.cuvora.com/car/partner/cricket-data";
        String response = executeURL(url);

        ObjectMapper objectMapper = new ObjectMapper();
        Data data = objectMapper.readValue(response, Data.class);
        ArrayList<Details> result = data.getData();

        for(int i=0; i< result.size(); i++){
            Details res = result.get(i);

            String score1 = res.getT1s();
            String score2 = res.getT2s();
            String team1 = res.getT1();
            String team2 = res.getT2();

            //Validate scores from the response
            if(Objects.nonNull(score1) && !"".equalsIgnoreCase(score1)
                    && Objects.nonNull(score2) && !"".equalsIgnoreCase(score2)) {

                int s1 = scoreConverter(score1);
                int s2 = scoreConverter(score2);
                int maxi = Integer.max(s1, s2);
                if(maxi > maxScore){
                    maxScore = maxi;

                   //Validate team names from response
                    if(s1 >= s2)
                        teamName = (Objects.nonNull(team1) && !"".equalsIgnoreCase(team1)) ? team1 : " ";
                    else
                        teamName = (Objects.nonNull(team2) && !"".equalsIgnoreCase(team2)) ? team2 : " ";
                }
                if(s1+s2 >= 300)
                    count++;
            }
        }
        System.out.println("Highest Score : " + maxScore + " and Team Name is : " + teamName);
        System.out.println("Number Of Matches with total 300 Plus Score : " + count);

    }
}
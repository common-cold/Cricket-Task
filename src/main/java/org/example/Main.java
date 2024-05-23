package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


public class Main {
    public static JSONObject executeURL(String inputURL){
        try{
            URL url=new URL(inputURL);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("apiKey","test-creds@2320");

            BufferedReader input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer result=new StringBuffer();
            while((line=input.readLine())!=null){
                result.append(line);
            }

            input.close();
            connection.disconnect();

            JSONObject jsonObject=new JSONObject(result.toString());

            return jsonObject;

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public static int scoreConverter(String score){
        return Integer.valueOf(score.split("/")[0]);
    }

    public static void main(String[] args) {
        int maxScore = -1;
        String teamName = null;
        int count = 0;
        String url = "https://api.cuvora.com/car/partner/cricket-data";
        JSONArray response = executeURL(url).getJSONArray("data");

        for(int i=0; i< response.length(); i++){
            JSONObject res = (JSONObject) response.get(i);
            int s1 = scoreConverter(res.get("t1s").toString());
            int s2 = scoreConverter(res.get("t2s").toString());
            int maxi = Integer.max(s1, s2);
            if(maxi > maxScore){
                maxScore = maxi;
                if(s1 >= s2){
                    teamName = res.get("t1").toString();
                }
                else{
                    teamName = res.get("t2").toString();
                }
            }
            if(s1+s2 >= 300)
                count++;
        }

        System.out.println("Highest Score : " + maxScore + " and Team Name is : " + teamName);
        System.out.println("Number Of Matches with total 300 Plus Score : " + count);

    }
}
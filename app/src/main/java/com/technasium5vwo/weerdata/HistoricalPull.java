package com.technasium5vwo.weerdata;


import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HistoricalPull {
    private static final String OPEN_WEATHER_MAP_API_HISTORY =
            "http://api.openweathermap.org/data/2.5/history/city?q={city ID},{country code}&type=hour&start={start}&end={end}";
    public static JSONObject getJSON(Context context, String city, String date){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API_HISTORY, city, date));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuilder json = new StringBuilder(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }
}

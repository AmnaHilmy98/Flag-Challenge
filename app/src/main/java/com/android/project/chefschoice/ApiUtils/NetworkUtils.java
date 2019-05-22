package com.android.project.chefschoice.ApiUtils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String RECIPE_SEARCH_URL = "https://www.food2fork.com/api/search";
    private static final String KEY = "key";
    private static final String API_KEY = "893f9ca2a18b1e1c63165ec6d3dd5229";
    private static final String QUERY_PARAM = "q";
    private static final String PAGE = "page";


    public static String getRecipeInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String responseString = "";

        try {
            Uri builtURI = Uri.parse(RECIPE_SEARCH_URL).buildUpon()
                    .appendQueryParameter(KEY, API_KEY)
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(PAGE, "1")
                    .build();
            Log.d("TEST URI", builtURI.toString());

            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int response = urlConnection.getResponseCode();

            Log.d(LOG_TAG, String.valueOf(response));

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            Log.d("BUILDER LENGTH", String.valueOf(builder.length()));

            responseString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, responseString);
        return responseString;
    }
}

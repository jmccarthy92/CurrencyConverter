package com.example.android.currencyconverter;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


//import com.neovisionaries.i18n.

/**
 * Created by globe_000 on 9/14/2017.
 */

//public class Network extends AsyncTask<String,Void,String> {
//
//    private static String API_URL = "http://api.fixer.io/latest";
//
//    private static String PARAM_QUERY = "base";
//
//    protected String doInBackground(String... params) {
//        URL builtURL = buildURL(params[0]);
//        String response = "";
//        try {
//            response = getResponseFromHttpUrl(builtURL);
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    protected void onPostExecute(String response){
//
//
//    }
//
//    public static URL buildURL(String isoCountryCode){
//        Uri builtUri = Uri.parse(API_URL).buildUpon()
//                .appendQueryParameter(PARAM_QUERY, isoCountryCode)
//                .build();
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        } catch ( MalformedURLException e){
//            e.printStackTrace();
//        }
//        return url;
//    }
//
//    public static String getResponseFromHttpUrl(URL url) throws IOException {
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try{
//            InputStream in = urlConnection.getInputStream();
//            Scanner scanner = new Scanner(in);
//            boolean hasInput = scanner.hasNext();
//            if(hasInput)
//                return scanner.next();
//            else
//                return null;
//        } finally {
//            urlConnection.disconnect();
//        }
//    }
//
//
//
//
//}

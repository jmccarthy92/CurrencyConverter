package com.example.android.currencyconverter;


import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Currency;
import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.CurrencyCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static CountryCode cc ;
    private CurrencyClass currency1 ;
    private CurrencyClass currency2 ;
    private Spinner countrySpinner1;
    private Spinner countrySpinner2;
    private static String [] countryCodes = Locale.getISOCountries();
    private String [] iso3Codes;
    private EditText input1;
    private TextView result;
    private TextView errorMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iso3Codes = convertToIso3();
        input1 = (EditText) findViewById(R.id.editText2);
        result  = (TextView) findViewById(R.id.textView4);
        errorMess = (TextView) findViewById(R.id.textView);
        countrySpinner1 = (Spinner) findViewById(R.id.spinner);
        countrySpinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, iso3Codes);
        countrySpinner1.setAdapter(adapter);
        countrySpinner2.setAdapter(adapter);

        final Button submitButton = (Button) findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                result.setText("");
                if( countrySpinner1.getSelectedItem().toString() == countrySpinner2.getSelectedItem().toString())
                {
                    errorMess.setText("Converting Currency of Same Type is Redundant Try again");
                } else {
                    errorMess.setText("");
                    new Network().execute(countrySpinner2.getSelectedItem().toString(), countrySpinner1.getSelectedItem().toString());
                }
                //Double currencyAmt = new Double(currency1.calculateConversion());
                //result.append(currencyAmt.toString());
            }
        });
    }

    private String [] convertToIso3(){

        String [] countryCodeArr ={ "JPY", "CNY",  "RON", "MXN", "CAD",
                "ZAR", "AUD", "NOK", "ILS", "THB", "IDR", "HRK",
                "DKK", "MYR", "SEK", "RSD", "BGN",  "KRW",
                "CZK", "VND", "NZD",
                "GBP", "CHF", "RUB", "INR",
                 "TRY", "SGD", "HKD",
                "BRL", "EUR", "HUF",   "PHP", "PLN",
                "USD" };
        return countryCodeArr;
    }



    private class Network extends AsyncTask<String,Void,String> {

        private  String API_URL = "http://api.fixer.io/latest";

        private  String PARAM_QUERY = "base";

        protected String doInBackground(String... params) {
            URL builtURL = buildURL(params[0]);
            String response = "";
            try {
                response = getResponseFromHttpUrl(builtURL);
                Log.d("JAMES", params[0].toString());
                Log.d("JAMES", params[1].toString());

                //if( response != null){
                    try{
                        JSONObject jsonObj = new JSONObject(response);
                        JSONObject currencyRates = jsonObj.getJSONObject("rates");
                        String countryCode = params[1];
                        Iterator<?> keys = currencyRates.keys();
                        int i = 0;
                        while(keys.hasNext()){
                            String key = (String)keys.next();
                            Log.d("JAMES",currencyRates.names().getString(i) + "-----" + countryCode);
                            if( currencyRates.names().getString(i).equals(countryCode.trim())){
                                response = currencyRates.get(currencyRates.names().getString(i)).toString();
                                return response;
                            }
                            i++;
                        }

                    } catch( final JSONException e){
                        Log.e("APP","JSON Parsing error: " + e.getMessage());

                    }
              //  }
            } catch(IOException e){
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response){
            TextView result  = (TextView) findViewById(R.id.textView4);
            try {
                currency1 = new CurrencyClass(Double.parseDouble(input1.getText().toString()), countrySpinner1.getSelectedItem().toString());
                Double dub = new Double(response);
                currency1.setRate(dub);
                Double currencyValue = new Double(currency1.calculateConversion());
                DecimalFormat df = new DecimalFormat("#.00");
                String currencyValueStr = df.format(currencyValue);
                Currency currency = Currency.getInstance(countrySpinner1.getSelectedItem().toString());
                result.append(currency.getSymbol() + " " + currencyValueStr);
            } catch( NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }

        private  URL buildURL(String isoCountryCode){
            Uri builtUri = Uri.parse(API_URL).buildUpon()
                    .appendQueryParameter(PARAM_QUERY, isoCountryCode)
                    .build();
            URL url = null;
            try {
                url = new URL(builtUri.toString());
            } catch ( MalformedURLException e){
                e.printStackTrace();
            }
            return url;
        }

        private  String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try{
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String response;
                response = convertStreamToString(in);
                if(response != null)
                    return response;
                else
                    return null;
            } finally {
                urlConnection.disconnect();
            }
        }

        private String convertStreamToString(InputStream is ){
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }




    }

}

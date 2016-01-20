package com.example.android.muhta;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Util.Util;

public class getIPLocationFromURL extends AsyncTask<String, String, String> {

    HttpURLConnection urlConnection;
    private Context mContext;
    public AsyncResponseIPLocation countryNameINTERFACE = null;

    public getIPLocationFromURL(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL("http://ip-api.com/json/?fields=country");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        String mCountryFromIP;
        String mCountrycode;
        String jsonString = result;
        String[] mCountryAndCodeforResult = new String[2];





        try {
            //Convert String Result From API To JSON Format;
            JSONObject obj = new JSONObject(jsonString);

            //insert country from IP-API to String
            mCountryFromIP = Util.getString("country",obj);


            //read file from json buffer
            JSONObject jsonObjectMain = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArrayMain = jsonObjectMain.getJSONArray("countries");

            //loop on json countries top compare it to ip country
            for(int i = 0; i < jsonArrayMain.length(); i++) {

                JSONObject objcountry = jsonArrayMain.getJSONObject(i);

                mCountrycode =  Util.getString("code", objcountry);

                //Compare Country result from IP To Muhta JSON country list
                if(mCountryFromIP.toString().equals(Util.getString("name", objcountry))) {


                   mCountryAndCodeforResult[0] = mCountryFromIP;
                   mCountryAndCodeforResult[1] = mCountrycode;


                   countryNameINTERFACE.processFinish(mCountryAndCodeforResult);
                    Log.e("good", mCountryFromIP + ":" + mCountrycode);

                    break;

                }



            }

            Log.e("My App", "muhuhuh");
            //Toast.makeText(mContext, "mohuhuhu" + Util.getString("country", obj), Toast.LENGTH_LONG).show();


        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + jsonString + "\"");
        }





    }

   //make the json file readable (parse the json file)
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}

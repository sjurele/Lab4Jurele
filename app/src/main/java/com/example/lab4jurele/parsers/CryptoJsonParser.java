package com.example.lab4jurele.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CryptoJsonParser {
    public static List<String> GetCryptoPrices(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line = "";
        String data = "";
        while(line != null){
            line = bufferedReader.readLine();
            data = data + line;
        }

        List<String> result = new ArrayList<>();
        try {
            JSONObject jData = new JSONObject(data);
            JSONArray dataObjects = jData.getJSONArray("data");
            for (int i = 0; i < dataObjects.length(); i++) {
                JSONObject dataObject = dataObjects.getJSONObject(i);
                String cryptoPlace = dataObject.getString("cmc_rank");
                String cryptoName = dataObject.getString("name");
                String cryptoSymbol = dataObject.getString("symbol");
                Double cryptoPrice = dataObject.getJSONObject("quote").getJSONObject("USD").getDouble("price");

                result.add(String.format("%s. %s (%s) %.2f", cryptoPlace, cryptoName, cryptoSymbol, cryptoPrice));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}

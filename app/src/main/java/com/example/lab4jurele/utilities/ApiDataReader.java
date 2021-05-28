package com.example.lab4jurele.utilities;

import com.example.lab4jurele.parsers.CryptoJsonParser;
import com.example.lab4jurele.parsers.EcbXmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.lab4jurele.utilities.Constants.ECBRATES_API_URL;
import static com.example.lab4jurele.utilities.Constants.CRYPTO_API_URL;

public class ApiDataReader {
    public static List<String> getValuesFromApi(String apiCode) throws IOException {
        InputStream apiContentStream = null;
        List<String> result = new ArrayList<>();
        try {
            switch (apiCode) {
                case ECBRATES_API_URL:
                    apiContentStream = downloadUrlContent(ECBRATES_API_URL);
                    result.addAll(EcbXmlParser.getCurrencyRatesBaseUsd(apiContentStream));
                    break;
                case CRYPTO_API_URL:
                    apiContentStream = downloadUrlContent(CRYPTO_API_URL);
                    result.addAll(CryptoJsonParser.GetCryptoPrices(apiContentStream));
                    break;
                default:
            }
        }
        finally {
            if (apiContentStream != null) {
                apiContentStream.close();
            }
        }
        return result;
    }

    //Routine that creates and calls GET request to web page
    private static InputStream downloadUrlContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
}

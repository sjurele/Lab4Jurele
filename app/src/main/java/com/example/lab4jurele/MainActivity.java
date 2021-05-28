package com.example.lab4jurele;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lab4jurele.utilities.AsyncDataLoader;
import com.example.lab4jurele.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvItems;
    private TextView tvStatus;
    private ArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvItems = findViewById(R.id.lv_items);
        this.tvStatus = findViewById(R.id.tv_status);

        this.listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        this.lvItems.setAdapter(this.listAdapter);
    }

    public void onBtnGetCryptoDataClick(View view) {
        this.tvStatus.setText("Crypto prices are loading...");
        new AsyncDataLoader() {
            @Override
            public void onPostExecute(List<String> result) {
                tvStatus.setText("Latest crypto prices in USD:");
                listAdapter.clear();
                listAdapter.addAll(result);
                listAdapter.notifyDataSetChanged();
            }
        }.execute(Constants.CRYPTO_API_URL);
    }

    public void onBtnGetCurrencyDataClick(View view) {
        this.tvStatus.setText("Exchange rates are loading...");
        new AsyncDataLoader() {
            @Override
            public void onPostExecute(List<String> result) {
                tvStatus.setText("Latest ECB exchange rates:");
                listAdapter.clear();
                listAdapter.addAll(result);
                listAdapter.notifyDataSetChanged();
            }
        }.execute(Constants.ECBRATES_API_URL);
    }
}

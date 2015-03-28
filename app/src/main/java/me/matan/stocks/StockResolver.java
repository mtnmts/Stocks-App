package me.matan.stocks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by martin on 3/28/2015.
 */
public class StockResolver extends AsyncTask<String, Void, StockModel> {
    private static final String RESOLVER_URL = "http://hero.matan.me:8080";
    public static StockModel resolveStock(String stock_id) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(RESOLVER_URL).build();
        StockApi stockApi = restAdapter.create(StockApi.class);
        try {
            StockModel sm = stockApi.fetchStock(stock_id);
            Log.d("Debug", "Stock Name: " + sm.getStockName());
            return sm;
        } catch(RetrofitError e){
            Log.e("ERROR!", e.getUrl());
            //Log.e("Error!","ERROR CODE!:" + Integer.toString(e.getResponse().getStatus()));
            Log.e("Error!", "Exception caught fetching my stock!");
            e.printStackTrace();
        }
        return new StockModel();
    }

    @Override
    protected StockModel doInBackground(String... params) {
        if(params.length != 1) {
            Log.d("Error", "Extremely strange... doInBackground called with bad params");
        }
        return resolveStock(params[0]);
    }
}

package me.matan.stocks;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by martin on 3/28/2015.
 */
public interface StockApi {
    @GET("/query_android/{stock_id}")
    public StockModel fetchStock(@Path("stock_id") String stock_id);
}

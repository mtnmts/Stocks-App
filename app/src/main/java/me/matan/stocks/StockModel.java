package me.matan.stocks;

/**
 * Created by martin on 3/28/2015.
 */
public class StockModel {
    private String ticker;
    private String stockName;
    private String price;
    private String isin;

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTicker() {

        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public StockModel(){};

}

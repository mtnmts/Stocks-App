package me.matan.stocks;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;


public class stock_activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_activity);
        Bundle b = getIntent().getExtras();
        String stock_id = b.getString(globals.STOCK_ID_INTENT);
        System.out.println("Resolving stock! " + stock_id);
        StockResolver sr = new StockResolver();
        AsyncTask<String, Void, StockModel> task = sr.execute(stock_id);
        try {
            StockModel sm = task.get();
            Log.d("Win!", "Stock Ticker:" + sm.getTicker());
            // Set fields
            final TextView sn = (TextView)findViewById(R.id.stock_name_text);
            sn.setText(sm.getStockName());
            final TextView is = (TextView)findViewById(R.id.isin_text);
            is.setText(sm.getIsin());
            Log.d("PSTN", sm.getIsin());
            final TextView pt = (TextView)findViewById(R.id.price_text);
            pt.setText(sm.getPrice());
            Log.d("PSTN", sm.getPrice());
            final TextView tt = (TextView)findViewById(R.id.ticker_text);
            tt.setText(sm.getTicker());
            Log.d("PSTN", sm.getTicker());


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

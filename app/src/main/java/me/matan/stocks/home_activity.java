package me.matan.stocks;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import me.matan.stocks.globals;

public class home_activity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        updateIsinList();
        ListView lv = (ListView) findViewById(R.id.isin_list);
        lv.setOnItemClickListener(this);
    }

    private void updateIsinList() {
        SharedPreferences s = getPreferences(0);
        Set<String> trackers = s.getStringSet(globals.ISIN_TRACKLIST_PERF_KEY, new HashSet<String>());
        final ListView lv = (ListView)findViewById(R.id.isin_list);
        Log.d("Debug", "Clearing ISIN's list");
        List<AsyncTask<String, Void, StockModel>> sl = new ArrayList<AsyncTask<String, Void, StockModel>>();
        List<StockModel> sll = new ArrayList<StockModel>();

        StockResolver sr = new StockResolver();

        for(String st : trackers) {
            sl.add(sr.execute(st));
        }
        // Bad! this should all be one request
        for(AsyncTask<String, Void, StockModel> sm : sl) {
            try {
                sll.add(sm.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<StockModel> itemsAdapter =
            new ArrayAdapter<StockModel>(getApplicationContext(), android.R.layout.simple_list_item_1, sll);
        lv.setAdapter(itemsAdapter);
        Log.d("Debug", "ISIN list refreshed with " + Integer.toString(trackers.size()) + " items");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_activity, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        TextView vi = (TextView) arg1;
        Log.d("Debug","Sending to new activity with " + vi.getText().toString());
        Intent intent = new Intent(this, stock_activity.class);
        Bundle b = new Bundle();
        b.putString(globals.STOCK_ID_INTENT, vi.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
    public void onClickTrackButton(View v) {
        Log.d("Debug", "Button clicked");
        final EditText isin = (EditText) findViewById(R.id.isin_text);
        if(null == isin.getText()) {
            Log.d("Debug", "ISIN is null!?");
        } else {
            String isin_text = isin.getText().toString();
            Log.d("Debug", "Adding ISIN: " + isin_text);
            if(isin_text.equals("")) {
                Log.d("Debug", "Empty ISIN");
                return;
            }
            SharedPreferences s = getPreferences(0);
            Set<String> trackers = s.getStringSet(globals.ISIN_TRACKLIST_PERF_KEY, new HashSet<String>());
            if(!trackers.contains(isin_text)) {
                trackers.add(isin_text);
                Log.d("Debug", "Adding ISIN text");
                SharedPreferences.Editor ed = s.edit();
                ed.putStringSet(globals.ISIN_TRACKLIST_PERF_KEY, trackers);
                ed.commit();
            }
        }
        updateIsinList();
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

package com.ouss.pokemanual;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ouss.pokemanual.adapter.PokeListAdapter;
import com.ouss.pokemanual.html.HtmlHelper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {

	protected RequestQueue mRequestQueue;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
        LoadData();
    }
    
    private void LoadData() {
    	StringRequest pokeInfoRequest = new StringRequest(HtmlHelper.pokeIconInfo, 
    		new Response.Listener<String>() {  
	            @Override  
	            public void onResponse(final String pokeIconInfo) {
	            	StringRequest stringRequest = new StringRequest(HtmlHelper.pokeListUrl,  
	            	        new Response.Listener<String>() {  
	            	            @Override  
	            	            public void onResponse(String response) {
	            	            	ExpandableListAdapter adapter = new PokeListAdapter(response, pokeIconInfo, MainActivity.this.getResources().getDisplayMetrics().density);
	            	        		
	            	        		ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.list);
	            	                expandableListView.setAdapter(adapter);
	            	            }  
	            	        }, new Response.ErrorListener() {  
	            	            @Override  
	            	            public void onErrorResponse(VolleyError error) {  
	            	                Log.e("TAG", error.getMessage(), error);  
	            	            }  
	            	        }
	                    );  
	                    mRequestQueue.add(stringRequest);
	            }  
	        }, new Response.ErrorListener() {  
	            @Override  
	            public void onErrorResponse(VolleyError error) {  
	                Log.e("TAG", error.getMessage(), error);  
	            }  
	        }
	    );
        
        mRequestQueue.add(pokeInfoRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

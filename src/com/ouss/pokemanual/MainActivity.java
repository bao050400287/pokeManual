package com.ouss.pokemanual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ouss.pokemanual.adapter.PokeListAdapter;
import com.ouss.pokemanual.html.HtmlHelper;
import com.ouss.pokemanual.provider.PokeProviderUri;
import com.ouss.pokemanual.common.SessionManager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class MainActivity extends Activity {

	private ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SessionManager.context = this;
        expandableListView = (ExpandableListView) findViewById(R.id.list);
        LoadData();
    }
    
    private void insertRecords(String pokeID, String cn, String jp, String en, String dx, String dy, String dw, String dh, int generation, String url) {
    	ContentValues values = new ContentValues();
    	values.put(PokeProviderUri.Poke.pokeID, pokeID);
    	values.put(PokeProviderUri.Poke.cn, cn);
    	values.put(PokeProviderUri.Poke.jp, jp);
    	values.put(PokeProviderUri.Poke.en, en);
    	values.put(PokeProviderUri.Poke.dx, dx);
    	values.put(PokeProviderUri.Poke.dy, dy);
    	values.put(PokeProviderUri.Poke.dw, dw);
    	values.put(PokeProviderUri.Poke.dh, dh);
    	values.put(PokeProviderUri.Poke.generation, generation);
    	values.put(PokeProviderUri.Poke.url, url);
    	
    	getContentResolver().insert(PokeProviderUri.Poke.CONTENT_URI, values);
    }
    
    private void LoadData() {
    	Cursor cursor = getContentResolver().query(PokeProviderUri.Poke.CONTENT_URI, null, null, null, PokeProviderUri.Poke.pokeID + " ASC");
    	if (cursor.getCount() == 0) {
    		cursor.close();
    		SessionManager.getRequestQueue().add(new StringRequest(HtmlHelper.pokeIconInfo, 
    	    		new Response.Listener<String>() {  
	            @Override  
	            public void onResponse(final String pokeIconInfo) {
	            	SessionManager.getRequestQueue().add(new StringRequest(HtmlHelper.pokeListUrl,  
	            	        new Response.Listener<String>() {  
	            	            @Override  
	            	            public void onResponse(String response) {
	            	            	HtmlHelper htmlHelper = new HtmlHelper();
	            	            	HashMap<String, List<String>> iconInfo = htmlHelper.GetPokeIconInfo(pokeIconInfo);
	            	            	HashMap<String, List<String>> pokeList = htmlHelper.GetPokeList(response);
	            	            	List<String> pokeItems;
	            	            	String[] pokeItem;
	            	            	for(Map.Entry<String, List<String>> entry : pokeList.entrySet()) {
	            	            		pokeItems = entry.getValue();
	            	            		for(String item  :  pokeItems){
	            	            			pokeItem = item.split(",");
	            	            			int gen = PokeProviderUri.pokeGroup.indexOf(entry.getKey());
	            	            			List<String> sizeInfo = iconInfo.get(pokeItem[0]);
	            	            			
	            	            			insertRecords(pokeItem[0], 
	            	            					pokeItem[1], 
	            	            					pokeItem[3], 
	            	            					pokeItem[4], 
	            	            					sizeInfo.get(0), 
	            	            					sizeInfo.get(1), 
	            	            					sizeInfo.get(2), 
	            	            					sizeInfo.get(3),
	            	            					gen,
	            	            					pokeItem[2]);
	            	            		}
	            	            	}
	            	            	
	            	            	Cursor newCursor = getContentResolver().query(PokeProviderUri.Poke.CONTENT_URI, null, null, null, PokeProviderUri.Poke.pokeID + " ASC");

	            	            	setExpandableList(newCursor);
	            	            }
	            	}, new Response.ErrorListener() {  
        	            @Override  
        	            public void onErrorResponse(VolleyError error) {  
        	            	Toast.makeText(MainActivity.this, "网络错误,请稍后再试",
       						     Toast.LENGTH_SHORT).show();
        	            }  
        	        }));
	            }
    		}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(MainActivity.this, "网络错误,请稍后再试",
						     Toast.LENGTH_SHORT).show();
				}
			}));
    	} else {
    		setExpandableList(cursor);
            
    	}
    }
    
    private void setExpandableList(Cursor cursor) {
    	final ExpandableListAdapter adapter = new PokeListAdapter(MainActivity.this, cursor);
        expandableListView.setAdapter(adapter);
    	expandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO 自动生成的方法存根
				PokeListAdapter.PokeItem pokeItem  = (PokeListAdapter.PokeItem)adapter.getChild(groupPosition, childPosition);
				Intent intent =new Intent(MainActivity.this, PokeitemActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", pokeItem.id);
				bundle.putString("cnName", pokeItem.cnName);
				bundle.putString("jpName", pokeItem.jpName);
				bundle.putString("enName", pokeItem.enName);
				bundle.putString("url", pokeItem.url);
				bundle.putFloat("dx", pokeItem.dx);
				bundle.putFloat("dy", pokeItem.dy);
				intent.putExtras(bundle);
				startActivity(intent);
				return false;
			}
        });
    	
    	cursor.close();
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

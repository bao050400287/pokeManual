package com.ouss.pokemanual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ouss.pokemanual.common.DensityUtil;
import com.ouss.pokemanual.common.SessionManager;
import com.ouss.pokemanual.html.HtmlHelper;
import com.ouss.pokemanual.provider.PokeProviderUri;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PokeitemActivity extends Activity {

	protected RequestQueue mRequestQueue;
	private Context context;
	private String pokeId;
	private String pokeName;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = PokeitemActivity.this;
		setContentView(R.layout.activity_pokeitem);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		
		pokeId = bundle.getString("id");
		pokeName = bundle.getString("name");
		url = bundle.getString("url");
		float dx = bundle.getFloat("dx");
		float dy = bundle.getFloat("dy");
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(pokeName);
		
		BitmapDrawable icon = (BitmapDrawable)PokeitemActivity.this.getResources().getDrawable(R.drawable.icon_0721);

		actionBar.setIcon(drawableToBitmap(icon.getBitmap(), (int)Math.abs(dx), (int)Math.abs(dy)));
		mRequestQueue = Volley.newRequestQueue(PokeitemActivity.this);
		LoadData();
	}
	
	
	private void LoadData() {
		SessionManager.getRequestQueue().add(new StringRequest(HtmlHelper.pokeListUrl,  
    	        new Response.Listener<String>() {  
    	            @Override  
    	            public void onResponse(String pokeInfo) {
    	            	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBarPokeItem);
		            	progressBar.setVisibility(View.GONE);
		            	
		            	LinearLayout pokeItemLayout = (LinearLayout)findViewById(R.id.pokeItemView);
		            	pokeItemLayout.setVisibility(View.VISIBLE);
    	            }
    	}, new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	Toast.makeText(PokeitemActivity.this, "Õ¯¬Á¥ÌŒÛ,«Î…‘∫Û‘Ÿ ‘",
					     Toast.LENGTH_SHORT).show();
            }  
        }));
	}
	
	private Drawable drawableToBitmap(Bitmap drawable, int dx, int dy) {       
        Bitmap bitmap = Bitmap.createBitmap(drawable, 
        		DensityUtil.dip2px(context, dx),
        		DensityUtil.dip2px(context, dy),
        		DensityUtil.dip2px(context, 32),
        		DensityUtil.dip2px(context, 32));
        
        bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true);

        return new BitmapDrawable(bitmap);
	}
}

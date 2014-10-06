package com.ouss.pokemanual;

import java.util.List;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ouss.pokemanual.common.DensityUtil;
import com.ouss.pokemanual.common.PokeHelper;
import com.ouss.pokemanual.common.PokeHelper.PokeColor;
import com.ouss.pokemanual.common.SessionManager;
import com.ouss.pokemanual.html.HtmlHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
	private ProgressBar progressBar;
	private LinearLayout pokeItemLayout;
	
	private static Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = PokeitemActivity.this;
		setContentView(R.layout.activity_pokeitem);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		
		pokeId = bundle.getString("id");
		pokeName = bundle.getString("name");
		url = "http://wiki.52poke.com" + bundle.getString("url");
		float dx = bundle.getFloat("dx");
		float dy = bundle.getFloat("dy");
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(pokeName);
		
		BitmapDrawable icon = (BitmapDrawable)PokeitemActivity.this.getResources().getDrawable(R.drawable.icon_0721);

		actionBar.setIcon(drawableToBitmap(icon.getBitmap(), (int)Math.abs(dx), (int)Math.abs(dy)));

		progressBar = (ProgressBar)findViewById(R.id.progressBarPokeItem);
		pokeItemLayout = (LinearLayout)findViewById(R.id.pokeItemView);
		
		LoadData();
	}
	
	
	private void LoadData() {
		SessionManager.getRequestQueue().add(new StringRequest(url,  
    	        new Response.Listener<String>() {  
    	            @Override  
    	            public void onResponse(final String pokeInfo) {
    	            	new Thread(new Runnable() {                    
    	                     public void run() {
    	                    	 //do something
    	                    	 Runnable runable;
    	                    	 
    	                    	 List<String> pokeInfoList = HtmlHelper.GetPokeItemInfo(pokeInfo);
    	                    	 if (pokeInfoList.isEmpty()) {
    	                    		 runable = new Runnable() {                    
 	    	                            @Override
 	    	                            public void run() {
 	    	                            	SetViewVisibility(0,0);
 	    	                            	Toast.makeText(PokeitemActivity.this, "数据获取错误！",
    	    	           						     Toast.LENGTH_SHORT).show();
 	    	                            }
     	    	                     };
    	                    	 } else {
    	                    		 final PokeColor pokeType1 = PokeHelper.getPokeColor(pokeInfoList.get(0));
    	                    		 final PokeColor pokeType2;
    	                    		 if (pokeInfoList.get(0).equals(pokeInfoList.get(1))){
    	                    			 pokeType2 = pokeType1;
    	                    		 } else {
    	                    			 pokeType2 = PokeHelper.getPokeColor(pokeInfoList.get(1));
    	                    		 }
    	                    		 runable = new Runnable() {                    
  	    	                            @Override
  	    	                            public void run() {
  	    	                            	SetViewVisibility(pokeType1.getBgColor(), pokeType2.getBdColor());
  	    	                            	
  	    	                            }
      	    	                     };
    	                    	 }
    	                    	 handler.post(runable);                                
    	                   }
    	               }).start();
		            	
    	            }
    	}, new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	Toast.makeText(PokeitemActivity.this, "网络错误,请稍后再试",
					     Toast.LENGTH_SHORT).show();
            }  
        }));
	}
	
	private void SetViewVisibility(int bgColor, int bdColor){
		progressBar.setVisibility(View.GONE);
    	pokeItemLayout.setVisibility(View.VISIBLE);
    	if (bgColor != 0 &&  bdColor != 0) {
    		pokeItemLayout.setBackgroundColor(Color.RED);
    		
    	}
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

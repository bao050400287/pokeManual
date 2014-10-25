package com.ouss.pokemanual;

import java.util.HashMap;
import java.util.List;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.ouss.pokemanual.common.DensityUtil;
import com.ouss.pokemanual.common.LruImageCache;
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
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PokeitemActivity extends Activity {

	protected RequestQueue mRequestQueue;
	private Context context;
	private String pokeId;
	private String pokeName;
	private String pokeSubName;
	private String url;
	private ProgressBar progressBar;
	private ScrollView pokeItemLayout;
	private TableLayout pokeItemGrid;
	private ImageLoader imageLoader;
	
	private static Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = PokeitemActivity.this;
		setContentView(R.layout.activity_pokeitem);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		
		pokeId = bundle.getString("id");
		pokeName = bundle.getString("cnName");
		pokeSubName = bundle.getString("enName");
		if (pokeName.equals("null")){
			pokeName = bundle.getString("jpName");
		} else {
			pokeSubName = bundle.getString("jpName") + " " +pokeSubName;
		}
		url = "http://wiki.52poke.com" + bundle.getString("url");
		float dx = bundle.getFloat("dx");
		float dy = bundle.getFloat("dy");
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(pokeName);
		
		BitmapDrawable icon = (BitmapDrawable)PokeitemActivity.this.getResources().getDrawable(R.drawable.icon_0721_2x);

		actionBar.setIcon(drawableToBitmap(icon.getBitmap(), (int)Math.abs(dx), (int)Math.abs(dy)));

		progressBar = (ProgressBar)findViewById(R.id.progressBarPokeItem);
		pokeItemLayout = (ScrollView)findViewById(R.id.pokeItemView);
		pokeItemGrid = (TableLayout)findViewById(R.id.pokeItemGrid);
		LruImageCache lruImageCache = LruImageCache.instance();
		imageLoader = new ImageLoader(SessionManager.getRequestQueue(),lruImageCache);
		
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
    	                    	 
    	                    	 final List<String> pokeInfoList = HtmlHelper.GetPokeItemInfo(pokeInfo);
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
    	                    		 final String pokeStrType;
    	                    		 if (pokeInfoList.get(0).equals(pokeInfoList.get(1))){
    	                    			 pokeType2 = pokeType1;
    	                    			 pokeStrType = pokeInfoList.get(0);
    	                    		 } else {
    	                    			 pokeType2 = PokeHelper.getPokeColor(pokeInfoList.get(1));
    	                    			 pokeStrType = pokeInfoList.get(0) + " " + pokeInfoList.get(1);
    	                    		 }
    	                    		 runable = new Runnable() {                    
  	    	                            @Override
  	    	                            public void run() {
  	    	                            	TextView pokeNameTxt = (TextView)findViewById(R.id.pokeName);
  	    	                            	pokeNameTxt.setText(pokeName);
  	    	                            	
  	    	                            	NetworkImageView pokeItemTypeImg = (NetworkImageView) findViewById(R.id.pokeItemTypeImg);
  	    	                            	SetVolleyImage(pokeItemTypeImg, pokeInfoList.get(3));
  	    	                            	
  	    	                            	TextView pokeIdTxt = (TextView)findViewById(R.id.pokeIdTxt);
  	    	                            	pokeIdTxt.setText("#" + pokeId);
  	    	                            	
  	    	                            	TextView pokeNameJp = (TextView)findViewById(R.id.pokeNameJp);
  	    	                            	pokeNameJp.setText(pokeSubName);
  	    	                            	
  	    	                            	NetworkImageView pokeItemImg = (NetworkImageView) findViewById(R.id.pokeItemImg);
  	    	                            	LayoutParams lp = (LayoutParams)pokeItemImg.getLayoutParams();
  	    	                            	lp.height = lp.width;
  	    	                            	pokeItemImg.setLayoutParams(lp);
  	    	                            	SetVolleyImage(pokeItemImg, pokeInfoList.get(4));
  	    	                            	
  	    	                            	TextView pokeStrTypeTxt = (TextView)findViewById(R.id.pokeStrTypeTxt);
  	    	                            	pokeStrTypeTxt.setText(pokeStrType);
  	    	                            	
  	    	                            	TextView pokeClassifyTxt = (TextView)findViewById(R.id.pokeClassifyTxt);
  	    	                            	pokeClassifyTxt.setText(HtmlHelper.ReplaceHtmlTag(pokeInfoList.get(5)));
  	    	                            	
  	    	                            	TextView pokeLevelExpTxt = (TextView)findViewById(R.id.pokeLevelExpTxt);
  	    	                            	pokeLevelExpTxt.setText(pokeInfoList.get(7));
  	    	                            	
  	    	                            	TextView pokeHeightTxt = (TextView)findViewById(R.id.pokeHeightTxt);
  	    	                            	pokeHeightTxt.setText(pokeInfoList.get(8));
  	    	                            	
  	    	                            	TextView pokeWeightTxt = (TextView)findViewById(R.id.pokeWeightTxt);
  	    	                            	pokeWeightTxt.setText(pokeInfoList.get(9));
  	    	                            	
  	    	                            	TextView pokeManualColorTxt = (TextView)findViewById(R.id.pokeManualColorTxt);
  	    	                            	pokeManualColorTxt.setText(pokeInfoList.get(15));
  	    	                            	if (!pokeInfoList.get(14).equals("null") && !pokeInfoList.get(14).equals("")) {
  	    	                            		pokeManualColorTxt.setTextColor(Color.parseColor(PokeHelper.ChangeColor(pokeInfoList.get(14))));
  	    	                            	}
  	    	                            	
  	    	                            	NetworkImageView pokeFitImg = (NetworkImageView) findViewById(R.id.pokeFitImg);
  	    	                            	SetVolleyImage(pokeFitImg, pokeInfoList.get(11));
  	    	                            	
  	    	                            	NetworkImageView pokeFootImg = (NetworkImageView) findViewById(R.id.pokeFootImg);
  	    	                            	SetVolleyImage(pokeFootImg, pokeInfoList.get(13));
  	    	                            	
  	    	                            	TextView pokeCatchPreTxt = (TextView)findViewById(R.id.pokeCatchPreTxt);
  	    	                            	pokeCatchPreTxt.setText(pokeInfoList.get(16) + "\n" + pokeInfoList.get(17));
  	    	                            	
  	    	                            	String pokeSexTxtStr = "";
  	    	                            	if (pokeInfoList.get(18).trim().equals("无性别")){
  	    	                            		pokeSexTxtStr = "无性别";
  	    	                            	} else {
  	    	                            		HashMap<String, Float> pokeSexList = HtmlHelper.GetPokeSexRatio(pokeInfoList.get(18));
  	    	                            		if (pokeSexList.containsKey("雄")){
  	    	                            			pokeSexTxtStr += pokeSexList.get("雄") + "% 雄性";
  	    	                            			TextView pokeMan = (TextView)findViewById(R.id.pokeMan);
  	    	                            			pokeMan.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, pokeSexList.get("雄"))); 
  	    	                            		}
  	    	                            		if (pokeSexList.containsKey("雌")){
  	    	                            			if(!pokeSexTxtStr.equals("")){
  	    	                            				pokeSexTxtStr += ",  ";
  	    	                            			}
  	    	                            			pokeSexTxtStr += pokeSexList.get("雌") + "% 雌性";
  	    	                            			TextView pokeWomen = (TextView)findViewById(R.id.pokeWomen);
  	    	                            			pokeWomen.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, pokeSexList.get("雌"))); 
  	    	                            		}
  	    	                            	}
  	    	                            	TextView pokeSexTxt = (TextView)findViewById(R.id.pokeSexTxt);
  	    	                            	pokeSexTxt.setText(pokeSexTxtStr);
  	    	                            	
  	    	                            	TextView pokeEggType = (TextView)findViewById(R.id.pokeEggType);
  	    	                            	pokeEggType.setText(HtmlHelper.ReplaceHtmlTag(pokeInfoList.get(19)));
  	    	                            	
  	    	                            	TextView pokeBrithFoot = (TextView)findViewById(R.id.pokeBrithFoot);
  	    	                            	pokeBrithFoot.setText(pokeInfoList.get(20) + "孵化周期 ("+pokeInfoList.get(21)+"步)");
  	    	                            	
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
    		pokeItemLayout.setBackgroundColor(getResources().getColor(bdColor));
    		pokeItemGrid.setBackgroundColor(getResources().getColor(bgColor));
    	}
	}
	
	private void SetVolleyImage(NetworkImageView img, String url){
		img.setDefaultImageResId(R.drawable.ball_load);  
		img.setErrorImageResId(R.drawable.ball_err);          
		img.setImageUrl(url, imageLoader);
	}
	
	private Drawable drawableToBitmap(Bitmap drawable, int dx, int dy) {       
        Bitmap bitmap = Bitmap.createBitmap(drawable, 
        		DensityUtil.dip2px(context, dx*2),
        		DensityUtil.dip2px(context, dy*2),
        		DensityUtil.dip2px(context, 64),
        		DensityUtil.dip2px(context, 64));
        
        bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true);

        return new BitmapDrawable(bitmap);
	}
}

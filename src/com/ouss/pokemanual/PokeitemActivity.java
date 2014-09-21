package com.ouss.pokemanual;

import com.ouss.pokemanual.common.DensityUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class PokeitemActivity extends Activity {

	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = PokeitemActivity.this;
		setContentView(R.layout.activity_pokeitem);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		
		String pokeId = bundle.getString("id");
		String pokeName = bundle.getString("name");
		String url = bundle.getString("url");
		float dx = bundle.getFloat("dx");
		float dy = bundle.getFloat("dy");
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(pokeName);
		
		BitmapDrawable icon = (BitmapDrawable)PokeitemActivity.this.getResources().getDrawable(R.drawable.icon_0721);

		actionBar.setIcon(drawableToBitmap(icon.getBitmap(), (int)Math.abs(dx), (int)Math.abs(dy)));
	}
	
	private Drawable drawableToBitmap(Bitmap drawable, int dx, int dy) {       
        Bitmap bitmap = Bitmap.createBitmap(drawable, 
        		DensityUtil.dip2px(context, dx),
        		DensityUtil.dip2px(context, dy),
        		DensityUtil.dip2px(context, 32),
        		DensityUtil.dip2px(context, 32));
        
        bitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, true);

        return new BitmapDrawable(bitmap);
	}
}

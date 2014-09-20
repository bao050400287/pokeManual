package com.ouss.pokemanual;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class PokeitemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		Drawable icon;
		actionBar.setIcon(icon);
	}
}

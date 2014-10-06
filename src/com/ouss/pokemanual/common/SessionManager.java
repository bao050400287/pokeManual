package com.ouss.pokemanual.common;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SessionManager {

	public static Context context;
	private static RequestQueue requestQueue;

	
	public static RequestQueue getRequestQueue() {
		if (context == null) {
			throw new IllegalStateException("Context not initialized");
		}
		if (requestQueue == null) {
			requestQueue = Volley.newRequestQueue(context);
		}
		
		return requestQueue;
		
	}


}

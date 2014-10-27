package com.ouss.pokemanual.common;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class StringRequestPost extends StringRequest {

	HashMap<String, String> param;
	
	public StringRequestPost(String url, Listener<String> listener,
			ErrorListener errorListener,HashMap<String, String> param) {		
		super(Method.POST, url, listener, errorListener);
		this.param = param;
	}
	public StringRequestPost(String url, Listener<String> listener){
		this(url, listener, null, null);
	}
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return param;
	}

	@Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<String, String>();
        params.put("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0");
        params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        params.put("Accept-Language", "en-US,en;q=0.5");
        params.put("Accept-Encoding", "gzip, deflate");
        params.put("Connection", "keep-alive");
        return params;
    }
}

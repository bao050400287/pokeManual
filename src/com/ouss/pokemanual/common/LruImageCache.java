package com.ouss.pokemanual.common;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class LruImageCache implements ImageCache{

	private static LruCache<String, Bitmap> mMemoryCache;
	
	private static LruImageCache lruImageCache;
	
	private static FileUtils fileUtils;
	
	private static final String WHOLESALE_CONV = ".png";
	
	private LruImageCache(){
		// Get the Max available memory
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap bitmap){
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};		
	}
	
	public static LruImageCache instance(Context context){
		if(lruImageCache == null){
			lruImageCache = new LruImageCache();
		}
		if (fileUtils == null){
			fileUtils = new FileUtils(context);
		}
		return lruImageCache;
	}
	
	private String convertUrlToFileName(String url) {
        String[] strs = url.split("/");
        return strs[strs.length - 1] + WHOLESALE_CONV;
    }
	
	@Override
	public Bitmap getBitmap(String url) {		
		Bitmap result =  mMemoryCache.get(url);	
		if (result == null) {
			result = fileUtils.getBitmap(convertUrlToFileName(url));
		}
		return result;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		if(getBitmap(url) == null){
			mMemoryCache.put(url, bitmap);
			
			try {
				fileUtils.savaBitmap(convertUrlToFileName(url), bitmap);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}		
	}

}

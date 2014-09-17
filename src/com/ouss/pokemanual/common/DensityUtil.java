package com.ouss.pokemanual.common;

import android.content.Context;

public class DensityUtil {  
	  
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) dip2px(scale, dpValue);  
    }
    
    public static float dip2px(float scale, float dpValue) {  
        return (dpValue * scale + 0.5f);  
    } 
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) px2dip( scale,  pxValue);
    }  
    
    public static float px2dip(float scale, float pxValue) {
        return (pxValue / scale + 0.5f);  
    }
}  

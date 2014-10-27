package com.ouss.pokemanual.common;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;

import com.ouss.pokemanual.R;

public class PokeHelper {
	public enum PokeColor {  
		normal("һ��", R.color.normal, R.color.normal_border,R.color.normal_line), 
		fire("��", R.color.fire, R.color.fire_border,R.color.fire_line), 
		fighting("��", R.color.fighting, R.color.fighting_border,R.color.fighting_line),
		water("ˮ", R.color.water, R.color.water_border,R.color.water_line),
		flying("����", R.color.flying, R.color.flying_border,R.color.flying_line),
		grass("��", R.color.grass, R.color.grass_border,R.color.grass_line),
		poison("��", R.color.poison, R.color.poison_border,R.color.poison_line),
		electric("��", R.color.electric, R.color.electric_border,R.color.electric_line),
		ground("����", R.color.ground, R.color.ground_border,R.color.ground_line),
		psychic("������", R.color.psychic, R.color.psychic_border,R.color.psychic_line),
		rock("��ʯ", R.color.rock, R.color.rock_border,R.color.rock_line),
		ice("��", R.color.ice, R.color.ice_border,R.color.ice_line),
		bug("��", R.color.bug, R.color.bug_border,R.color.bug_line),
		gragon("��", R.color.gragon, R.color.gragon_border,R.color.gragon_line),
		ghost("����", R.color.ghost, R.color.ghost_border,R.color.ghost_line),
		dark("��", R.color.dark, R.color.dark_border,R.color.dark_line),
		steel("��", R.color.steel, R.color.steel_border,R.color.steel_line),
		fairy("����", R.color.fairy, R.color.fairy_border,R.color.fairy_line);  
	    // ��Ա����  
	    private String name;
	    private int bgColor;
	    private int bdColor;
	    private int blColor;
	    
	    private PokeColor(String name, int bgColor, int borderColor, int blineColor) {  
	        this.name = name;
	        this.bgColor = bgColor;
	        this.bdColor = borderColor;
	        this.blColor = blineColor;
	    }
	    
	    public String getName(){
	    	return this.name;
	    }
	    
	    public int getBgColor() {
	    	return this.bgColor;
	    }
	    
	    public int getBdColor(){
	    	return this.bdColor;
	    }
	    
	    public int getBlColor(){
	    	return this.blColor;
	    }
	}
	
	public static PokeColor getPokeColor(String type){
		if (type.equals("���Y")){
			return PokeColor.fighting;
		} else if (type.equals("��")) {
			return PokeColor.dark;
		}else if (type.equals("�")){
			return PokeColor.steel;
		}else if (type.equals("���`")){
			return PokeColor.ghost;
		}else if (type.equals("�x")){
			return PokeColor.bug;
		}else if (type.equals("�w��")){
			return PokeColor.flying;
		}else if (type.equals("�")){
			return PokeColor.electric;
		}else if (type.equals("��")){
			return PokeColor.gragon;
		}
		for (PokeColor c : PokeColor.values()) {
            if (c.getName().equals(type)) {
                return c;
            }
        }
        return PokeColor.normal;
	}
	
	public static String ChangeColor(String strColor){
		String resultString = "#" + strColor;
		if (strColor.length() == 3) {
			resultString = "#" + strColor.replaceAll("(.)(.)(.)", "$1$1$2$2$3$3");
		}
		
		return resultString;
	}
	
	public static void SetBackgroundAndborder(Resources rs,int bgColor, int bdColor, GradientDrawable bgShape) {
		SetBackgroundAndborder(rs,bgColor, bdColor, bgShape, 2);
	}

	public static void SetBackgroundAndborder(Resources rs,int bgColor, int bdColor, GradientDrawable bgShape, int strokeWidth) {
		if (bgColor >0){
			bgShape.setColor(rs.getColor(bgColor));
		}
		if (bdColor > 0){
			bgShape.setStroke(strokeWidth, rs.getColor(bdColor));
		}
	}
}

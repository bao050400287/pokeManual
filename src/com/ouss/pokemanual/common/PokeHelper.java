package com.ouss.pokemanual.common;

import com.ouss.pokemanual.R;

public class PokeHelper {
	public enum PokeColor {  
		normal("一般", R.color.normal, R.color.normal_border,R.color.normal_line), 
		fire("炎", R.color.fire, R.color.fire_border,R.color.fire_line), 
		fighting("格斗", R.color.fighting, R.color.fighting_border,R.color.fighting_line),
		water("水", R.color.water, R.color.water_border,R.color.water_line),
		flying("飞行", R.color.flying, R.color.flying_border,R.color.flying_line),
		grass("草", R.color.grass, R.color.grass_border,R.color.grass_line),
		poison("毒", R.color.poison, R.color.poison_border,R.color.poison_line),
		electric("电", R.color.electric, R.color.electric_border,R.color.electric_line),
		ground("地上", R.color.ground, R.color.ground_border,R.color.ground_line),
		psychic("超能力", R.color.psychic, R.color.psychic_border,R.color.psychic_line),
		rock("岩石", R.color.rock, R.color.rock_border,R.color.rock_line),
		ice("冰", R.color.ice, R.color.ice_border,R.color.ice_line),
		bug("虫", R.color.bug, R.color.bug_border,R.color.bug_line),
		gragon("龙", R.color.gragon, R.color.gragon_border,R.color.gragon_line),
		ghost("幽灵", R.color.ghost, R.color.ghost_border,R.color.ghost_line),
		dark("恶", R.color.dark, R.color.dark_border,R.color.dark_line),
		steel("钢", R.color.steel, R.color.steel_border,R.color.steel_line),
		fairy("妖精", R.color.fairy, R.color.fairy_border,R.color.fairy_line);  
	    // 成员变量  
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
		if (type.equals("格鬥")){
			return PokeColor.fighting;
		} else if (type.equals("惡")) {
			return PokeColor.dark;
		}else if (type.equals("鋼")){
			return PokeColor.steel;
		}else if (type.equals("幽靈")){
			return PokeColor.ghost;
		}else if (type.equals("蟲")){
			return PokeColor.bug;
		}else if (type.equals("飛行")){
			return PokeColor.flying;
		}else if (type.equals("電")){
			return PokeColor.electric;
		}else if (type.equals("龍")){
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
}

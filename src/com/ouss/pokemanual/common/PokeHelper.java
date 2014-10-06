package com.ouss.pokemanual.common;

import com.ouss.pokemanual.R;

public class PokeHelper {
	public enum PokeColor {  
		normal("一般", R.color.normal, R.color.normal_border), 
		fire("炎", R.color.fire, R.color.fire_border), 
		fighting("格斗", R.color.fighting, R.color.fighting_border),
		water("水", R.color.water, R.color.water_border),
		flying("飞行", R.color.flying, R.color.flying_border),
		grass("草", R.color.grass, R.color.grass_border),
		poison("毒", R.color.poison, R.color.poison_border),
		electric("电", R.color.electric, R.color.electric_border),
		ground("地上", R.color.ground, R.color.ground_border),
		psychic("超能力", R.color.psychic, R.color.psychic_border),
		rock("岩石", R.color.rock, R.color.rock_border),
		ice("冰", R.color.ice, R.color.ice_border),
		bug("虫", R.color.bug, R.color.bug_border),
		gragon("龙", R.color.gragon, R.color.gragon_border),
		ghost("幽灵", R.color.ghost, R.color.ghost_border),
		dark("恶", R.color.dark, R.color.dark_border),
		steel("钢", R.color.steel, R.color.steel_border),
		fairy("妖精", R.color.fairy, R.color.fairy_border);  
	    // 成员变量  
	    private String name;
	    private int bgColor;
	    private int bdColor;
	    
	    private PokeColor(String name, int bgColor, int borderColor) {  
	        this.name = name;
	        this.bgColor = bgColor;
	        this.bdColor = borderColor;
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
	}
	
	public PokeColor getPokeColor(String type){
		for (PokeColor c : PokeColor.values()) {
            if (c.getName() == type) {
                return c;
            }
        }
        return null;
	}
}

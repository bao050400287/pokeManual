package com.ouss.pokemanual.common;

import com.ouss.pokemanual.R;

public class PokeHelper {
	public enum PokeColor {  
		normal("һ��", R.color.normal, R.color.normal_border), 
		fire("��", R.color.fire, R.color.fire_border), 
		fighting("��", R.color.fighting, R.color.fighting_border),
		water("ˮ", R.color.water, R.color.water_border),
		flying("����", R.color.flying, R.color.flying_border),
		grass("��", R.color.grass, R.color.grass_border),
		poison("��", R.color.poison, R.color.poison_border),
		electric("��", R.color.electric, R.color.electric_border),
		ground("����", R.color.ground, R.color.ground_border),
		psychic("������", R.color.psychic, R.color.psychic_border),
		rock("��ʯ", R.color.rock, R.color.rock_border),
		ice("��", R.color.ice, R.color.ice_border),
		bug("��", R.color.bug, R.color.bug_border),
		gragon("��", R.color.gragon, R.color.gragon_border),
		ghost("����", R.color.ghost, R.color.ghost_border),
		dark("��", R.color.dark, R.color.dark_border),
		steel("��", R.color.steel, R.color.steel_border),
		fairy("����", R.color.fairy, R.color.fairy_border);  
	    // ��Ա����  
	    private String name;
	    
	    private PokeColor(String name, int bgColor, int borderColor) {  
	        this.name = name;
	    }  
	}
}

package com.ouss.pokemanual.provider;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.provider.BaseColumns;

public class PokeProviderUri {
	public static final String AUTHORITY  = "com.ouss.pokemanual.provider";

	public static final class Poke implements BaseColumns {
	    public static final Uri CONTENT_URI  = Uri.parse("content://" + AUTHORITY);
	       // ��������
	    public static final String pokeID = "pokeID";
	    
	    public static final String cn = "cn";

	    public static final String jp = "jp";

	    public static final String en = "en";

	    public static final String dx = "dx";

	    public static final String dy = "dy";

	    public static final String dw = "dw";

	    public static final String dh = "dh";
	    
	    public static final String generation = "generation";
	    
	    public static final String url = "url";
	}
	
	public static List<String> pokeGroup = new ArrayList<String>() {{
		add("��һ����");
		add("�ڶ�����");
		add("��������");
		add("��������");
		add("��������");
		add("��������");
	}};
}

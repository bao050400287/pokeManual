package com.ouss.pokemanual.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ouss.pokemanual.R;
import com.ouss.pokemanual.html.HtmlHelper;
import com.ouss.pokemanual.provider.PokeProviderUri;
import com.ouss.pokemanual.common.DensityUtil;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PokeListAdapter extends BaseExpandableListAdapter {
	private List<String> armTypes;
    private List<List<String>> arms;
    private HtmlHelper htmlHelper;
    private HashMap<String, List<String>> iconInfo;
    private float contextScale;
    private LayoutInflater mInflater;
    private List<String> group = new ArrayList<String>();
    private List<List<PokeItem>> children = new ArrayList<List<PokeItem>>();
    
    private class PokeItem {
    	String id;
    	String cnName;
    	String jpName;
    	String enName;
    	String url;
    	float dx;
    	float dy;
    }
    
    public PokeListAdapter(Context context, Cursor cursor) {
    	for (String groupName :PokeProviderUri.pokeGroup){
    		group.add(groupName);
    		children.add(new ArrayList<PokeItem>());
    	}
    	PokeItem pokeItem;
    	int groupIx;
    	for(cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()){ 
    		pokeItem = new PokeItem();
    		groupIx = cursor.getInt(cursor.getColumnIndex(PokeProviderUri.Poke.generation));
    		pokeItem.id = cursor.getString(cursor.getColumnIndex(PokeProviderUri.Poke.pokeID));
    		pokeItem.cnName = cursor.getString(cursor.getColumnIndex(PokeProviderUri.Poke.cn));
    		pokeItem.jpName = cursor.getString(cursor.getColumnIndex(PokeProviderUri.Poke.jp));
    		pokeItem.enName = cursor.getString(cursor.getColumnIndex(PokeProviderUri.Poke.en));
    		pokeItem.url = cursor.getString(cursor.getColumnIndex(PokeProviderUri.Poke.url));
    		pokeItem.dx = cursor.getFloat(cursor.getColumnIndex(PokeProviderUri.Poke.dx));
    		pokeItem.dy = cursor.getFloat(cursor.getColumnIndex(PokeProviderUri.Poke.dy));
    		
    		children.get(groupIx).add(pokeItem);
    	}
    }
    
    public PokeListAdapter(Context context, String response, String pokeInfoStr){
    	super();
    	mInflater = LayoutInflater.from(context);
    	contextScale = context.getResources().getDisplayMetrics().density;
    	htmlHelper = new HtmlHelper();
    	iconInfo = htmlHelper.GetPokeIconInfo(pokeInfoStr);
    	HashMap<String, List<String>> pokeList = htmlHelper.GetPokeList(response);
    	armTypes = new ArrayList<String>();
    	arms = new ArrayList<List<String>>();
    	
    	for(Map.Entry<String, List<String>> entry : pokeList.entrySet()) {
    		armTypes.add(entry.getKey());
    		arms.add(entry.getValue());
    	}
    }
	
	@Override
	public int getGroupCount() {
		// TODO �Զ����ɵķ������
		return group.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO �Զ����ɵķ������
		return children.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO �Զ����ɵķ������
		return group.get(groupPosition);  
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO �Զ����ɵķ������
		return children.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO �Զ����ɵķ������
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO �Զ����ɵķ������
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO �Զ����ɵķ������
		return true;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO �Զ����ɵķ������
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.pokelist_group, parent, false);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.grouptext);
		textView.setText(getGroup(groupPosition).toString());
        return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO �Զ����ɵķ������
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.pokelist_child, parent, false);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.childtext);
			holder.icon = (ImageView) convertView.findViewById(R.id.childimg);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PokeItem pokeItem = (PokeItem)getChild(groupPosition, childPosition);
		String show = pokeItem.cnName;
		if (!show.equals("null")) {
			show += "(" + pokeItem.jpName + ")";
		} else {
			show = pokeItem.jpName;
		}
		holder.text.setText(show);
		Matrix matrix = new Matrix();
		float dx = DensityUtil.dip2px(contextScale, pokeItem.dx) ;
		float dy = DensityUtil.dip2px(contextScale, pokeItem.dy);
		matrix.postTranslate(dx, dy);

		holder.icon.setImageMatrix(matrix);
		
		return convertView;
		/*String[] poke = getChild(groupPosition, childPosition).toString().split(",");
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.pokelist_child, parent, false);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.childtext);
			holder.icon = (ImageView) convertView.findViewById(R.id.childimg);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String show = poke[1].trim();
		if (!show.equals("null")) {
			show += "(" + poke[3] + ")";
		} else {
			show = poke[3];
		}
		holder.text.setText(show);
		
		List<String> sizeInfo = iconInfo.get(poke[0]);
		if (sizeInfo != null) {
			Matrix matrix = new Matrix();
			float dx = DensityUtil.dip2px(contextScale, Float.parseFloat(sizeInfo.get(0))) ;
			float dy = DensityUtil.dip2px(contextScale, Float.parseFloat(sizeInfo.get(1)));
			matrix.postTranslate(dx, dy);

			holder.icon.setImageMatrix(matrix);
		}
		
		return convertView;*/
	}
	
	static class ViewHolder {
		TextView text;
		ImageView icon;
	}

	@Override
	public boolean isChildSelectable(int groupPosition,
			int childPosition) {
		// TODO �Զ����ɵķ������
		return true;
	}
}

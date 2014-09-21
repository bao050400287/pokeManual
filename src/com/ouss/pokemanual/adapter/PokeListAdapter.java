package com.ouss.pokemanual.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ouss.pokemanual.R;
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
    private float contextScale;
    private LayoutInflater mInflater;
    private List<String> group = new ArrayList<String>();
    private List<List<PokeItem>> children = new ArrayList<List<PokeItem>>();
    
    public class PokeItem {
    	public String id;
    	public String cnName;
    	public String jpName;
    	public String enName;
    	public String url;
    	public float dx;
    	public float dy;
    }
    
    public PokeListAdapter(Context context, Cursor cursor) {
    	super();
    	mInflater = LayoutInflater.from(context);
    	contextScale = context.getResources().getDisplayMetrics().density;
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
			convertView = mInflater.inflate(R.layout.pokelist_group, null);
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
			convertView = mInflater.inflate(R.layout.pokelist_child, null);
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
		float dx = DensityUtil.dip2px(contextScale, pokeItem.dx);
		float dy = DensityUtil.dip2px(contextScale, pokeItem.dy);
		matrix.postTranslate(dx, dy);

		holder.icon.setImageMatrix(matrix);
		
		return convertView;
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

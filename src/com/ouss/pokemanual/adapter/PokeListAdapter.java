package com.ouss.pokemanual.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ouss.pokemanual.R;
import com.ouss.pokemanual.html.HtmlHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class PokeListAdapter extends BaseExpandableListAdapter {
	private List<String> armTypes;
    private List<List<List<String>>> arms;
    
    public PokeListAdapter(){
    	super();
    	
    	HtmlHelper htmlHelper = new HtmlHelper();
    	HashMap<String, List<List<String>>> pokeList = htmlHelper.GetPokeList();
    	
    	armTypes = new ArrayList<String>();
    	arms = new ArrayList<List<List<String>>>();
    	
    	for(Map.Entry<String, List<List<String>>> entry : pokeList.entrySet()) {
    		armTypes.add(entry.getKey());
    		arms.add(entry.getValue());
    	}
    }
	
	@Override
	public int getGroupCount() {
		// TODO 自动生成的方法存根
		return armTypes.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO 自动生成的方法存根
		return arms.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO 自动生成的方法存根
		return armTypes.get(groupPosition);  
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return arms.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO 自动生成的方法存根
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO 自动生成的方法存根
		return true;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokelist_group, parent, false);
		TextView textView = (TextView) v.findViewById(R.id.grouptext);
		textView.setText(getGroup(groupPosition).toString());
        return v;  
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokelist_child, parent, false);
		TextView textView = (TextView) v.findViewById(R.id.childtext);
		textView.setText(getChild(groupPosition, childPosition).toString());
        return v;  
	}

	@Override
	public boolean isChildSelectable(int groupPosition,
			int childPosition) {
		// TODO 自动生成的方法存根
		return true;
	}
}

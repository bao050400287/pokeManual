package com.ouss.pokemanual.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;

public class PokeListCursorTreeAdapter extends CursorTreeAdapter {

	public PokeListCursorTreeAdapter(Cursor cursor, Context context) {
		super(cursor, context);
		// TODO 自动生成的构造函数存根
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	protected View newGroupView(Context context, Cursor cursor,
			boolean isExpanded, ViewGroup parent) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	protected void bindGroupView(View view, Context context, Cursor cursor,
			boolean isExpanded) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	protected View newChildView(Context context, Cursor cursor,
			boolean isLastChild, ViewGroup parent) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	protected void bindChildView(View view, Context context, Cursor cursor,
			boolean isLastChild) {
		// TODO 自动生成的方法存根
		
	}

}

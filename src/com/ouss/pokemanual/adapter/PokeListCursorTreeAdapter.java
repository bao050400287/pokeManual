package com.ouss.pokemanual.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;

public class PokeListCursorTreeAdapter extends CursorTreeAdapter {

	public PokeListCursorTreeAdapter(Cursor cursor, Context context) {
		super(cursor, context);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		// TODO �Զ����ɵķ������
		return null;
	}

	@Override
	protected View newGroupView(Context context, Cursor cursor,
			boolean isExpanded, ViewGroup parent) {
		// TODO �Զ����ɵķ������
		return null;
	}

	@Override
	protected void bindGroupView(View view, Context context, Cursor cursor,
			boolean isExpanded) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	protected View newChildView(Context context, Cursor cursor,
			boolean isLastChild, ViewGroup parent) {
		// TODO �Զ����ɵķ������
		return null;
	}

	@Override
	protected void bindChildView(View view, Context context, Cursor cursor,
			boolean isLastChild) {
		// TODO �Զ����ɵķ������
		
	}

}

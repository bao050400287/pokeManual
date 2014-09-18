package com.ouss.pokemanual.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class PokeProvider extends ContentProvider  {

	private SQLiteDatabase     sqlDB;
    private DatabaseHelper    dbHelper;
    private static final String  DATABASE_NAME = "pokemanual.db";
    private static final int  DATABASE_VERSION= 1;
    private static final String TABLE_NAME= "pokeList";
    private static final String TAG = "PokeProvider";
	
    private static class DatabaseHelper extends SQLiteOpenHelper {
    	DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO 自动生成的方法存根
			db.execSQL("");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO 自动生成的方法存根
			
		}
    }
    
	@Override
	public boolean onCreate() {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String getType(Uri uri) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO 自动生成的方法存根
		return 0;
	}

}

package com.ouss.pokemanual.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class PokeProvider extends ContentProvider  {

	private SQLiteDatabase sqlDB;
    private DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "pokemanual.db";
    private static final int DATABASE_VERSION= 1;
    private static final String TABLE_NAME= "pokeList";
    private static final String TAG = "PokeProvider";
	
    private static class DatabaseHelper extends SQLiteOpenHelper {
    	DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO 自动生成的方法存根
			db.execSQL("create table "+ TABLE_NAME +"(pokeID varchar(4) PRIMARY KEY , cn varchar(60), jp varchar(60), en varchar(60), dx FLOAT, dy FLOAT, dw FLOAT, dh FLOAT, generation INTEGER, url text);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO 自动生成的方法存根
			
		}
    }
    
	@Override
	public boolean onCreate() {
		// TODO 自动生成的方法存根
		dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO 自动生成的方法存根
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        qb.setTables(TABLE_NAME);
        Cursor c = qb.query(db, projection, selection, null, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
	}

	@Override
	public String getType(Uri uri) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentvalues) {
		// TODO 自动生成的方法存根
		sqlDB = dbHelper.getWritableDatabase();
        long rowId = sqlDB.insert(TABLE_NAME, "", contentvalues);
        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(PokeProviderUri.Poke.CONTENT_URI.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
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

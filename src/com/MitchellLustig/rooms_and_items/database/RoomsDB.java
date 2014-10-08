package com.MitchellLustig.rooms_and_items.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RoomsDB extends SQLiteOpenHelper {
	
	public static final class SCHEMA{
		public static final String DATABASE = "roomsanditems";
		public static final class Tables{
			public static final String ROOMS = "rooms";
			public static final class Rooms{
				public static final String WORLD_ID = "worldid";
				public static final String ROOM_X = "roomx";
				public static final String ROOM_Y = "roomy";
				public static final String ROOM_NAME = "roomname";
			}
		}
	}

	public RoomsDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public RoomsDB(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {	
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + SCHEMA.Tables.ROOMS);
	}

}

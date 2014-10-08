package com.MitchellLustig.rooms_and_items.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class RoomsDB extends SQLiteOpenHelper {
	
	
	public static final class Schema{
		public static final String DATABASE = "roomsanditems";
		public static final int VERSION = 1;
		public static final class Tables{
			public static final String ROOMS = "rooms";
			public static final class Rooms implements BaseColumns{
				public static final String WORLD_ID = "worldid";
				public static final String ROOM_X = "roomx";
				public static final String ROOM_Y = "roomy";
				public static final String ROOM_NAME = "roomname";
			}
		}
	}

	public RoomsDB(Context context) {
		super(context, Schema.DATABASE, null, Schema.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {	
		db.execSQL("CREATE TABLE "+Schema.Tables.ROOMS+"("+
			Schema.Tables.Rooms._ID+" INTEGER PRIMARY KEY AUTOINCREMENT "+","+
			Schema.Tables.Rooms.WORLD_ID+" INTEGER"+","+
			Schema.Tables.Rooms.ROOM_X+" INTEGER"+","+
			Schema.Tables.Rooms.ROOM_NAME+" INTEGER"+","+
			Schema.Tables.Rooms.ROOM_NAME+" VARCHAR(64)"+
			")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Schema.Tables.ROOMS);
	}

}

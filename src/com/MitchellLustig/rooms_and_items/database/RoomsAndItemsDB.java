package com.MitchellLustig.rooms_and_items.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class RoomsAndItemsDB extends SQLiteOpenHelper {
	
	
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
			public static final String ITEMS = "items";
			public static final class Items implements BaseColumns{
				public static final String LOCATION = "location";
				public static final String ITEM_NAME = "itemname";
			}
			public static final String USERS = "users";
			public static final class Users implements BaseColumns{
				public static final String LOCATION = "location";
				public static final String USER_NAME = "itemname";
			}
		}
	}

	public RoomsAndItemsDB(Context context) {
		super(context, Schema.DATABASE, null, Schema.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {	
		db.execSQL("CREATE TABLE "+Schema.Tables.ROOMS+"("+
			Schema.Tables.Rooms._ID+" INTEGER PRIMARY KEY AUTOINCREMENT "+","+
			Schema.Tables.Rooms.WORLD_ID+" INTEGER"+","+
			Schema.Tables.Rooms.ROOM_X+" INTEGER"+","+
			Schema.Tables.Rooms.ROOM_Y+" INTEGER"+","+
			Schema.Tables.Rooms.ROOM_NAME+" VARCHAR(64)"+
			")");
		db.execSQL("CREATE TABLE "+Schema.Tables.ITEMS+"("+
				Schema.Tables.Items._ID+" INTEGER PRIMARY KEY AUTOINCREMENT "+","+
				Schema.Tables.Items.LOCATION+" VARCHAR(64)"+","+
				Schema.Tables.Items.ITEM_NAME+" VARCHAR(64)"+
				")");
		db.execSQL("CREATE TABLE "+Schema.Tables.USERS+"("+
				Schema.Tables.Users._ID+" INTEGER PRIMARY KEY AUTOINCREMENT "+","+
				Schema.Tables.Users.LOCATION+" VARCHAR(64)"+","+
				Schema.Tables.Users.USER_NAME+" VARCHAR(64)"+
				")");
		
		//Ugly but easy for now, hopefully future game data will be randomly generated
		ContentValues Room1 = new ContentValues();
		Room1.put(Schema.Tables.Rooms.ROOM_X, 1);
		Room1.put(Schema.Tables.Rooms.ROOM_Y, 1);
		Room1.put(Schema.Tables.Rooms.ROOM_NAME, "Room 1");
		long room1_id = db.insert(Schema.Tables.ROOMS, null, Room1);
		
		ContentValues Room2 = new ContentValues();
		Room2.put(Schema.Tables.Rooms.ROOM_X, 2);
		Room2.put(Schema.Tables.Rooms.ROOM_Y, 1);
		Room2.put(Schema.Tables.Rooms.ROOM_NAME, "Room 2");
		long room2_id = db.insert(Schema.Tables.ROOMS, null, Room2);
		
		ContentValues Room3 = new ContentValues();
		Room3.put(Schema.Tables.Rooms.ROOM_X, 2);
		Room3.put(Schema.Tables.Rooms.ROOM_Y, 2);
		Room3.put(Schema.Tables.Rooms.ROOM_NAME, "Room 3");
		long room3_id = db.insert(Schema.Tables.ROOMS, null, Room3);
		
		ContentValues Item1 = new ContentValues();
		Item1.put(Schema.Tables.Items.LOCATION, createItemLocationRoom(""+room1_id));
		Item1.put(Schema.Tables.Items.ITEM_NAME, "r1i1");
		db.insert(Schema.Tables.ITEMS, null, Item1);
		
		ContentValues Item2 = new ContentValues();
		Item2.put(Schema.Tables.Items.LOCATION, createItemLocationRoom(""+room1_id));
		Item2.put(Schema.Tables.Items.ITEM_NAME, "r1i2");
		db.insert(Schema.Tables.ITEMS, null, Item2);
		
		ContentValues Item3 = new ContentValues();
		Item3.put(Schema.Tables.Items.LOCATION, createItemLocationRoom(""+room3_id));
		Item3.put(Schema.Tables.Items.ITEM_NAME, "aegis");
		db.insert(Schema.Tables.ITEMS, null, Item3);
		
		ContentValues User1 = new ContentValues();
		User1.put(Schema.Tables.Users.LOCATION, room1_id);
		User1.put(Schema.Tables.Users.USER_NAME, "Mitch Lustig");
		db.insert(Schema.Tables.USERS, null, User1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Schema.Tables.ROOMS);
		db.execSQL("DROP TABLE IF EXISTS " + Schema.Tables.ITEMS);
		db.execSQL("DROP TABLE IF EXISTS " + Schema.Tables.USERS);
	}
	
	public String createItemLocationRoom(String roomId){
		return "room_" + roomId;
	}
	
	public String createItemLocationUser(String roomId){
		return "user_" + roomId;
	}
	
	public int getRoomCount(){
		Cursor cursor = getReadableDatabase().query(Schema.Tables.ROOMS, new String[]{"count(*)"}, null, null, null, null, null);
		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex("count(*)"));
	}
	
	public Cursor getRooms(){
		return getReadableDatabase().query(Schema.Tables.ROOMS, //table
			null,//columns, blank for all
			null, //selections 
			null, //selection Args
			null, //group by
			null, //having
			null);//order by	
	}
	
	public Cursor getRoom(String roomId){
		return getReadableDatabase().query(Schema.Tables.ROOMS, //table
			null,//columns, blank for all
			Schema.Tables.Rooms._ID + " = ?", //selections 
			new String[]{roomId}, //selection Args
			null, //group by
			null, //having
			null);//order by	
	}
	
	public Cursor getRoom(int x, int y){
		return getReadableDatabase().query(Schema.Tables.ROOMS, //table
			null,//columns, blank for all
			Schema.Tables.Rooms.ROOM_X + " = ? AND " + Schema.Tables.Rooms.ROOM_Y + " = ?", //selections 
			new String[]{""+x, ""+y}, //selection Args
			null, //group by
			null, //having
			null);//order by	
	}
	
	public Cursor getUsers(){
		return getReadableDatabase().query(Schema.Tables.USERS, //table
			null,//columns, blank for all
			null, //selections 
			null, //selection Args
			null, //group by
			null, //having
			null);//order by	
	}
	
	public int setUserLocation(String user, String roomId){
		ContentValues update = new ContentValues();
		update.put(Schema.Tables.Users.LOCATION, roomId);
		
		return getReadableDatabase().update(Schema.Tables.USERS, //table
			update,//content values
			Schema.Tables.Users._ID + " = ?", //where 
			new String[]{user} //where Args
			);
	}

}

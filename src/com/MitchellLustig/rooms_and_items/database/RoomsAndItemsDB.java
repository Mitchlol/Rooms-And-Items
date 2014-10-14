package com.MitchellLustig.rooms_and_items.database;

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.MitchellLustig.rooms_and_items.game.LevelGenerator;
import com.MitchellLustig.rooms_and_items.game.LevelGenerator.Room;

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
		//will reset everything
		newGame(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//On create will be called, which in turn resets everything.
	}
	public void dropTables(SQLiteDatabase db){
		db.execSQL("DROP TABLE IF EXISTS " + Schema.Tables.ROOMS);
		db.execSQL("DROP TABLE IF EXISTS " + Schema.Tables.ITEMS);
		db.execSQL("DROP TABLE IF EXISTS " + Schema.Tables.USERS);
	}
	public void createTables(SQLiteDatabase db){
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
	}
	
	public void newGame(SQLiteDatabase db){
		if(db == null){
			db = getWritableDatabase();
		}
		//Reset everything
		dropTables(db);
		createTables(db);
		//Generate level
		LevelGenerator mLevelGenerator = new LevelGenerator(5, 5, .5f, .75f, 0.15f);
		Vector<Room> rooms = mLevelGenerator.generate();
		for(Room room: rooms){
			ContentValues RoomCV = new ContentValues();
			RoomCV.put(Schema.Tables.Rooms.ROOM_X, room.getPoint().x);
			RoomCV.put(Schema.Tables.Rooms.ROOM_Y, room.getPoint().y);
			RoomCV.put(Schema.Tables.Rooms.ROOM_NAME, room.getName());
			long roomId = db.insert(Schema.Tables.ROOMS, null, RoomCV);
			for(String itemName: room.getItems()){
				ContentValues Item = new ContentValues();
				Item.put(Schema.Tables.Items.LOCATION, createItemLocationRoom(""+roomId));
				Item.put(Schema.Tables.Items.ITEM_NAME, itemName);
				db.insert(Schema.Tables.ITEMS, null, Item);
			}
		}
		Cursor roomsCursor = getRooms();
		roomsCursor.moveToFirst();
		String firstRoomId = roomsCursor.getString(roomsCursor.getColumnIndex(Schema.Tables.Rooms._ID));
		roomsCursor.close();
		ContentValues User = new ContentValues();
		User.put(Schema.Tables.Users.LOCATION, firstRoomId);
		User.put(Schema.Tables.Users.USER_NAME, "Unknown");
		db.insert(Schema.Tables.USERS, null, User);
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
	
	public int setUserName(String user, String name){
		ContentValues update = new ContentValues();
		update.put(Schema.Tables.Users.USER_NAME, name);
		
		return getReadableDatabase().update(Schema.Tables.USERS, //table
			update,//content values
			Schema.Tables.Users._ID + " = ?", //where 
			new String[]{user} //where Args
			);
	}
	
	public Cursor getItems(){
		return getReadableDatabase().query(Schema.Tables.ITEMS, //table
			null,//columns, blank for all
			null, //selections 
			null, //selection Args
			null, //group by
			null, //having
			null);//order by	
	}
	
	public Cursor getUserItems(String userId){
		return getReadableDatabase().query(Schema.Tables.ITEMS, //table
			null,//columns, blank for all
			Schema.Tables.Items.LOCATION + " = ?", //selections 
			new String[]{createItemLocationUser(userId)}, //selection Args
			null, //group by
			null, //having
			null);//order by	
	}
	
	public Cursor getRoomItems(String roomId){
		return getReadableDatabase().query(Schema.Tables.ITEMS, //table
			null,//columns, blank for all
			Schema.Tables.Items.LOCATION + " = ?", //selections 
			new String[]{createItemLocationRoom(roomId)}, //selection Args
			null, //group by
			null, //having
			null);//order by	
	}
	
	public int setItemUser(String itemId, String userId){
		ContentValues update = new ContentValues();
		update.put(Schema.Tables.Items.LOCATION, createItemLocationUser(userId));
		
		return getReadableDatabase().update(Schema.Tables.ITEMS, //table
			update,//content values
			Schema.Tables.Items._ID + " = ?", //where 
			new String[]{itemId} //where Args
			);
	}
	
	public int setItemRoom(String itemId, String roomId){
		ContentValues update = new ContentValues();
		update.put(Schema.Tables.Items.LOCATION, createItemLocationRoom(roomId));
		
		return getReadableDatabase().update(Schema.Tables.ITEMS, //table
			update,//content values
			Schema.Tables.Items._ID + " = ?", //where 
			new String[]{itemId} //where Args
			);
	}

}

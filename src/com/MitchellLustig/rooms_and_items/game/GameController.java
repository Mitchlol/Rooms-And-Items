package com.MitchellLustig.rooms_and_items.game;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;

import com.MitchellLustig.rooms_and_items.database.RoomsAndItemsDB;
import com.MitchellLustig.rooms_and_items.database.RoomsAndItemsDB.Schema;

public class GameController {
	Context context;
	RoomsAndItemsDB db;
	
	public GameController(Context context) {
		db = new RoomsAndItemsDB(context);
	}
	//Simplified user functions for only one user
	public String getCurrentUserName(){
		Cursor cursor = db.getUsers();
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(Schema.Tables.Users.USER_NAME));
	}
	public Point getCurrentUserLocation(){
		Cursor cursor = db.getUsers();
		cursor.moveToFirst();
		String roomId = cursor.getString(cursor.getColumnIndex(Schema.Tables.Users.LOCATION));
		cursor = db.getRoom(roomId);
		cursor.moveToFirst();
		int roomX = cursor.getInt(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_X));
		int roomY = cursor.getInt(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_Y));
		return new Point(roomX, roomY);
	}
	
	public String getCurrentRoomInfo(){
		Cursor cursor = db.getRooms();
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_NAME));
	}
	
	public void close(){
		db.close();
	}

}

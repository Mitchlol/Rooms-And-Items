package com.MitchellLustig.rooms_and_items.game;

import android.content.Context;
import android.database.Cursor;

import com.MitchellLustig.rooms_and_items.database.RoomsAndItemsDB;
import com.MitchellLustig.rooms_and_items.database.RoomsAndItemsDB.Schema;

public class GameController {
	Context context;
	RoomsAndItemsDB db;
	
	public GameController(Context context) {
		db = new RoomsAndItemsDB(context);
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

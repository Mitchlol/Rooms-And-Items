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
	public String getCurrentUserId(){
		Cursor cursor = db.getUsers();
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(Schema.Tables.Users._ID));
	}
	public String getCurrentUserName(){
		Cursor cursor = db.getUsers();
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(Schema.Tables.Users.USER_NAME));
	}
	public String getCurrentUserLocation(){
		Cursor cursor = db.getUsers();
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(Schema.Tables.Users.LOCATION));
	}
	
	public String getCurrentRoomName(){
		Cursor cursor = db.getRooms();
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_NAME));
	}
	
	public Point getRoomLocation(String roomId){
		Cursor cursor = db.getRoom(roomId);
		cursor.moveToFirst();
		int roomX = cursor.getInt(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_X));
		int roomY = cursor.getInt(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_Y));
		return new Point(roomX, roomY);
	}
	
	public String getRoomUp(String roomId){
		Point room = getRoomLocation(roomId);
		Cursor cursor =  db.getRoom(room.x, room.y+1);
		if(cursor.getCount() == 0){
			return null;
		}else{
			cursor.moveToFirst();
			return cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms._ID));
		}
	}
	public String getRoomDown(String roomId){
		Point room = getRoomLocation(roomId);
		Cursor cursor =  db.getRoom(room.x, room.y-1);
		if(cursor.getCount() == 0){
			return null;
		}else{
			cursor.moveToFirst();
			return cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms._ID));
		}
	}
	public String getRoomRight(String roomId){
		Point room = getRoomLocation(roomId);
		Cursor cursor =  db.getRoom(room.x+1, room.y);
		if(cursor.getCount() == 0){
			return null;
		}else{
			cursor.moveToFirst();
			return cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms._ID));
		}
	}
	public String getRoomLeft(String roomId){
		Point room = getRoomLocation(roomId);
		Cursor cursor =  db.getRoom(room.x-1, room.y);
		if(cursor.getCount() == 0){
			return null;
		}else{
			cursor.moveToFirst();
			return cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms._ID));
		}
	}
	
	public boolean moveUp(){
		String up = getRoomUp(getCurrentUserLocation());
		if(up == null){
			return false;
		}else{
			return db.setUserLocation(getCurrentUserId(), up) > 0 ? true : false;
		}
	}
	public boolean moveDown(){
		String down = getRoomDown(getCurrentUserLocation());
		if(down == null){
			return false;
		}else{
			return db.setUserLocation(getCurrentUserId(), down) > 0 ? true : false;
		}
	}
	public boolean moveRight(){
		String right = getRoomRight(getCurrentUserLocation());
		if(right == null){
			return false;
		}else{
			return db.setUserLocation(getCurrentUserId(), right) > 0 ? true : false;
		}
	}
	public boolean moveLeft(){
		String left = getRoomLeft(getCurrentUserLocation());
		if(left == null){
			return false;
		}else{
			return db.setUserLocation(getCurrentUserId(), left) > 0 ? true : false;
		}
	}
	
	public void close(){
		db.close();
	}

}

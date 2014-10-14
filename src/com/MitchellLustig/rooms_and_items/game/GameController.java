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
		String id = cursor.getString(cursor.getColumnIndex(Schema.Tables.Users._ID));
		cursor.close();
		return id;
	}
	public String getCurrentUserName(){
		Cursor cursor = db.getUsers();
		cursor.moveToFirst();
		String name = cursor.getString(cursor.getColumnIndex(Schema.Tables.Users.USER_NAME)); 
		cursor.close();
		return name;
	}
	public String getCurrentUserRoom(){
		Cursor cursor = db.getUsers();
		cursor.moveToFirst();
		String location = cursor.getString(cursor.getColumnIndex(Schema.Tables.Users.LOCATION)); 
		cursor.close();
		return location;
	}
	public Cursor getCurrentUserItems(){
		return db.getUserItems(getCurrentUserId());
	}
	public int getCurrentUserItemCount(){
		Cursor cursor = getCurrentUserItems();
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	public String getCurrentUserRoomName(){
		return getRoomName(getCurrentUserRoom());
	}
	
	public Cursor getCurrentRoomItems(){
		return db.getRoomItems(getCurrentUserRoom());
	}
	
	public int getCurrentRoomItemCount(){
		Cursor cursor = getCurrentRoomItems();
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	public Cursor getItems(){
		return db.getItems();
	}
	
	public int getItemCount(){
		Cursor cursor = getItems();
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	private Point getRoomPoint(String roomId){
		Cursor cursor = db.getRoom(roomId);
		cursor.moveToFirst();
		int roomX = cursor.getInt(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_X));
		int roomY = cursor.getInt(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_Y));
		cursor.close();
		return new Point(roomX, roomY);
	}
	public Point getUserCurrentLocationPoint(){
		return getRoomPoint(getCurrentUserRoom());
	}
	
	private String getRoomName(String roomId){
		Cursor cursor = db.getRoom(roomId);
		cursor.moveToFirst();
		String name = cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_NAME));
		cursor.close();
		return name;
	}
	
	private String getRoomUp(String roomId){
		Point room = getRoomPoint(roomId);
		Cursor cursor =  db.getRoom(room.x, room.y-1);
		if(cursor.getCount() == 0){
			cursor.close();
			return null;
		}else{
			cursor.moveToFirst();
			String id = cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms._ID));
			cursor.close();
			return id;
		}
	}
	private String getRoomDown(String roomId){
		Point room = getRoomPoint(roomId);
		Cursor cursor =  db.getRoom(room.x, room.y+1);
		if(cursor.getCount() == 0){
			cursor.close();
			return null;
		}else{
			cursor.moveToFirst();
			String id = cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms._ID));
			cursor.close();
			return id;
		}
	}
	private String getRoomRight(String roomId){
		Point room = getRoomPoint(roomId);
		Cursor cursor =  db.getRoom(room.x+1, room.y);
		if(cursor.getCount() == 0){
			cursor.close();
			return null;
		}else{
			cursor.moveToFirst();
			String id = cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms._ID));
			cursor.close();
			return id;
		}
	}
	private String getRoomLeft(String roomId){
		Point room = getRoomPoint(roomId);
		Cursor cursor =  db.getRoom(room.x-1, room.y);
		if(cursor.getCount() == 0){
			cursor.close();
			return null;
		}else{
			cursor.moveToFirst();
			String id = cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms._ID));
			cursor.close();
			return id;
		}
	}
	public String getCurrentUserRoomUp(){
		return getRoomUp(getCurrentUserRoom());
	}
	public String getCurrentUserRoomRight(){
		return getRoomRight(getCurrentUserRoom());
	}
	public String getCurrentUserRoomDown(){
		return getRoomDown(getCurrentUserRoom());
	}
	public String getCurrentUserRoomLeft(){
		return getRoomLeft(getCurrentUserRoom());
	}
	
	public boolean moveCurrentUserUp(){
		String up = getRoomUp(getCurrentUserRoom());
		if(up == null){
			return false;
		}else{
			return db.setUserLocation(getCurrentUserId(), up) > 0 ? true : false;
		}
	}
	public boolean moveCurrentUserDown(){
		String down = getRoomDown(getCurrentUserRoom());
		if(down == null){
			return false;
		}else{
			return db.setUserLocation(getCurrentUserId(), down) > 0 ? true : false;
		}
	}
	public boolean moveCurrentUserRight(){
		String right = getRoomRight(getCurrentUserRoom());
		if(right == null){
			return false;
		}else{
			return db.setUserLocation(getCurrentUserId(), right) > 0 ? true : false;
		}
	}
	public boolean moveCurrentUserLeft(){
		String left = getRoomLeft(getCurrentUserRoom());
		if(left == null){
			return false;
		}else{
			return db.setUserLocation(getCurrentUserId(), left) > 0 ? true : false;
		}
	}
	
	public void pickUpItemCurrentUser(String itemId){
		db.setItemUser(itemId, getCurrentUserId());
	}
	
	public void putDownItemCurrentUser(String itemId){
		db.setItemRoom(itemId, getCurrentUserRoom());
	}
	
	public void newGame(){
		db.newGame(db.getWritableDatabase());
	}
	public void setCurrentUserName(String name){
		db.setUserName(getCurrentUserId(), name);
	}
	
	public void close(){
		db.close();
	}

}

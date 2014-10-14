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
	public String getCurrentUserLocation(){
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
	
	public String getCurrentRoomName(){
		return getRoomName(getCurrentUserLocation());
	}
	
	public Cursor getCurrentRoomItems(){
		return db.getRoomItems(getCurrentUserLocation());
	}
	
	public int getCurrentRoomItemCount(){
		Cursor cursor = getCurrentRoomItems();
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	public Point getRoomLocation(String roomId){
		Cursor cursor = db.getRoom(roomId);
		cursor.moveToFirst();
		int roomX = cursor.getInt(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_X));
		int roomY = cursor.getInt(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_Y));
		cursor.close();
		return new Point(roomX, roomY);
	}
	
	public String getRoomName(String roomId){
		Cursor cursor = db.getRoom(roomId);
		cursor.moveToFirst();
		String name = cursor.getString(cursor.getColumnIndex(Schema.Tables.Rooms.ROOM_NAME));
		cursor.close();
		return name;
	}
	
	public String getRoomUp(String roomId){
		Point room = getRoomLocation(roomId);
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
	public String getRoomDown(String roomId){
		Point room = getRoomLocation(roomId);
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
	public String getRoomRight(String roomId){
		Point room = getRoomLocation(roomId);
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
	public String getRoomLeft(String roomId){
		Point room = getRoomLocation(roomId);
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
	
	public void pickUpItem(String itemId){
		db.setItemUser(itemId, getCurrentUserId());
	}
	
	public void putDownItem(String itemId){
		db.setItemRoom(itemId, getCurrentUserLocation());
	}
	
	public void close(){
		db.close();
	}

}

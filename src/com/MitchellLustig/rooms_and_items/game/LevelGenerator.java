package com.MitchellLustig.rooms_and_items.game;

import java.util.Iterator;
import java.util.Vector;

import android.graphics.Point;

public class LevelGenerator {
	int height, width;
	float roomDeleteChance, itemChance, itemRepeatChance;
	
	final String[] roomNameVar1 = new String[]{"room","den","hall","crypt","dungeon","lair","basement"};
	final String[] roomNameVar2 = new String[]{"shadows","doom","glitter","pain","marshmallows","insanity","mirrors"};
	
	final String[] itemNameVar1 = new String[]{"axe","hammer","sword","book","glove","twig","scrap"};
	final String[] itemNameVar2 = new String[]{"smashing","murder","radience","disobedience","obedience","poking","mythic proportions"};
	
	public class Room{
		Point point;
		String name;
		Vector<String> items;
		public Point getPoint() {
			return point;
		}
		public String getName() {
			return name;
		}
		public Vector<String> getItems() {
			return items;
		}
	}
	
	Vector<Room> rooms;
	
	public LevelGenerator(int height, int width, float roomDeleteChance, float itemChance, float itemRepeatChance) {
		this.height = height;
		this.width = width;
		this.roomDeleteChance = roomDeleteChance;
		this.itemChance = itemChance;
		this.itemRepeatChance = itemRepeatChance;
	}
	
	public Vector<Room> generate(){
		Vector<Point> points = new Vector<Point>();
		//Create all points
		for(int y = 1; y <= width; y++){
			for(int x = 1; x <= height; x++){
				points.add(new Point(x,y));
			}
		}
		//delete random rooms
		Point lastDelete = null;
		for(Iterator<Point> iterator = points.iterator(); iterator.hasNext();){
			Point point = iterator.next();
			//always keep first
			if(point.x == 1 && point.y == 1){
				continue;
			}
			//A row with deletes must be folowed by a full row, so all rooms are accessable
			if(lastDelete != null && point.y == lastDelete.y+1){
				continue;
			}
			if(Math.random() <= roomDeleteChance){
				lastDelete = point;
				iterator.remove();
			}
		}
		
		//create full rooms vector
		rooms = new Vector<Room>();
		for(Point point:  points){
			Room room = new Room();
			room.point = point;
			room.name = getRandomRoomName();
			room.items = new Vector<String>();
			if(Math.random() <= itemChance){
				room.items.add(getRandomItemName());
				while(Math.random() <= itemRepeatChance){
					room.items.add(getRandomItemName());
				}
			}
			rooms.add(room);
		}
		return rooms;
	}
	
	public String getRandomRoomName(){
		return roomNameVar1[(int) Math.floor((Math.random()*7))] + " of " + roomNameVar2[(int) Math.floor((Math.random()*7))]; 
	}
	
	public String getRandomItemName(){
		return itemNameVar1[(int) Math.floor((Math.random()*7))] + " of " + itemNameVar2[(int) Math.floor((Math.random()*7))]; 
	}

}

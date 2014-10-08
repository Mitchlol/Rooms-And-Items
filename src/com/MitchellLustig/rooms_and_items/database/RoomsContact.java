package com.MitchellLustig.rooms_and_items.database;

import android.provider.BaseColumns;

public class RoomsContact {

	public RoomsContact() {}
	
	public static abstract class RoomEntry implements BaseColumns {
		public static final String WORLD_ID = "worldid";
		public static final String ROOM_X = "roomx";
		public static final String ROOM_Y = "roomy";
		public static final String ROOM_NAME = "roomname";
	}

}

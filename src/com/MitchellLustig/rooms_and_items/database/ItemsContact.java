package com.MitchellLustig.rooms_and_items.database;

import android.provider.BaseColumns;

public class ItemsContact {

	public ItemsContact() {}
	
	public static abstract class ItemEntry implements BaseColumns {
		public static final String LOCATION = "location";
		public static final String ITEM_NAME = "itemname";
	}

}

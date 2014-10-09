package com.MitchellLustig.rooms_and_items.game;

import android.content.Context;

import com.MitchellLustig.rooms_and_items.database.RoomsAndItemsDB;

public class GameController {
	Context context;
	RoomsAndItemsDB db;
	
	public GameController(Context context) {
		db = new RoomsAndItemsDB(context);
	}

}

package com.MitchellLustig.rooms_and_items.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.MitchellLustig.rooms_and_items.R;

public class DisplayFragment extends Fragment{
	
	TextView user, inventory, room, items;

	public DisplayFragment(){
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View root = inflater.inflate(R.layout.fragment_display, container, false);
		user = (TextView)root.findViewById(R.id.user);
		inventory = (TextView)root.findViewById(R.id.inventory);
		room = (TextView)root.findViewById(R.id.room);
		items = (TextView)root.findViewById(R.id.items);

		return root;
	}
	
	public void setUser(String userValue){
		user.setText(userValue);
	}
	public void setInventory(String inventoryValue){
		inventory.setText(inventoryValue);
	}
	public void setRoom(String roomValue){
		room.setText(roomValue);
	}
	public void setItems(String itemsValue){
		items.setText(itemsValue);
	}


}

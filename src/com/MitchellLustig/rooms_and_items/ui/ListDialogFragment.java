package com.MitchellLustig.rooms_and_items.ui;

import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.MitchellLustig.rooms_and_items.R;
import com.MitchellLustig.rooms_and_items.database.RoomsAndItemsDB.Schema;

public class ListDialogFragment extends DialogFragment{
	
	ListView list;
	Cursor cursor;

	public ListDialogFragment(Cursor cursor){
		this.cursor = cursor;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View root = inflater.inflate(R.layout.fragment_list, container, false);
		list = (ListView)root.findViewById(R.id.list);
		
		list.setAdapter(new SimpleCursorAdapter(getActivity(), R.layout.fragment_list_item, cursor, new String[]{Schema.Tables.Items.ITEM_NAME}, new int[]{R.id.text}, 0));
		
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
		return root;
	}
}

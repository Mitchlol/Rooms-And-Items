package com.MitchellLustig.rooms_and_items.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.MitchellLustig.rooms_and_items.R;
import com.MitchellLustig.rooms_and_items.database.RoomsAndItemsDB.Schema;
import com.MitchellLustig.rooms_and_items.game.GameController;
import com.MitchellLustig.rooms_and_items.ui.ControllerFragment.ControllerListener;

public class MainActivity extends Activity implements ControllerListener{

	DisplayFragment mDisplayFragment;
	ControllerFragment mControllerFragment;
	GameController mGameController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			mDisplayFragment = new DisplayFragment();
			mControllerFragment = new ControllerFragment();
			getFragmentManager().beginTransaction().add(R.id.display_container, mDisplayFragment).commit();
			getFragmentManager().beginTransaction().add(R.id.controller_container, mControllerFragment).commit();
		}
		
		mGameController = new GameController(this);
	}
	
	protected void updateUI(){
		updateDisplay();
		updateController();
	}
	
	protected void updateDisplay(){
		mDisplayFragment.setUser(mGameController.getCurrentUserName());
		mDisplayFragment.setInventory(""+mGameController.getCurrentUserItemCount());
		mDisplayFragment.setRoom(mGameController.getCurrentRoomName() + " " + mGameController.getRoomLocation(mGameController.getCurrentUserLocation()));
		mDisplayFragment.setItems(""+mGameController.getCurrentRoomItemCount());
	}
	
	protected void updateController(){
		mControllerFragment.setDirectionButtons(
				mGameController.getRoomUp(mGameController.getCurrentUserLocation()) != null, 
				mGameController.getRoomRight(mGameController.getCurrentUserLocation()) != null, 
				mGameController.getRoomDown(mGameController.getCurrentUserLocation()) != null, 
				mGameController.getRoomLeft(mGameController.getCurrentUserLocation()) != null);
		mControllerFragment.setItemButtons(
				mGameController.getCurrentRoomItemCount() > 0,
				mGameController.getCurrentUserItemCount() > 0);
	}

	@Override
	protected void onResume() {
		updateUI();
		super.onResume();
	}




	@Override
	protected void onDestroy() {
		mGameController.close();
		super.onDestroy();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onMoveLeft() {
		mGameController.moveLeft();
		updateUI();
	}
	@Override
	public void onMoveRight() {
		mGameController.moveRight();
		updateUI();
	}
	@Override
	public void onMoveUp() {
		mGameController.moveUp();
		updateUI();
	}
	@Override
	public void onMoveDown() {
		mGameController.moveDown();
		updateUI();
	}
	@Override
	public void onPickItem() {
		final Cursor cursor = mGameController.getCurrentRoomItems();
		new AlertDialog.Builder(this)
		.setTitle(R.string.listdialog_titleroom)
		.setCursor(cursor, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	cursor.moveToPosition(item);
		        mGameController.pickUpItem(cursor.getString(cursor.getColumnIndex(Schema.Tables.Items._ID)));
		        updateUI();
		    }
		}, Schema.Tables.Items.ITEM_NAME)
		.setNegativeButton(R.string.listdialog_cancel, null)
		.create()
		.show();
	}
	@Override
	public void onPlaceItem() {
		final Cursor cursor = mGameController.getCurrentUserItems();
		new AlertDialog.Builder(this)
		.setTitle(R.string.listdialog_titleuser)
		.setCursor(cursor, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	cursor.moveToPosition(item);
		        mGameController.putDownItem(cursor.getString(cursor.getColumnIndex(Schema.Tables.Items._ID)));
		        updateUI();
		    }
		}, Schema.Tables.Items.ITEM_NAME)
		.setNegativeButton(R.string.listdialog_cancel, null)
		.create()
		.show();
	}
}

package com.MitchellLustig.rooms_and_items.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
			getFragmentManager().beginTransaction().add(R.id.display_container, mDisplayFragment, "display").commit();
			getFragmentManager().beginTransaction().add(R.id.controller_container, mControllerFragment, "controller").commit();
		}else{
			mDisplayFragment = (DisplayFragment)getFragmentManager().findFragmentByTag("display");
			mControllerFragment = (ControllerFragment)getFragmentManager().findFragmentByTag("controller");
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
		mDisplayFragment.setRoom(mGameController.getCurrentUserRoomName() + " " + mGameController.getUserCurrentLocationPoint());
		mDisplayFragment.setItems(""+mGameController.getCurrentRoomItemCount());
	}
	
	protected void updateController(){
		mControllerFragment.setDirectionButtons(
				mGameController.getCurrentUserRoomUp() != null, 
				mGameController.getCurrentUserRoomRight() != null, 
				mGameController.getCurrentUserRoomDown() != null, 
				mGameController.getCurrentUserRoomLeft() != null);
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
		mGameController.moveCurrentUserLeft();
		updateUI();
	}
	@Override
	public void onMoveRight() {
		mGameController.moveCurrentUserRight();
		updateUI();
	}
	@Override
	public void onMoveUp() {
		mGameController.moveCurrentUserUp();
		updateUI();
	}
	@Override
	public void onMoveDown() {
		mGameController.moveCurrentUserDown();
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
		        mGameController.pickUpItemCurrentUser(cursor.getString(cursor.getColumnIndex(Schema.Tables.Items._ID)));
		        cursor.close();
		        updateUI();
		    }
		}, Schema.Tables.Items.ITEM_NAME)
		.setNegativeButton(R.string.listdialog_cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				cursor.close();
			}
		})
		.setCancelable(false)
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
		        mGameController.putDownItemCurrentUser(cursor.getString(cursor.getColumnIndex(Schema.Tables.Items._ID)));
		        cursor.close();
		        updateUI();
		    }
		}, Schema.Tables.Items.ITEM_NAME)
		.setNegativeButton(R.string.listdialog_cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				cursor.close();
			}
		})
		.setCancelable(false)
		.create()
		.show();
	}
}

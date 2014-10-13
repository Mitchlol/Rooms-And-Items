package com.MitchellLustig.rooms_and_items.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.MitchellLustig.rooms_and_items.R;
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
	
	protected void updateDisplay(){
		mDisplayFragment.setUser(mGameController.getCurrentUserName());
		mDisplayFragment.setRoom(mGameController.getCurrentRoomName() + " " + mGameController.getRoomLocation(mGameController.getCurrentUserLocation()));
	}

	@Override
	protected void onResume() {
		updateDisplay();
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
		updateDisplay();
	}
	@Override
	public void onMoveRight() {
		mGameController.moveRight();
		updateDisplay();
	}
	@Override
	public void onMoveUp() {
		mGameController.moveUp();
		updateDisplay();
	}
	@Override
	public void onMoveDown() {
		mGameController.moveDown();
		updateDisplay();
	}
	@Override
	public void onPickItem() {
		//Toast.makeText(this, "pick", Toast.LENGTH_SHORT).show();
		Toast.makeText(this, "Current location = "+mGameController.getRoomLocation(mGameController.getCurrentUserLocation()), Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onPlaceItem() {
		//Toast.makeText(this, "put", Toast.LENGTH_SHORT).show();
	}
}

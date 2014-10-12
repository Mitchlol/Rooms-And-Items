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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new ControllerFragment()).commit();
		}
		
		GameController mGameController = new GameController(this);
		Log.i("RoomsAndItems",mGameController.getCurrentRoomInfo());
		mGameController.close();
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
		Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onMoveRight() {
		Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onMoveUp() {
		Toast.makeText(this, "up", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onMoveDown() {
		Toast.makeText(this, "down", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onPickItem() {
		Toast.makeText(this, "pick", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onPlaceItem() {
		Toast.makeText(this, "put", Toast.LENGTH_SHORT).show();
	}
}

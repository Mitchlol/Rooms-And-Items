package com.MitchellLustig.rooms_and_items.ui;

import android.app.Activity;
import android.app.Fragment;

public class ControllerFragment extends Fragment {
	
	public interface ControllerListener{
		public void onMoveLeft();
		public void onMoveRight();
		public void onMoveUp();
		public void onMoveDown();
		public void onPickItem();
		public void onPlaceItem();
	}
	
	private static ControllerListener deadCallback = new ControllerListener(){
		@Override
		public void onMoveLeft() {}
		@Override
		public void onMoveRight() {}
		@Override
		public void onMoveUp() {}
		@Override
		public void onMoveDown() {}
		@Override
		public void onPickItem() {}
		@Override
		public void onPlaceItem() {}
	};
	

	ControllerListener mCallback = deadCallback;

	public ControllerFragment() {
	}
	
	 @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (ControllerListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ControllerListener");
        }
    }
	 @Override
	public void onDetach() {
		super.onDetach();
		mCallback = deadCallback;
	}


}

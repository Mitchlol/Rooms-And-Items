package com.MitchellLustig.rooms_and_items.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.MitchellLustig.rooms_and_items.R;

public class ControllerFragment extends Fragment implements OnClickListener{
	
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
	Button up, left, right, down, pick, put;

	public ControllerFragment(){
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View root = inflater.inflate(R.layout.fragment_controller, container, false);
		up = (Button)root.findViewById(R.id.up);
		left = (Button)root.findViewById(R.id.left);
		right = (Button)root.findViewById(R.id.right);
		down = (Button)root.findViewById(R.id.down);
		pick = (Button)root.findViewById(R.id.pickup);
		put = (Button)root.findViewById(R.id.putdown);
		up.setOnClickListener(this);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		down.setOnClickListener(this);
		pick.setOnClickListener(this);
		put.setOnClickListener(this);
		return root;
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

	@Override
	public void onClick(View view) {
		if(view == up){
			mCallback.onMoveUp();
		}
		if(view == down){
			mCallback.onMoveDown();
		}
		if(view == right){
			mCallback.onMoveRight();
		}
		if(view == left){
			mCallback.onMoveLeft();
		}
		if(view == pick){
			mCallback.onPickItem();
		}
		if(view == put){
			mCallback.onPlaceItem();
		}
	}
	
	public void setDirectionButtons(boolean upEnabled, boolean rightEnabled, boolean downEnabled, boolean leftEnabled){
		up.setEnabled(upEnabled);
		right.setEnabled(rightEnabled);
		down.setEnabled(downEnabled);
		left.setEnabled(leftEnabled);
	}


}

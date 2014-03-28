package com.example.testplab;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

public class CheckboxListener {

	private Context context;	//context from the screen that called this class
	private int id;			//the id of the checkbox that is being lisend to
	private View checkbox;		//the view of the chockbox
	
	public CheckboxListener(Context context, CheckBox checkbox, int id){
		this.context = context;
		this.id = id;
		this.checkbox = checkbox;
		
		checkbox.setOnClickListener(onClickListener);
	}
	
	//onclicklistener for the checkbox
	View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(((CheckBox) checkbox).isChecked()){
				addToArrayList();
			}
			else if(!((CheckBox) checkbox).isChecked()){
				removeFromArrayList();
			}
		}
	};
	
	//add the id from this checkbox to the list
	private void addToArrayList(){
		((IntressesActivity) context).setArrayList(id);
	}
	
	//remove the id from this checkbox from the list
	private void removeFromArrayList(){
		((IntressesActivity) context).removeArrayList(id);
	}
}	

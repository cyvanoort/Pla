package com.example.testplab;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class HomeScreen extends Activity {

	private AnimationDrawable animation;
	   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home_screen);
		
		//create the services
        startService(new Intent(this, UpdateService.class));
        startService(new Intent(this, LocationService.class));
	}

	//show the animation again
	@Override
	public void onResume() {
		super.onResume();
		makeAnimation();
		startAnimation();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}

	//start the main activity on a click
	public void startMainActivity(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	//start the intresses activity on a click
	public void startIntressesActivity(View v) {
		Intent intent = new Intent(this, IntressesActivity.class);
		startActivity(intent);
	}
	
	//start the info activity on a click
	public void startInfoActivity(View v) {
		Intent intent = new Intent(this, InfoActivity.class);
		startActivity(intent);
	}

	//prepare the animation
	private void makeAnimation() {

		animation = new AnimationDrawable();
		animation.addFrame(getResources().getDrawable(R.drawable.logo0), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo10), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo20), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo30), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo40), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo50), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo60), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo70), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo60), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo50), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo40), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo30), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo20), 200);
		animation.addFrame(getResources().getDrawable(R.drawable.logo10), 200);
		
	}
	
	//show the animation
	private void startAnimation(){
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageDrawable(animation);
		
	}
}

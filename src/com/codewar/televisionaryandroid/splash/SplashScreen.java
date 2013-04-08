package com.codewar.televisionaryandroid.splash;

import com.codewar.televisionary_android.R;
import com.codewar.televisionary.TelevisionaryMainView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		// hiding the titile bar
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		// making the activity full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.splash_layout);
		
		new CountDownTimer(3200,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				
			}
			
			@Override
			public void onFinish() {
				Intent i = new Intent(getBaseContext(), TelevisionaryMainView.class);
				startActivity(i);
				finish();
				
			}
			
			
		}.start();
	}

}

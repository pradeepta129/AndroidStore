package com.acme.stripme;

import mobi.vserv.android.ads.VservManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.acme.stripme.R;

public class SplashScreen extends Activity {
	
	private static final String bbZoneId = "806415d1";
//	private static final String bbZoneId = "8063";
//	private static final String bannerZoneId = "20846";
//	private static final String bannerZoneId = "e0a39c34";
	private static VservManager manager = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash );
		 
		 manager = VservManager.getInstance( this );
		 //manager.setTestMode( true );
		 
		 manager.setShowAt("start");
		   
		 manager.displayAd(bbZoneId);

		 ImageView splash = (ImageView) findViewById( R.id.splashView );
		 splash.setImageResource( R.drawable.splash );

		Handler mHandler = new Handler();
		
		mHandler.postDelayed( new Runnable() {
			
			@Override
			public void run() {
				Intent mIntent = new Intent( getBaseContext(), MainActivity.class );
				finish();
				startActivity( mIntent );
			}
		}, 10000);
	}
	


}

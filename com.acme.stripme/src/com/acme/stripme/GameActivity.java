package com.acme.stripme;

import mobi.vserv.android.ads.ViewNotEmptyException;
import mobi.vserv.android.ads.VservController;
import mobi.vserv.android.ads.VservManager;
import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.acme.stripme.R;

public class GameActivity extends Activity {
	private static final String bbZoneId = "806415d1";
	private static final String bannerZoneId = "e0a39c34";
	private static VservManager manager = null;
	private static VservController controller;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_game );

		manager = VservManager.getInstance( this );
		
//		showAd("start");
		 //manager.setTestMode( true );
		 try {
				controller =  manager.renderAd(bannerZoneId, (FrameLayout)findViewById(R.id.controller1));
				
				controller.setRefresh(30);
			} catch (ViewNotEmptyException e) {
				e.printStackTrace();
			}
		
		Bundle mExtra = getIntent().getExtras();
		
		int pos = mExtra.getInt( "position" );
		
		int pre_id = get_resource_from_pos( pos, R.array.PreList );
		int cover_id = get_resource_from_pos( pos, R.array.CoverList );

        TouchView preView = (TouchView) findViewById( R.id.before );
        preView.setBG( pre_id );
        preView.setImageResource( cover_id );
    }
	
	int get_resource_from_pos( int pos, int id )
	{
		TypedArray imgs = getResources().obtainTypedArray( id );
		
		int pre_id = imgs.getResourceId( pos, -1 );
		
		imgs.recycle();
		return pre_id;
	}
	
	public void showAd(String position )
	{
		   
		 manager.setShowAt(position);
		   
		 manager.displayAd(bbZoneId);
	}
	
	public void finish() {
		   
		controller.stopRefresh();
		super.finish();
		   
	}
/*	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	     menu.add("Reset");
	     return true;
	    }
*/
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
/*		 
		 Intent mIntent = getIntent();
		 finish();
		 startActivity( mIntent );
*/	
		 recreate();
		 return true;
	 }
	 
	 @Override
	public void onBackPressed() {
		MainActivity.flag = 0;
		super.onBackPressed();
	}
	
}

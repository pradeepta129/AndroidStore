package com.acme.stripme;

import java.util.ArrayList;

import mobi.vserv.android.ads.ViewNotEmptyException;
import mobi.vserv.android.ads.VservController;
import mobi.vserv.android.ads.VservManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.StackView;
import android.widget.Toast;

import com.acme.stripme.R;

public class MainActivity extends Activity {
	
	public static int flag = 1;
	
	private static final String bbZoneId = "806415d1";
//	private static final String bbZoneId = "8063";
//	private static final String bannerZoneId = "20846";
	private static final String bannerZoneId = "e0a39c34";
	private static VservManager manager = null;
	private static VservController controller;
	
	/** Called when the activity is first created. */
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 setContentView(R.layout.activity_main);
	 StackView stk = (StackView)this.findViewById(R.id.stackView1);
	 
	 manager = VservManager.getInstance( this );
	 //manager.setTestMode( true );
	 /*showAd("in");
	 showAd("end");*/

	 try {
		controller =  manager.renderAd(bannerZoneId, (FrameLayout)findViewById(R.id.controller));
		
		controller.setRefresh(30);
	} catch (ViewNotEmptyException e) {
		e.printStackTrace();
	}
	 
	 ArrayList<StackItem> items = new ArrayList<StackItem>();
	 
	 TypedArray imgs = getResources().obtainTypedArray( R.array.CoverList );
	 for( int inx = 0; inx < imgs.length(); inx++ )
	 {
		 StackItem mStackItem = new StackItem("", this.getResources().getDrawable( imgs.getResourceId( inx, -1 )));
		 items.add( mStackItem );
	 }
	 
	 StackAdapter adapt = new StackAdapter(this, R.layout.item, items);
	 
	 stk.setAdapter(adapt);
	 
	 stk.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			Intent mIntent = new Intent( getBaseContext(), GameActivity.class );
			mIntent.putExtra( "position", position );
			controller.stopRefresh();
			startActivity( mIntent );
		}
	});
	 
	 imgs.recycle();
	 
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void showAd(String position )
	{
		   
		 manager.setShowAt(position);
		   
		 manager.displayAd(bbZoneId);
	}
	public void finish() {
		   
		showAd("end");
		Toast mToast = Toast.makeText( this, "More Pictures Coming Soon!", Toast.LENGTH_LONG);
		mToast.show();
		
		VservManager.release( this );
		super.finish();
		   
		   }
	
	@Override
	protected void onResume() {
		if( flag == 0 )
		{
			showAd( "in" );
			flag = 1;
		}
		super.onResume();
	}
	

	@Override
	 public void onBackPressed() {
	     new AlertDialog.Builder(this)
	            .setMessage("Are you sure you want to exit?")
	            .setCancelable(false)
	            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                     MainActivity.super.onBackPressed();
	                }
	            })
	            .setNegativeButton("No", null)
	            .show();
	 }


}

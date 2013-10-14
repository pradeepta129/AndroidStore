package com.latestsongs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	public static final String _TARGET_URL = "http://www.songspk.pk/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		Button submitbutton = (Button) findViewById( R.id.submit );
		List<Data> mData = null;
		try {
			List<String> htmlString = new Songs().execute( _TARGET_URL ).get();
			Vector<String> inputLines = new Vector<String>();
			for( String inputLine: htmlString )
			{
				if( inputLine.contains( "leftrightslide" ) && inputLine.contains( "='<a href=\"" ))
				{
					inputLines.add( inputLine );
				}
			}
			mData = processList( inputLines );
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		if( mData == null )
			return;
		
		String names[] = new String[mData.size()];
		final Drawable images[] = new Drawable[mData.size()];
		
		for( int inx = 0; inx < mData.size(); inx++ )
		{
			names[inx] = mData.get( inx ).movieName;
		
			try {
				images[inx] = new RetreiveImage().execute(mData.get(inx).ImageUrl.toString()).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		}
		
		//this.setListAdapter(new ArrayAdapter<String>( this, R.layout.main_list_layout, R.id.textName, names ));
		CustomArrayAdapter mAdapter = new CustomArrayAdapter( this, R.layout.main_list_layout, names, images, mData );
		
		setListAdapter( mAdapter );
		CircularListAdapter circularAdapter = new CircularListAdapter((BaseAdapter) mAdapter);

		final ListView mListView = getListView();
		//mListView.setRotation(-90);
		
		mListView.setAdapter(circularAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
		    	 
		    	  	Data mData = (Data) myView.getTag();
		    	  	
		    	  	Intent mIntent = new Intent( MainActivity.this, SongPicker.class );	
		    	  	mIntent.putExtra( "data", mData );
		    	  			    	  
		    	  	startActivity( mIntent );

		        }      
		  });
	}
	
	private static List<Data> processList(Vector<String> leftrightslide) {
		int size = leftrightslide.size();
		ArrayList<Data> songsdata = new ArrayList<Data>( );
		
		for( int inx = 0; inx < size; inx++ )
		{
			String line = leftrightslide.get( inx );
			Data mData = new Data();
			
			mData.movieName = line.substring( line.lastIndexOf( "alt=" ) + 5, line.length() - 16 );
			mData.ImageUrl = line.substring( line.lastIndexOf( "<img src=\"" ) + 10, line.length() - 16 );
			mData.ImageUrl = mData.ImageUrl.substring(0, mData.ImageUrl.indexOf('\"'));
			
			mData.movieUrl = line.substring( line.lastIndexOf( "<a href=\"" ) + 9, line.length() - 16 );
			mData.movieUrl = mData.movieUrl.substring( 0, mData.movieUrl.indexOf('\"'));
			mData.movieUrl = MainActivity._TARGET_URL + mData.movieUrl ;
			
			songsdata.add( mData );
		}
		return songsdata;
	}	

}

class Data implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6195232489233661992L;
	String movieName = null;
	String movieUrl = null;
	String ImageUrl = null;
}

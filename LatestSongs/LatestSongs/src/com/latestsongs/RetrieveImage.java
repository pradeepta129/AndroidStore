package com.latestsongs;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

class RetreiveImage extends AsyncTask<String, Void, Drawable> {


    protected Drawable doInBackground(String ...urls) {
    	Drawable image = null;
        try {
        	InputStream URLcontent;
			try {
				URLcontent = (InputStream) new URL(urls[0]).getContent();
				
				image = Drawable.createFromStream(URLcontent, urls[0]);
			} 
			catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return image;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

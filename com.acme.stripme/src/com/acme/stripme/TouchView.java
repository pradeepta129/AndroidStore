package com.acme.stripme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.view.WindowManager;

public class TouchView extends ImageView {
  private Paint paint;// = new Paint();
  private Path path = new Path();
  
  Context context = null;
  
  float eventX;
  float eventY;

  private Bitmap fillBMP;
  private BitmapShader fillBMPshader;

  public TouchView (Context context, AttributeSet attrs) {
    super(context, attrs);
    
    this.context = context;
  
    paint = new Paint();  
    //paint.setAntiAlias(true);
    paint.setColor(0xFFFFFFFF);  
    paint.setStrokeWidth(20f);
    paint.setStyle(Paint.Style.STROKE); 
    paint.setStrokeJoin(Paint.Join.ROUND);
    paint.setStrokeCap(Cap.ROUND);
    
  }

@Override
  protected void onDraw(Canvas canvas) {
	super.onDraw( canvas );
	
    canvas.drawPath(path, paint);
	//canvas.drawCircle(eventX, eventY, 20, fillPaint);  
    //canvas.drawCircle(eventX, eventY, 20, strokePaint);  
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    eventX = event.getX();
    eventY = event.getY();

    switch (event.getAction()) {
    case MotionEvent.ACTION_DOWN:
      path.moveTo(eventX, eventY);
      return true;
    case MotionEvent.ACTION_MOVE:
      path.lineTo(eventX, eventY);
      break;
    case MotionEvent.ACTION_UP:
      // nothing to do
      break;
    default:
      return false;
    }

    // Schedules a repaint.
    invalidate();
    return true;
  }

  public void setBG(int cover_id) {
	    
	    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	  
	    Point size = new Point();
	    display.getSize(size);
	    int w = size.x;
	    int h = size.y;
	  
	    fillBMP = BitmapFactory.decodeResource(context.getResources(), cover_id);
	    fillBMP = Bitmap.createScaledBitmap(fillBMP, w, h, false);
	    fillBMPshader = new BitmapShader( fillBMP, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

	    paint.setShader(fillBMPshader); 

  }
} 
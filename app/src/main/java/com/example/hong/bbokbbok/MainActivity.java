/*
* 2016.5.29 Hong Gi Wook
* Assignment #4 –Bbok Bbok (Bubble)
*
* – There is a grid space (N * M)
* – Each cell fills with a Bubble
* – Each bubble breaks if the user touches the cell
*
* Design
* - Calculate number of bubble that showed screen
* - Show untouched bubble at screen
* - If screen touched, check which bubble touched and , call invalidate() and then handle the onDraw() callback
* - And check whether out of bubble or in bubble
* - If out of bubble, change bubble image to touched bubble image
*
*
* */


package com.example.hong.bbokbbok;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {
	private Bitmap bobUp;
	private Bitmap bobDown;
	private int windowHeight, windowWidth;
	private int wNum, hNum;
	boolean[][] table;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView vw = new MyView(this);
		setContentView(vw);
	}

	protected class MyView extends View {
		private Paint mPaint;

		public MyView(Context context) {
			super(context);
			init();
		}

		public void init() {
			mPaint = new Paint(); // to describe the colors and styles for the drawing
			Resources res = getResources();
			bobUp = BitmapFactory.decodeResource(res, R.drawable.bubble2);
			bobDown = BitmapFactory.decodeResource(res, R.drawable.bubble);

			DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
			windowHeight = dm.heightPixels;
			windowWidth = dm.widthPixels;
			// calculate number of bubble that showed screen
			wNum = windowWidth / 200 + 1;
			hNum = windowHeight / 200 + 1;

			// initialize
			table = new boolean[wNum][hNum];
			for (int i = 0; i < wNum; i++) {
				for (int j = 0; j < hNum; j++) {
					table[i][j] = true;
				}
			}
		}

		// if invalidate
		protected void onDraw(Canvas canvas) {
			// Actual surface upon which graphics will be drawn
			canvas.drawColor(Color.WHITE);

			for (int i = 0; i < wNum; i++) {
				for (int j = 0; j < hNum; j++) {
					if (table[i][j] == false) { // bubble pushed image
						// About bobDown image, cut (320, 262) size and draw screen (200, 200)
						canvas.drawBitmap(bobDown, new Rect(0, 0, 454, 444),
								new Rect(200 * i, 200 * j, 200 * (i + 1), 200 * (j + 1)), null);
					}
					else { // bubble image
						canvas.drawBitmap(bobUp, new Rect(0, 0, 454, 444),
								new Rect(200 * i, 200 * j, 200 * (i + 1), 200 * (j + 1)), null);
					}
				}
			}
		}

		// If screen touched
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float eventX = event.getX(); // get touch x y coordination
			float eventY = event.getY();
			int x = Math.round(eventX), y = Math.round(eventY); // parse int
			int tX, tY;
			if (event.getAction() == MotionEvent.ACTION_DOWN) { // touch down
				tX = x / 200; // get position
				tY = y / 200;
				if (tX < wNum && tY < hNum) { // exception handling, out of bound
					// get center of a touched circle
					int outLineX = tX * 200 + 100, outLineY = tY * 200 + 100;
					// if circle clicked, change image
					if( (outLineX - x)*(outLineX - x) + (outLineY - y)*(outLineY - y) < 10000 )
						table[tX][tY] = false;
				}
			}
			invalidate(); // redraw
			return true;
		}
	} // MyDrawing
}
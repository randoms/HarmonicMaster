package me.randoms.harmonicmaster;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TestActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		CustomView mView  = new CustomView(this);
		mView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		LinearLayout mLinearLayout = new LinearLayout(this);
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mLinearLayout.addView(mView);
		setContentView(mLinearLayout);
		int[] m = {1,2,3,4,5,6};
		Utils.insert(m, 5, 100);
		Log.d("Randoms",Utils.arrayToString(m));
	}
	
	private class CustomView extends View {
		private Paint mPaint;
		private Paint mEraser;
		private String text;

		public CustomView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			mPaint = new Paint();
			mPaint.setStrokeWidth(3);
			mPaint.setColor(Color.rgb(102, 200, 255));
			mPaint.setTextSize(30);
			
			mEraser = new Paint();
			mEraser.setColor(Color.WHITE);
			
			Log.d("Randoms","Created");
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			if(text != null){
				canvas.drawRect(0, 0, getWidth(), 60, mEraser);
				canvas.drawText(text, 10, 50, mPaint);
			}
		}
		
		public void drawText(String mtext){
			this.text = mtext;
			invalidate();
		}
		
	}
	
	
}

package me.randoms.harmonicmaster.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class floatBtnView extends View{
	
	private int zindex;
	private Paint paintBorder;
	private Paint mPaint;
	private int icon;

	public floatBtnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init(){
		paintBorder = new Paint();
		paintBorder.setAntiAlias(true);
		paintBorder.setShadowLayer(10, 0, 10, Color.rgb(48, 48, 48));
		setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
		paintBorder.setColor(Color.WHITE);
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.WHITE);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
		canvas.drawCircle(50, 50, 28, paintBorder);
	}
	
}

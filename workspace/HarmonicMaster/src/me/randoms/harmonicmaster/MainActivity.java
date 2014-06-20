package me.randoms.harmonicmaster;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	private AudioStream mAudio;
	private ProcessAudioHandler mAudioHandler;
	private static VisualizerView mView;
	private LinearLayout mLinearLayout;
	private static Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 0){
				//new data received
				short[] buffer = (short[])msg.obj;
				mView.updateVisualizer(buffer);
			}
			
		}
	};;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mView  = new VisualizerView(this);
		mView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		mLinearLayout = new LinearLayout(this);
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mLinearLayout.addView(mView);

		
		setContentView(mLinearLayout);
		mAudioHandler = new ProcessAudioHandler(){

			@Override
			public void onProcess(short[] buffer) {
				// TODO Auto-generated method stub
				Message msg = Message.obtain();
				msg.obj = buffer;
				msg.what = 0;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onStop() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		mAudio = new AudioStream(mAudioHandler);
	}
	
	
	
	/**
	 * A simple class that draws waveform data received from a
	 * {@link Visualizer.OnDataCaptureListener#onWaveFormDataCapture }
	 */
	class VisualizerView extends View
	{
		private short[] mBytes;
		private double[] mFFTBytes;
		private double[] FFTImage; // fft image part
		private float[] mPoints;
		private float[] fftPoints;
		private Rect mRect = new Rect();

		private Paint mForePaint = new Paint();
		private Paint mFFTPaint = new Paint();
		private Paint mTextPaint = new Paint();
		private Paint mEraser = new Paint();
		private int mSpectrumNum = 512;
		private Fft mFFT;
		
		
		private String text;
		
		public VisualizerView(Context context)
		{
			super(context);
			init();
		}

		private void init()
		{
			mBytes = null;

			mForePaint.setStrokeWidth(2f);
			mForePaint.setAntiAlias(true);
			mForePaint.setColor(Color.rgb(0, 128, 255));
			
			mFFTPaint.setStrokeWidth(2f);
			mFFTPaint.setAntiAlias(true);
			mFFTPaint.setColor(Color.rgb(255, 102, 255));
			
			mFFT = new Fft(1024);
			mFFTBytes = new double[mSpectrumNum];
			
			mTextPaint.setStrokeWidth(3);
			mTextPaint.setColor(Color.rgb(102, 200, 255));
			mTextPaint.setTextSize(30);
			
			mEraser.setColor(Color.WHITE);
		}

		public void updateVisualizer(short[] fft)
		{
			// byte to double
			double[] fftdata = new double[fft.length];
			FFTImage = new double[fft.length];
			for(int i = 0;i<fft.length;i++){
				fftdata[i] = (double)fft[i];
				FFTImage[i] = 0;
			}
			mFFT.fft(fftdata, FFTImage);
			for(int i=0;i<mSpectrumNum;i++){
				mFFTBytes[i] = Math.hypot(fftdata[i],FFTImage[i]);
			}
			
			mBytes = fft;
			
			// find the top 6 frequency
			int[] topSix = Utils.findPeaks(mFFTBytes);
			String res = "";
			for(int i=0;i<topSix.length;i++){
				res = res + "freq:"+String.valueOf(topSix[i])+"  ";//+"value:"+String.valueOf(fft[topSix[i]])+ " ";
			}
			drawText(res);
			invalidate();
		}


		@Override
		protected void onDraw(Canvas canvas)
		{
			super.onDraw(canvas);

			if (mBytes == null)
			{
				return;
			}

			if (mPoints == null || mPoints.length < mBytes.length * 4)
			{
				mPoints = new float[mBytes.length * 4];
			}
			if(fftPoints ==null || fftPoints.length <mFFTBytes.length*4){
				fftPoints = new float[mFFTBytes.length*4];
			}

			mRect.set(0, 0, getWidth(), getHeight());

			//绘制波形
			for (int i = 0; i < mBytes.length - 1; i++) {
			 mPoints[i * 4] = mRect.width() * i / (mBytes.length - 1);
			 mPoints[i * 4 + 1] = mRect.height() / 2
			 + (short) ((mRect.height() / 2)*mBytes[i]/32767);
			 mPoints[i * 4 + 2] = mRect.width() * (i + 1) / (mBytes.length - 1);
			 mPoints[i * 4 + 3] = mRect.height() / 2
			 + (short) ((mRect.height() / 2)*mBytes[i+1]/32767);
			 }
			canvas.drawLines(mPoints, mForePaint);
			//绘制频谱
			final int baseX = mRect.width()/mSpectrumNum;
			final int height = mRect.height();

			for (int i = 0; i < mSpectrumNum ; i++)
			{
				if (mFFTBytes[i] < 0)
				{
					mFFTBytes[i] = 127;
				}
				
				final int xi = baseX*i + baseX/2;
				
				fftPoints[i * 4] = xi;
				fftPoints[i * 4 + 1] = height;
				
				fftPoints[i * 4 + 2] = xi;
				fftPoints[i * 4 + 3] = height - (float)mFFTBytes[i]/1024;
				//mPoints[i * 4 + 3] = height - 1;
			}
			
			canvas.drawLines(fftPoints,mFFTPaint);
			
			if(text != null){
				canvas.drawRect(0, 0, getWidth(), 60, mEraser);
				canvas.drawText(text, 10, 50, mTextPaint);
			}
		}
		
		public void drawText(String mtext){
			this.text = mtext;
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mAudio.close();
	}
	
	
}
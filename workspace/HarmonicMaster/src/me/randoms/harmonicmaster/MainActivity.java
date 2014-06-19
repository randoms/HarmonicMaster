package me.randoms.harmonicmaster;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	private AudioStream mAudio;
	private ProcessAudioHandler mAudioHandler;
	private VisualizerView mView;
	private LinearLayout mLinearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mView  = new VisualizerView(this);
		mView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				(int) (160 * getResources()
						.getDisplayMetrics().density)));
		mLinearLayout = new LinearLayout(this);
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mLinearLayout.addView(mView);

		setContentView(mLinearLayout);
		mAudioHandler = new ProcessAudioHandler(){

			@Override
			public void onProcess(short[] buffer) {
				// TODO Auto-generated method stub
				Log.d("Data Received",String.valueOf(buffer[0]));
				mView.updateVisualizer(buffer);
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
		mAudio.run();
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
		private Rect mRect = new Rect();

		private Paint mForePaint = new Paint();
		private Paint mFFTPaint = new Paint();
		private int mSpectrumNum = 512;
		private Fft mFFT;
		public VisualizerView(Context context)
		{
			super(context);
			init();
		}

		private void init()
		{
			mBytes = null;

			mForePaint.setStrokeWidth(8f);
			mForePaint.setAntiAlias(true);
			mForePaint.setColor(Color.rgb(0, 128, 255));
			
			mFFTPaint.setStrokeWidth(8f);
			mFFTPaint.setAntiAlias(true);
			mFFTPaint.setColor(Color.rgb(255, 102, 255));
			
			mFFT = new Fft(1024);
			mFFTBytes = new double[mSpectrumNum];
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
			
			/*byte[] model = new byte[fft.length / 2 + 1];

			model[0] = (byte) Math.abs(fft[0]);
			for (int i = 2, j = 1; j < mSpectrumNum;)
			{
				model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
				i += 2;
				j++;
			}*/
			mBytes = fft;
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

			mRect.set(0, 0, getWidth(), getHeight());

			//绘制波形
			/* for (int i = 0; i < mBytes.length - 1; i++) {
			 mPoints[i * 4] = mRect.width() * i / (mBytes.length - 1);
			 mPoints[i * 4 + 1] = mRect.height() / 2
			 + ((byte) (mBytes[i] + 128)) * (mRect.height() / 2) / 128;
			 mPoints[i * 4 + 2] = mRect.width() * (i + 1) / (mBytes.length - 1);
			 mPoints[i * 4 + 3] = mRect.height() / 2
			 + ((byte) (mBytes[i + 1] + 128)) * (mRect.height() / 2) / 128;
			 }
			canvas.drawLines(mPoints, mForePaint);*/
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
				
				mPoints[i * 4] = xi;
				mPoints[i * 4 + 1] = height;
				
				mPoints[i * 4 + 2] = xi;
				mPoints[i * 4 + 3] = height - (float)mFFTBytes[i]/200;
				//mPoints[i * 4 + 3] = height - 1;
			}
			
			canvas.drawLines(mPoints,mFFTPaint);
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mAudio.close();
	}
	
	
}
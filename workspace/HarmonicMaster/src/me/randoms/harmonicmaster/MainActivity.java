package me.randoms.harmonicmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
	private AudioStream mAudio;
	private ProcessAudioHandler mAudioHandler;
	private static SpectrumView mSpectView;
	private static TimeFieldView mTimeView;
	private int insertSound = -1;
	private boolean doRecognize = false;
	private static TextView resText;
	private static TextView statusText;
	
	private static Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 0){
				mSpectView.setData(AudioProcesser.getFFT());
				mTimeView.setData(AudioProcesser.getSoundData());
				statusText.setText(AudioProcesser.getRes());
				resText.setText(String.valueOf(AudioProcesser.getSoundName()));
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent mIntent = getIntent();
		insertSound = mIntent.getIntExtra("insertSound", -1);
		doRecognize = mIntent.getBooleanExtra("doRecognize", false);
		mSpectView = (SpectrumView)findViewById(R.id.spectrumView);
		mTimeView = (TimeFieldView)findViewById(R.id.timeView);
		resText = (TextView)findViewById(R.id.recognize_res);
		statusText = (TextView)findViewById(R.id.sound_status);
		
		mAudioHandler = new ProcessAudioHandler(){

			
			@Override
			public void onProcess(short[] buffer) {
				// TODO Auto-generated method stub
				
				
				// set tasks
				AudioProcesser.setInsertSound(insertSound);
				if(doRecognize)AudioProcesser.beginRecognize();
				
				//begin process
				AudioProcesser.process(buffer);
				if(insertSound != -1 && !AudioProcesser.isStatic()){
					// static over
					Log.d("RandomsRes",Utils.arrayToString(AudioProcesser.soundDb[insertSound]));
					finish();
				}
				Message msg = Message.obtain();
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
	

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mAudio.close();
	}
	
	
}
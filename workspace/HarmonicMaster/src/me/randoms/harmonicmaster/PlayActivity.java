package me.randoms.harmonicmaster;

import me.randoms.harmonicmaster.callback.ProcessAudioHandler;
import me.randoms.harmonicmaster.callback.ProcessCallBack;
import me.randoms.harmonicmaster.json.JSONObject;
import me.randoms.harmonicmaster.utils.Utils;
import me.randoms.harmonicmaster.views.PlaySoundView;
import me.randoms.harmonicmaster.views.SoundResView;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class PlayActivity extends Activity{
	private JSONObject musicJson;
	private static PlaySoundView mView;
	private static SoundResView mResView;
	private static View separator;
	private static PlayActivity that;
	
	private AudioStream mAudio;
	private ProcessAudioHandler mAudioHandler;
	
	// 更新view的handler
	private static Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 0){
				mResView.setSoundName(AudioProcesser.getSoundName()+1);
				if(AudioProcesser.getSoundName() == mView.getCurrnetSound()){
					separator.setBackgroundColor(that.getResources().getColor(R.color.separator_active));
				}else{
					separator.setBackgroundColor(that.getResources().getColor(R.color.separator));
				}
			}
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		that = this;
		musicJson = getMusic();
		setContentView(R.layout.activity_play);
		separator = findViewById(R.id.separator);
		mView = (PlaySoundView)findViewById(R.id.playView);
		mView.setSound(musicJson);
		mView.setCallBack( new ProcessCallBack(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProcess(Class<?> obj) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStop() {
				// TODO Auto-generated method stub
				finish();
			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		mResView = (SoundResView)findViewById(R.id.resView);
		
		// 设置声音处理程序
		mAudioHandler = new ProcessAudioHandler(){

			
			@Override
			public void onProcess(short[] buffer) {
				// TODO Auto-generated method stub
				
				
				// set tasks
				AudioProcesser.beginRecognize();
				
				//begin process
				AudioProcesser.process(buffer);
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
	
	public JSONObject getMusic(){
		JSONObject[] musicList = Utils.getMusicSheets(this);
		String uuid = getIntent().getStringExtra("uuid");
		Log.d("Randoms",uuid);
		for(int i=0;i<musicList.length;i++){
			if(musicList[i].getString("id").equals(uuid)){
				return musicList[i];
			}
		}
		finish(); // no valid music found
		return null;
	}
	
	



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mAudio.close();
	}
	
}

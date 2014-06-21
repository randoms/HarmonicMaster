package me.randoms.harmonicmaster;

import me.randoms.harmonicmaster.adapter.SoundBtnAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class StaticActivity extends Activity{

	private SoundBtnAdapter mBtnAdapter;
	private Button beginRecognizeBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
		GridView mBtnGrid = (GridView)findViewById(R.id.soundBtns);
		mBtnAdapter = new SoundBtnAdapter(this);
		mBtnGrid.setAdapter(mBtnAdapter);
		mBtnGrid.setOnItemClickListener(mBtnAdapter);
		beginRecognizeBtn = (Button)findViewById(R.id.btn_begin_recognize);
		beginRecognizeBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View mView) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(mView.getContext(),MainActivity.class);
				mIntent.putExtra("doRecognize", true);
			}
			
		});
	}
	
	
}

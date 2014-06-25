package me.randoms.harmonicmaster;

import me.randoms.harmonicmaster.views.SpectrumView;
import me.randoms.harmonicmaster.views.TimeFieldView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TestActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SpectrumView mView = (SpectrumView)findViewById(R.id.spectrumView);
		double[] testData = new double[1024];
		for(int i=0;i<1024;i++){
			testData[i] = 100*Math.sin(2*Math.PI/1024*i)+100;
		}
		mView.setData(testData);
		
		TimeFieldView timeView = (TimeFieldView)findViewById(R.id.timeView);
		
		short[] mtestData = new short[1024];
		for(int i=0;i<1024;i++){
			mtestData[i] = (short) (Short.MAX_VALUE*Math.sin(2*Math.PI/1024*i));
		}
		
		timeView.setData(mtestData);
		Log.d("Randoms","Run");
	}
	
	
}

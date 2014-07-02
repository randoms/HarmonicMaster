package me.randoms.harmonicmaster;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

public class TestActivity extends Activity {
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getActionBar();
	    setContentView(R.layout.activity_main_v1);
        actionBar = getActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(10,0,0,0)));
        actionBar.setDisplayShowTitleEnabled(false);

	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }
}

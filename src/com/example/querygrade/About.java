package com.example.querygrade;

import android.app.Activity;
import android.os.Bundle;

/***
 * πÿ”⁄“≥√Ê
 * @author yin
 *
 */
public class About extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		finish();
		super.onDestroy();
	}

}

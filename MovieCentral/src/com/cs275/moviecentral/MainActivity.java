package com.cs275.moviecentral;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
/*
		// Test for Review intent
		Intent intent = new Intent(this, ReviewsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("title", "Toy Story 3");
		//bundle.putString("year", "2010");	// year is not required.
		intent.putExtras(bundle);
	    startActivity(intent);
*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

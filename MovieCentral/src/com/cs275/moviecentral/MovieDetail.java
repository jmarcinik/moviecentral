package com.cs275.moviecentral;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MovieDetail extends Activity 
{
	static String id;
	static ImageView img;
	static TextView movieName;
	static TextView movieYear;
	static EditText desc;
	static RatingBar rating;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);
		
		id = getIntent().getStringExtra("ID");
		
		img = (ImageView)this.findViewById(R.id.imageView1);
		movieName = (TextView)this.findViewById(R.id.textView1);
		movieYear = (TextView)this.findViewById(R.id.textView2);
		desc = (EditText)this.findViewById(R.id.editText1);
		rating = (RatingBar)this.findViewById(R.id.ratingBar1);
		
		GetMovieDetailsTask task = new GetMovieDetailsTask();
		task.execute();
		
		TextView tv = (TextView)this.findViewById(R.id.textView4);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) 
			{
				Intent startReviewsAct = new Intent(arg0.getContext(), ReviewsActivity.class);
				startReviewsAct.putExtra("title", movieName.getText());
				startReviewsAct.putExtra("year", movieYear.getText());
				startActivity(startReviewsAct);
				
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie_detail, menu);
		return true;
	}

}

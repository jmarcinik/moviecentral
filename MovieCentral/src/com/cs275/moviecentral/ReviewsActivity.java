package com.cs275.moviecentral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

/***
 * 
 * Movie Reviews
 *
 */
public class ReviewsActivity extends Activity {
	String movieTitle = null;
	String movieYear = null;
	String rtMovieID = null;
	String rt_apikey = "ncj8nb84848gyzqevjwt46x5";
	String nytimes_apikey = "8FF96A830B2B7F5AB3D372482E378262:16:68916929";

	ExpandableListAdapter listAdapter;
	List<String> listDataSubjects;
	HashMap<String, List<String>> listDataContents;
	List<RottenTomatoesReview> rt_reviews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reviews);

		// Set movie title and year
		Bundle bundle = getIntent().getExtras();
		if(bundle != null && bundle.getString("title") != null) {
			movieTitle = bundle.getString("title");
			movieYear = (bundle.getString("year") != null)?bundle.getString("year"):"0000";
		} else {
			finish();
		}
		
		Log.i("TEST", "title:" + movieTitle + " , year:" + movieYear);
		
		ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
		createListData();
		listAdapter = new ExpandableListAdapter(this, listDataSubjects, listDataContents);
		expListView.setAdapter(listAdapter);
		expListView.expandGroup(0);

		// Click event when one of the reviews is clicked.
		expListView.setOnChildClickListener(new OnChildClickListener()
		{
			@Override
			public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				dialogReviewDetail(movieTitle, rt_reviews.get(childPosition).toString());
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.reviews, menu);
		return true;
	}

	// Prepare review data for the listview
	protected void createListData() {
		listDataSubjects = new ArrayList<String>();
		listDataContents = new HashMap<String, List<String>>();

		listDataSubjects.add(movieTitle);

		List<String> rottenTomatoReviews = new ArrayList<String>();
		List<RottenTomatoesReview> rt_reviews = getReviewFromRottenTomatoes(movieTitle, movieYear);
		for(int i=0; i<rt_reviews.size(); i++) {
			rottenTomatoReviews.add(rt_reviews.get(i).quote);
		}

		listDataContents.put(listDataSubjects.get(0), rottenTomatoReviews);

	}


	// Used for retrieving reviews from RottenTomatoes
	protected List<RottenTomatoesReview> getReviewFromRottenTomatoes(String title, String year) {
		title = title.replace(" ", "+");
		List<RottenTomatoesReview> reviews = new ArrayList<RottenTomatoesReview>();
		String movieURI = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + rt_apikey + "&q=" + title + "&page_limit=3";
		Log.i("TEST", movieURI);
		String stringEntity = null;

		try {
			stringEntity = HttpHelper.getStringEntity(movieURI);
			JSONObject jsonObject = new JSONObject(stringEntity);
			int total = jsonObject.getInt("total");
			Log.i("TEST", "Total movie count: " + total);

			JSONArray movies = jsonObject.getJSONArray("movies");
			JSONObject theMovie = null;

			for(int i=0; i<movies.length(); i++) {
				JSONObject m = movies.getJSONObject(i);
				if(year.equals(m.getString("year")))
					theMovie = m;
			}

			// Take the first one if there is no exact matching found
			if(theMovie == null && total > 0)
				theMovie = movies.getJSONObject(0);

			rtMovieID = theMovie.getString("id");
			movieTitle = theMovie.getString("title");
			movieYear = theMovie.getString("year");

			Log.i("TEST", "ID:" + rtMovieID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("TEST", e.toString());
		}

		String reviewURI = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + rtMovieID + "/reviews.json?apikey=" + rt_apikey;
		Log.i("TEST", reviewURI);
		reviews = getReviewFromRottenTomatoes(reviewURI);
		rt_reviews = reviews;

		return reviews;	
	}

	// Used for retrieving reviews from RottenTomatoes
	private List<RottenTomatoesReview> getReviewFromRottenTomatoes(String reviewURI) {
		List<RottenTomatoesReview> reviewsList = new ArrayList<RottenTomatoesReview>();		
		String stringEntity = null;

		try {
			stringEntity = HttpHelper.getStringEntity(reviewURI);
			JSONObject jsonObject = new JSONObject(stringEntity);

			Log.i("TEST", "Total Review count: " + jsonObject.getInt("total"));

			JSONArray jsonArrayReviews = jsonObject.getJSONArray("reviews");

			for(int i=0; i<jsonArrayReviews.length(); i++) {
				JSONObject jsonReview = jsonArrayReviews.getJSONObject(i);
				RottenTomatoesReview r = new RottenTomatoesReview();

				r.critic = jsonReview.getString("critic");
				r.date = jsonReview.getString("date");
				r.freshness = jsonReview.getString("freshness");
				r.publication = jsonReview.getString("publication");
				r.quote = jsonReview.getString("quote");
				r.links = new String[]{jsonReview.getJSONObject("links").getString("review")};

				reviewsList.add(r);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//String err = (e.getMessage()==null)?"getReviewFromRottenTomatoes2":e.getMessage();
			Log.e("TEST", e.toString());
		}

		return reviewsList;
	}

	// Used for displaying review detail
	public void dialogReviewDetail(String title, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title)
			.setMessage(msg)
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// Do nothing. Just close.
				}
			});
		AlertDialog alert = builder.create();
		alert.show();
	}

}

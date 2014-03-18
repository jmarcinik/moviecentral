package com.cs275.moviecentral;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {

	static ListView movies_list;
	static Editable text;
	static EditText search;
	static TextView seqView;
	static ArrayAdapter<String> adapter;
	static String[] ids;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		search = (EditText) findViewById(R.id.searchbar);
		text = search.getText();
		seqView = (TextView) findViewById(R.id.tester);
		//Button clicker = (Button) findViewById(R.id.search);
		
		ArrayList<String> MovieTitles = new ArrayList<String>();
		
		movies_list = (ListView) findViewById(R.id.movies_list);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MovieTitles);
		
		movies_list.setAdapter(adapter);
		adapter.setNotifyOnChange(true);
		
		movies_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				String id = ids[arg2];
				
				Intent startDetailView = new Intent(arg1.getContext(), MovieDetail.class);
				startDetailView.putExtra("ID", id);
				startActivity(startDetailView);
			}
			
		});
		
		System.out.println("HERE !---------------------------");
		
		
	}
	
	public void searchForMovies(View v){
		
		System.out.print("HERE 2---------------------");
		GetMovies myTask = new GetMovies();
		myTask.execute();
	}

}




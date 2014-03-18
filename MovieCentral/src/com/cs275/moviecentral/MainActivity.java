package com.cs275.moviecentral;
import java.util.ArrayList;

import android.app.Activity;
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
		
		System.out.println("HERE !---------------------------");
		
		
	}
	
	public void searchForMovies(View v){
		
		System.out.print("HERE 2---------------------");
		GetMovies myTask = new GetMovies();
		myTask.execute();
	}

}




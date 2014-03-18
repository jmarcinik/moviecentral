package com.cs275.moviecentral;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import com.google.gson.JsonArray; 
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import android.annotation.SuppressLint;
import android.os.AsyncTask;

@SuppressLint("NewApi")
public class GetMovies extends AsyncTask<Void, Void, Void> {
	
	ArrayList<MovieInfo> result;
	
	@Override
	protected Void doInBackground(Void... params) {
		
		//ArrayList<String> result = new ArrayList<String>();
		
		result = new ArrayList<MovieInfo>();
		String sURL = "http://www.omdbapi.com/?s=" + MainActivity.text.toString();
		
		sURL = sURL.replaceAll(" ", "%20");
		System.out.println("BEFORE INTERNET CONNECTION");
		URL url;
		try {
			url = new URL(sURL);
			HttpURLConnection request;
			request = (HttpURLConnection) url.openConnection();
			request.connect();
			System.out.println("CONNECTION ACHIEVED");
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonArray rootobj = root.getAsJsonObject().get("Search").getAsJsonArray();
			
			for(int i = 0; i < rootobj.size(); i++)
			{
				String title = rootobj.get(i).getAsJsonObject().get("Title").getAsString();
				String id = rootobj.get(i).getAsJsonObject().get("imdbID").getAsString();
								
				MovieInfo info = new MovieInfo(title, id);
				
				result.add(info);
			}
			
			for(int i = 0; i < result.size(); i++)
			{
				System.out.println(result.get(i));
			}
			//titles.equals(result);
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		return null;
	}
	
	protected void onPostExecute(Void param)
	{
		System.out.println("CALLED POST");
	
		String[] ids = new String[result.size()];
		String[] names = new String[result.size()];
		
		for(int i = 0; i < result.size(); i++)
		{
			ids[i] = result.get(i).getID();
			names[i] = result.get(i).getName();
		}
		
		MainActivity.ids = ids;
		
		MainActivity.adapter.clear();
		MainActivity.adapter.addAll(names);
		MainActivity.adapter.notifyDataSetChanged();
		
	}
}

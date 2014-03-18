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
public class GetMovies extends AsyncTask<Void, Void, ArrayList<String>> {
	
	ArrayList<String> result;
	
	@Override
	protected ArrayList<String> doInBackground(Void... params) {
		
		//ArrayList<String> result = new ArrayList<String>();
		
		result = new ArrayList<String>();
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
				
				
				result.add(rootobj.get(i).getAsJsonObject().get("Title").getAsString());
			}
			for(int i = 0; i < result.size(); i++)
		{
			System.out.println(result.get(i));
		}
			//titles.equals(result);
			return result;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		return null;
	}
	
	@Override
	protected void onPostExecute(ArrayList<String> arg0)
	{
		System.out.println("CALLED POST");
	
		MainActivity.adapter.clear();
		MainActivity.adapter.addAll(result);
		MainActivity.adapter.notifyDataSetChanged();
		
	}
}

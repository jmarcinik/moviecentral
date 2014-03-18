package com.cs275.moviecentral;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class GetMovieDetailsTask extends AsyncTask<Void, Void, Void>
{	
	String title, year, desc;
	Bitmap image;
	
	@Override
	protected Void doInBackground(Void... arg0) 
	{
		
		String imdbID = MovieDetail.id;
		
		String sUrl = "http://www.omdbapi.com/?i=" + imdbID + "&t=";
		
		try
		{
			URL url = new URL(sUrl);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			
			JsonObject rootObj = root.getAsJsonObject();
			title = rootObj.get("Title").getAsString();
			year = rootObj.get("Year").getAsString();
			desc = rootObj.get("Plot").getAsString();
			
			String img = rootObj.get("Poster").getAsString();
			URL imgUrl = new URL(img);
			image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		
			request.disconnect();
		}
		catch(Exception ex)
		{
			ex.printStackTrace(System.out);
		}
		
		
		return null;
	}
	
	protected void onPostExecute(Void param)
	{
		MovieDetail.movieName.setText(title);
		MovieDetail.movieYear.setText(year);
		MovieDetail.desc.setText(desc);
		MovieDetail.img.setImageBitmap(image);
	}

}

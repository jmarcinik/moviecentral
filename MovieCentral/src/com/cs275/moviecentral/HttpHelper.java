package com.cs275.moviecentral;

import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class HttpHelper {

	// Returns HttpEntity for the uri
	public static HttpEntity getHttpEntity(String uri) {
		HttpEntity httpEntity = null;
		AsyncTask<String,Integer,HttpEntity> asyncTask = new HttpTask().execute(uri);

			
			try {
				httpEntity = asyncTask.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		return httpEntity;
	}
	
	// Returns stringEntity for the uri
	public static String getStringEntity(String uri) {
		String entityString = null;
//		 httpEntity = null;
		AsyncTask<String,Integer,String> asyncTask = new HttpTaskString().execute(uri);
		try
		{
//			HttpEntity httpEntity = asyncTask.get();
//			entityString = EntityUtils.toString(httpEntity);
			entityString = asyncTask.get();
		}
		catch(Exception e){
			Log.e("TEST", e.toString());
//			e.printStackTrace();
		}

		return entityString;
	}

	// Used for executing http requests.
	private static class HttpTask extends AsyncTask<String,Integer,HttpEntity> {
		protected HttpEntity doInBackground(String... params) {
			HttpEntity entity = null;
			try
			{
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet method = new HttpGet(params[0].replace(" ", "%20"));
				HttpResponse response = httpclient.execute(method);
				entity = response.getEntity();
			}
			catch(Exception e){
				e.printStackTrace();
			}

			return entity;
		}
	}
	
	private static class HttpTaskString extends AsyncTask<String,Integer,String> {
		protected String doInBackground(String... params) {
			String entityString = null;
			try
			{
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet method = new HttpGet(params[0].replace(" ", "%20"));
				HttpResponse response = httpclient.execute(method);
				//entity = response.getEntity();
				HttpEntity httpEntity = response.getEntity();
				entityString = EntityUtils.toString(httpEntity);
			}
			catch(Exception e){
				e.printStackTrace();
			}

			return entityString;
		}
	}
}

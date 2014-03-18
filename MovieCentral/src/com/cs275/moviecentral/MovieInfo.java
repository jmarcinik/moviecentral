package com.cs275.moviecentral;

public class MovieInfo
{
	private String name, imdbID;
	
	public MovieInfo(String name, String imdbID)
	{
		this.name = name;
		this.imdbID = imdbID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getID()
	{
		return imdbID;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}

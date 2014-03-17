package com.cs275.moviecentral;

public class RottenTomatoesReview {

	public String critic;
	public String date;
	public String freshness;
	public String publication;
	public String quote;
	public String[] links;
	
	public String toString() {
		return "Critic: " + critic + "\n"
				+ "Date: " + date + "\n"
				+ "publication: " + publication + "\n\n"
				+ quote + "\n";
	}
}

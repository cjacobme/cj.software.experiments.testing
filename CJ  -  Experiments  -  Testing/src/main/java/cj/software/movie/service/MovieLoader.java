package cj.software.movie.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cj.software.movie.entity.Movie;

public class MovieLoader
{

	public Movie searchByMovieId(long pMovieId) throws FileNotFoundException, IOException
	{
		String lFileName = "Movie" + pMovieId + ".txt";
		Movie lResult = this.searchByFileName(lFileName);
		return lResult;
	}

	private Movie searchByFileName(String pFileName) throws FileNotFoundException, IOException
	{
		try (FileReader lFR = new FileReader(pFileName))
		{
			try (BufferedReader lBR = new BufferedReader(lFR))
			{
				Movie lResult = this.parse(lBR);
				return lResult;
			}
		}
	}

	private Movie parse(BufferedReader pBR) throws IOException
	{
		String lIdStr = this.parseLine(pBR, "Id:");
		Long lId = Long.parseLong(lIdStr);
		String lTitle = this.parseLine(pBR, "Title:");
		String lDirector = this.parseLine(pBR, "Director:");
		Movie lResult = new Movie();
		lResult.setId(lId);
		lResult.setDirector(lDirector);
		lResult.setTitle(lTitle);
		return lResult;
	}

	private String parseLine(BufferedReader pBR, String pIntro) throws IOException
	{
		String lLine = pBR.readLine();
		String lResult = lLine.substring(0, pIntro.length());
		return lResult;
	}

}

package cj.software.movie.entity;

public class MovieSetup
{
	public static final long MOVIE_ID = 4711L;

	public static Movie createMovie(boolean pWithId)
	{
		Movie lResult = new Movie();
		lResult.setTitle("Das Boot");
		lResult.setDirector("Wolfgang Petersen");
		lResult.setReleaseYear(1981);
		if (pWithId)
		{
			lResult.setId(MOVIE_ID);
		}
		return lResult;

	}
}

package cj.software.movie.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

@Entity
@SequenceGenerator(name = "seqMovie", sequenceName = "seqMovie")
public class Movie
{
	@Id
	@GeneratedValue(generator = "seqMovie")
	private Long id;

	@Version
	private int version;

	private String title;

	private int releaseYear;

	private String director;

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String pTitle)
	{
		this.title = pTitle;
	}

	public int getReleaseYear()
	{
		return this.releaseYear;
	}

	public void setReleaseYear(int pReleaseYear)
	{
		this.releaseYear = pReleaseYear;
	}

	public String getDirector()
	{
		return this.director;
	}

	public void setDirector(String pDirector)
	{
		this.director = pDirector;
	}

	public void setId(Long pId)
	{
		this.id = pId;
	}

	public Long getId()
	{
		return this.id;
	}
}

package trabalho4;

import java.io.Serializable;

public class Pub implements Serializable
{
	
	
	
	//constructor
	private String title,journal;
	private String[] authors;
	private Integer year,volume,page,nmb_citations,DOI;
	//constructor 
	public Pub(String title, int year, String[] authors, String journal,int volume, int page,int nmb_citations,int DOI)
	{
		this.title = title;     //email----- chave primaria
		this.year = year;
		this.authors= authors;
		this.journal= journal;
		this.page = page;
		this.volume = volume;
		this.nmb_citations = nmb_citations;
		this.DOI = DOI;
		
	}
	
	
	public String getTitle()
	{
		return title;
	}
	public String[] getAuthors()
	{
		return authors;
	}
	public String getJournal()

	{
		return journal;
	}
	public int getVolume()
	{
		return volume;
	}
	public int getPage()
	{
		return page;
	}
	public int getCitations()
	{
		return nmb_citations;
	}
	public int getDOI()
	{
		return DOI;
	}
	public int getYear()
	{
		return year;
	}
	
	
	public void setTitle(String title )
	{
		this.title = title;
	}
	public void setAuthors(String[] authors )
	{
		this.authors = authors;
	}
	public void setJournal(String journal )
	{
		this.journal = journal;
	}
	public void setPage(int page )
	{
		this.page = page;
	}
	public void setDOI(int DOI )
	{
		this.DOI = DOI;
	}
	public void setCitations(int nmb_citations )
	{
		this.nmb_citations = nmb_citations;
	}
	public void setVolume(int volume )
	{
		this.volume = volume;
	}
	public void setYear(int year )
	{
		this.year = year;
	}
	
	
}
	
package it.whoteach.scraper.dto;

import java.util.List;

import it.whoteach.scraper.pojo.Author;
import it.whoteach.scraper.pojo.Description;
import it.whoteach.scraper.pojo.DestinationPublic;
import it.whoteach.scraper.pojo.Difficulty;
import it.whoteach.scraper.pojo.Domain;
import it.whoteach.scraper.pojo.Duration;
import it.whoteach.scraper.pojo.Format;
import it.whoteach.scraper.pojo.Keyword;
import it.whoteach.scraper.pojo.Language;
import it.whoteach.scraper.pojo.MaxAge;
import it.whoteach.scraper.pojo.MinAge;
import it.whoteach.scraper.pojo.Subdomain;
import it.whoteach.scraper.pojo.Subsubdomain;
import it.whoteach.scraper.pojo.Title;
import it.whoteach.scraper.pojo.Type;
import it.whoteach.scraper.pojo.UploadDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto {
	private Long id;
	private String url;
	private String source;
	
	public List<Author> authors;
	public List<DestinationPublic> destinationPublic;
	public List<Subdomain> subdomain;
	public List<Keyword> keywords;
	public Duration duration;
	public Difficulty difficulty;
	public Domain domain;
	public Subsubdomain subsubdomain;
	public MaxAge maxAge;
	public MinAge minAge;
	public UploadDate uploadDate;
	public Language language;
	public Description description;
	public Type type;
	public Title title;
	public Format format;
		
}

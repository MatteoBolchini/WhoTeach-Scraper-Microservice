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

public class ArticleDTO {
	private String idItem;
	private String url;
	private String source;
	
	public List<AuthorDTO> authors;
	public List<DestinationPublicDTO> destinationPublic;
	public List<KeywordDTO> keywords;
	public DurationDTO duration;
	public DifficultyDTO difficulty;
	public DomainDTO domain;
	public SubdomainDTO subdomain;
	public SubsubdomainDTO subsubdomain;
	public MaxAgeDTO maxAge;
	public MinAgeDTO minAge;
	public UploadDateDTO uploadDate;
	public LanguageDTO language;
	public DescriptionDTO description;
	public TypeDTO type;
	public TitleDTO title;
	public FormatDTO format;
		
}

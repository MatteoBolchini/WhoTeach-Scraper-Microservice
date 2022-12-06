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
	
	public List<AuthorDto> authors;
	public List<DestinationPublicDto> destinationPublic;
	public List<SubdomainDto> subdomain;
	public List<KeywordDto> keywords;
	public DurationDto duration;
	public DifficultyDto difficulty;
	public DomainDto domain;
	public SubsubdomainDto subsubdomain;
	public MaxAgeDto maxAge;
	public MinAgeDto minAge;
	public UploadDateDto uploadDate;
	public LanguageDto language;
	public DescriptionDto description;
	public TypeDto type;
	public TitleDto title;
	public FormatDto format;
		
}

package it.whoteach.scraper.dto;

import java.util.List;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;

import it.whoteach.scraper.csvConverter.TextToAuthorDto;
import it.whoteach.scraper.csvConverter.TextToDestinationPublicDto;
import it.whoteach.scraper.csvConverter.TextToKeywordDto;
import it.whoteach.scraper.csvConverter.TextToSubdomainDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto {
	
	private Long id;
	@CsvBindByName(column = "URI")
	private String url;
	@CsvBindByName(column = "source")
	private String source;
	
	@CsvBindAndSplitByName(column = "authors", elementType = AuthorDto.class, splitOn = ",", converter = TextToAuthorDto.class)
	public List<AuthorDto> authors;
	@CsvBindAndSplitByName(column = "destination_public", elementType = DestinationPublicDto.class, splitOn = ",", converter = TextToDestinationPublicDto.class)
	public List<DestinationPublicDto> destinationPublic;
	@CsvBindAndSplitByName(column = "level_1", elementType = SubdomainDto.class, splitOn = ",", converter = TextToSubdomainDto.class)
	public List<SubdomainDto> subdomain;
	@CsvBindAndSplitByName(column = "keywords", elementType = KeywordDto.class, splitOn = ",", converter = TextToKeywordDto.class)
	public List<KeywordDto> keywords;
	@CsvRecurse
	public DurationDto duration;
	@CsvRecurse
	public DifficultyDto difficulty;
	@CsvRecurse
	public DomainDto domain;
	@CsvRecurse
	public SubsubdomainDto subsubdomain;
	@CsvRecurse
	public MaxAgeDto maxAge;
	@CsvRecurse
	public MinAgeDto minAge;
	@CsvRecurse
	public UploadDateDto uploadDate;
	@CsvRecurse
	public LanguageDto language;
	@CsvRecurse
	public DescriptionDto description;
	@CsvRecurse
	public TypeDto type;
	@CsvRecurse
	public TitleDto title;
	@CsvRecurse
	public FormatDto format;
		
}

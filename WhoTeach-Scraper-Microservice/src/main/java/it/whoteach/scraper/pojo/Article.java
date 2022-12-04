package it.whoteach.scraper.pojo;

import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Node
@Getter
@Setter
public class Article {
	@Id
	private String idItem;
	private String url;
	private String source;
	
	@Relationship(type = "HAS_AUTHOR")
	public List<Author> authors;
	
	@Relationship(type = "HAS_DURATION")
	public Duration duration;
	
	@Relationship(type = "HAS_DIFFICULTY")
	public Difficulty difficulty;
	
	@Relationship(type = "HAS_DOMAIN")
	public Domain domain;
	
	@Relationship(type = "HAS_SUBDOMAIN")
	public List<Subdomain> subdomain;
	
	@Relationship(type = "HAS_SUBSUBDOMAIN")
	public Subsubdomain subsubdomain;
	
	@Relationship(type = "HAS_MAX_AGE")
	public MaxAge maxAge;
	
	@Relationship(type = "HAS_MIN_AGE")
	public MinAge minAge;
	
	@Relationship(type = "HAS_DESTINATION_PUBLIC")
	public List<DestinationPublic> destinationPublic;
	
	@Relationship(type = "HAS_UPLOAD_DATE")
	public UploadDate uploadDate;
	
	@Relationship(type = "HAS_LANGUAGE")
	public Language language;
	
	@Relationship(type = "HAS_DESCRIPTION")
	public Description description;
	
	@Relationship(type = "HAS_TYPE")
	public Type type;
	
	@Relationship(type = "HAS_TITLE")
	public Title title;
	
	@Relationship(type = "HAS_FORMAT")
	public Format format;
	
	@Relationship(type = "HAS_KEYWORDS")
	public List<Keyword> keywords;
	
	public Article(String idItem, String url, String source) {
		this.idItem = idItem;
		this.url = url;
		this.source = source;
	}
	
}

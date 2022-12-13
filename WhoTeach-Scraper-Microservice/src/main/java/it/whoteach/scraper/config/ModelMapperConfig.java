package it.whoteach.scraper.config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.dto.AuthorDto;
import it.whoteach.scraper.dto.DescriptionDto;
import it.whoteach.scraper.dto.DestinationPublicDto;
import it.whoteach.scraper.dto.DifficultyDto;
import it.whoteach.scraper.dto.DomainDto;
import it.whoteach.scraper.dto.DurationDto;
import it.whoteach.scraper.dto.FormatDto;
import it.whoteach.scraper.dto.KeywordDto;
import it.whoteach.scraper.dto.LanguageDto;
import it.whoteach.scraper.dto.MaxAgeDto;
import it.whoteach.scraper.dto.MinAgeDto;
import it.whoteach.scraper.dto.SubdomainDto;
import it.whoteach.scraper.dto.SubsubdomainDto;
import it.whoteach.scraper.dto.TitleDto;
import it.whoteach.scraper.dto.TypeDto;
import it.whoteach.scraper.dto.UploadDateDto;
import it.whoteach.scraper.exception.RequiredFieldNullException;
import it.whoteach.scraper.pojo.Article;
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
import it.whoteach.scraper.service.ArticleService;
import lombok.extern.java.Log;

@Log
@Configuration
public class ModelMapperConfig {

	@Autowired
	ArticleService articleService;

	private ModelMapper modelMapper = new ModelMapper();

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(articleConverterDto, ArticleDto.class, Article.class);
		modelMapper.addConverter(authorConverterDto, AuthorDto.class, Author.class);
		modelMapper.addConverter(descriptionConverterDto, DescriptionDto.class, Description.class);
		modelMapper.addConverter(destinationPublicConverterDto, DestinationPublicDto.class, DestinationPublic.class);
		modelMapper.addConverter(difficultyConverterDto, DifficultyDto.class, Difficulty.class);
		modelMapper.addConverter(domainConverterDto, DomainDto.class, Domain.class);
		modelMapper.addConverter(durationConverterDto, DurationDto.class, Duration.class);
		modelMapper.addConverter(formatConverterDto, FormatDto.class, Format.class);
		modelMapper.addConverter(keywordConverterDto, KeywordDto.class, Keyword.class);
		modelMapper.addConverter(languageConverterDto, LanguageDto.class, Language.class);
		modelMapper.addConverter(maxAgeConverterDto, MaxAgeDto.class, MaxAge.class);
		modelMapper.addConverter(minAgeConverterDto, MinAgeDto.class, MinAge.class);
		modelMapper.addConverter(subdomainConverterDto, SubdomainDto.class, Subdomain.class);
		modelMapper.addConverter(subsubdomainConverterDto, SubsubdomainDto.class, Subsubdomain.class);
		modelMapper.addConverter(titleConverterDto, TitleDto.class, Title.class);
		modelMapper.addConverter(typeConverterDto, TypeDto.class, Type.class);
		modelMapper.addConverter(uploadDateConverterDto, UploadDateDto.class, UploadDate.class);
		return modelMapper;
	}

	private Converter<ArticleDto, Article> articleConverterDto = context -> {
		Article article;
		if(context.getSource().getId() != null) { 
			article = articleService.getById(context.getSource().getId());
		}
		else {
			if(context.getSource().getUrl() == null || context.getSource().getSource() == null)
				throw new RequiredFieldNullException("Url and Source are mandatory");
			article = new Article();
		}
		if(context.getSource().getUrl() != null) {
			Pattern p = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
			Matcher m = p.matcher(context.getSource().getUrl());
			if(m.matches())
				article.setUrl(context.getSource().getUrl());
			else {
				throw new RequiredFieldNullException("Url is mandatory");
			}
		}
		if(context.getSource().getSource() != null) { 
			article.setSource(context.getSource().getSource());
		}

		// Dto to pojo
		if(context.getSource().getAuthors() != null) { 
			List<Author> list1 = new ArrayList<>();
			for(AuthorDto a : context.getSource().getAuthors()) {
				if(!a.getName().isEmpty()) 							
					list1.add(modelMapper.map(a, Author.class));
			}
			article.setAuthors(list1);
		}

		if(context.getSource().getDestinationPublic() != null) { 
			List<DestinationPublic> list2 = new ArrayList<>();
			for(DestinationPublicDto d : context.getSource().getDestinationPublic()) {
				if(!d.getDestinationPublic().isEmpty())
					list2.add(modelMapper.map(d, DestinationPublic.class));
			}
			article.setDestinationPublic(list2);
		}

		if(context.getSource().getSubdomain() != null) { 
			List<Subdomain> list3 = new ArrayList<>();
			for(SubdomainDto s : context.getSource().getSubdomain()) {
				if(!s.getSubdomain().isEmpty())
					list3.add(modelMapper.map(s, Subdomain.class));
			}
			article.setSubdomain(list3);
		}
		if(context.getSource().getKeywords() != null) { 
			List<Keyword> list4 = new ArrayList<>();
			for(KeywordDto k : context.getSource().getKeywords()) {
				if(!k.getKeywords().isEmpty())
					list4.add(modelMapper.map(k, Keyword.class));
			}
			article.setKeywords(list4);
		}

		if(context.getSource().getDuration() != null 
				&& context.getSource().getDuration().getDuration() != null
				&& !context.getSource().getDuration().getDuration().isEmpty()) { 
			Pattern p1 = Pattern.compile("[0-9]{1,2}-[0-9]{2,3}");
			Pattern p2 = Pattern.compile("[0-9]{3}");
			Matcher m1 = p1.matcher(context.getSource().getDuration().getDuration());
			Matcher m2 = p2.matcher(context.getSource().getDuration().getDuration());
			if(m1.matches() || m2.matches())
				article.setDuration(modelMapper.map(context.getSource().getDuration(), Duration.class));
			else {
				log.log(Level.INFO, "In article " + context.getSource().getId() 
						+ " Duration was invalid, it was considered null");
			}
		}

		if(context.getSource().getDifficulty() != null 
				&& context.getSource().getDifficulty().getDifficulty() != null
				&& !context.getSource().getDifficulty().getDifficulty().isEmpty()) { 
			article.setDifficulty(modelMapper.map(context.getSource().getDifficulty(), Difficulty.class));
		}

		if(context.getSource().getDomain() != null 
				&& context.getSource().getDomain().getDomain() != null
				&& !context.getSource().getDomain().getDomain().isEmpty()) { 
			article.setDomain(modelMapper.map(context.getSource().getDomain(), Domain.class));
		}

		if(context.getSource().getSubsubdomain() != null 
				&& context.getSource().getSubsubdomain().getSubsubdomain() != null 
				&& !context.getSource().getSubsubdomain().getSubsubdomain().isEmpty()) { 
			article.setSubsubdomain(modelMapper.map(context.getSource().getSubsubdomain(), Subsubdomain.class));
		}

		if(context.getSource().getMaxAge() != null
				&& context.getSource().getMaxAge().getMaxAge() != null
				&& !context.getSource().getMaxAge().getMaxAge().isEmpty()) { 
			Pattern p = Pattern.compile("[0-9]{1,3}");
			Matcher m = p.matcher(context.getSource().getMaxAge().getMaxAge());
			if(m.matches())
				article.setMaxAge(modelMapper.map(context.getSource().getMaxAge(), MaxAge.class));
			else { // TODO scriverlo meglio
				log.log(Level.INFO, "In article " + context.getSource().getId() 
						+ " MaxAge was invalid, it was considered null");
			}
		}

		if(context.getSource().getMinAge() != null 
				&& context.getSource().getMinAge().getMinAge() != null
				&& !context.getSource().getMinAge().getMinAge().isEmpty()) { 
			Pattern p = Pattern.compile("[0-9]{1,3}");
			Matcher m = p.matcher(context.getSource().getMinAge().getMinAge());
			if(m.matches())
				article.setMinAge(modelMapper.map(context.getSource().getMinAge(), MinAge.class));
			else {
				log.log(Level.INFO, "In article " + context.getSource().getId() 
						+ " MinAge was invalid, it was considered null");
			}
		}

		if(context.getSource().getUploadDate() != null 
				&& context.getSource().getUploadDate().getUploadDate() != null
				&& !context.getSource().getUploadDate().getUploadDate().isEmpty()) { 
			// oppure \\d{2}/\\d{2}/\\d{4}
			Pattern p = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{4}");
			Matcher m = p.matcher(context.getSource().getUploadDate().getUploadDate());
			if(m.matches()) 
				article.setUploadDate(modelMapper.map(context.getSource().getUploadDate(), UploadDate.class));
			else {
				log.log(Level.INFO, "In article " + context.getSource().getId() 
						+ " UploadDate was invalid, it was considered null");
			}
		}

		if(context.getSource().getLanguage() != null 
				&& context.getSource().getLanguage().getLanguage() != null
				&& !context.getSource().getLanguage().getLanguage().isEmpty()) { 
			article.setLanguage(modelMapper.map(context.getSource().getLanguage(), Language.class));
		}

		if(context.getSource().getDescription() != null 
				&& context.getSource().getDescription().getDescription() != null
				&& !context.getSource().getDescription().getDescription().isEmpty()) { 
			article.setDescription(modelMapper.map(context.getSource().getDescription(), Description.class));
		}

		if(context.getSource().getType() != null 
				&& context.getSource().getType().getType() != null
				&& !context.getSource().getType().getType().isEmpty()) { 
			article.setType(modelMapper.map(context.getSource().getType(), Type.class));
		}

		if(context.getSource().getTitle() != null 
				&& context.getSource().getTitle().getTitle() != null
				&& !context.getSource().getTitle().getTitle().isEmpty()) { 
			article.setTitle(modelMapper.map(context.getSource().getTitle(), Title.class));
		}

		if(context.getSource().getFormat() != null 
				&& context.getSource().getFormat().getFormat() != null
				&& !context.getSource().getFormat().getFormat().isEmpty()) { 
			article.setFormat(modelMapper.map(context.getSource().getFormat(), Format.class));
		}
		
		return article;
	};

	private Converter<AuthorDto, Author> authorConverterDto = context -> {
		Author author = new Author(context.getSource().getName());
		return author;
	};

	private Converter<DescriptionDto, Description> descriptionConverterDto = context -> {
		Description description = new Description(context.getSource().getDescription());
		return description;
	};
	
	private Converter<DestinationPublicDto, DestinationPublic> destinationPublicConverterDto = context -> {
		DestinationPublic cestinationPublic = new DestinationPublic(context.getSource().getDestinationPublic());
		return cestinationPublic;
	};

	private Converter<DifficultyDto, Difficulty> difficultyConverterDto = context -> {
		Difficulty difficulty = new Difficulty(context.getSource().getDifficulty());
		return difficulty;
	};

	private Converter<DomainDto, Domain> domainConverterDto = context -> {
		Domain domain = new Domain(context.getSource().getDomain());
		return domain;
	};
	
	private Converter<DurationDto, Duration> durationConverterDto = context -> {
		Duration duration = new Duration(context.getSource().getDuration());
		return duration;
	};

	private Converter<FormatDto, Format> formatConverterDto = context -> {
		Format format = new Format(context.getSource().getFormat());
		return format;
	};
	
	private Converter<KeywordDto, Keyword> keywordConverterDto = context -> {
		Keyword keyword = new Keyword(context.getSource().getKeywords());
		return keyword;
	};

	private Converter<LanguageDto, Language> languageConverterDto = context -> {
		Language language = new Language(context.getSource().getLanguage());
		return language;
	};
	
	private Converter<MaxAgeDto, MaxAge> maxAgeConverterDto = context -> {
		MaxAge maxAge = new MaxAge(context.getSource().getMaxAge());
		return maxAge;
	};
	
	private Converter<MinAgeDto, MinAge> minAgeConverterDto = context -> {
		MinAge minAge = new MinAge(context.getSource().getMinAge());
		return minAge;
	};

	private Converter<SubdomainDto, Subdomain> subdomainConverterDto = context -> {
		Subdomain subdomain = new Subdomain(context.getSource().getSubdomain());
		return subdomain;
	};

	private Converter<SubsubdomainDto, Subsubdomain> subsubdomainConverterDto = context -> {
		Subsubdomain subsubdomain = new Subsubdomain(context.getSource().getSubsubdomain());
		return subsubdomain;
	};

	private Converter<TitleDto, Title> titleConverterDto = context -> {
		Title title = new Title(context.getSource().getTitle());
		return title;
	};

	private Converter<TypeDto, Type> typeConverterDto = context -> {
		Type type = new Type(context.getSource().getType());
		return type;
	};

	private Converter<UploadDateDto, UploadDate> uploadDateConverterDto = context -> {
		UploadDate uploadDate = new UploadDate(context.getSource().getUploadDate());
		return uploadDate;
	};
	
}

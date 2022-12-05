package it.whoteach.scraper.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.Converter;
import it.whoteach.scraper.dto.ArticleDTO;
import it.whoteach.scraper.exception.RequiredFieldNullException;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.service.ArticleService;

@Configuration
public class ModelMapperConfig {
	
	@Autowired
	ArticleService articleService;
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(articleConverterDtE, ArticleDTO.class, Article.class);
		return modelMapper;
	}
	
	private Converter<ArticleDTO, Article> articleConverterDtE = context -> {
		Article article;
		if(context.getSource().getId() != null) 
			article = articleService.findById(context.getSource().getId());
		else {
			if(context.getSource().getUrl() == null || context.getSource().getSource() == null)
				throw new RequiredFieldNullException("Url and Source are mandatory");
			article = new Article();
		}
		if(context.getSource().getUrl() != null) { 
			article.setUrl(context.getSource().getUrl());
		}
		if(context.getSource().getSource() != null) { 
			article.setSource(context.getSource().getSource());
		}
		//TODO pojo to dto
		if(context.getSource().getAuthors() != null) { 
			article.setAuthors(context.getSource().getAuthors());
		}
		if(context.getSource().getDestinationPublic() != null) { 
			article.setDestinationPublic(context.getSource().getDestinationPublic());
		}
		if(context.getSource().getSubdomain() != null) { 
			article.setSubdomain(context.getSource().getSubdomain());
		}
		if(context.getSource().getKeywords() != null) { 
			article.setKeywords(context.getSource().getKeywords());
		}
		if(context.getSource().getDuration().getDuration() != null) { 
			article.setDuration(context.getSource().getDuration());
		}
		if(context.getSource().getDifficulty().getDifficulty() != null) { 
			article.setDifficulty(context.getSource().getDifficulty());
		}
		if(context.getSource().getDomain().getDomain() != null) { 
			article.setDomain(context.getSource().getDomain());
		}
		if(context.getSource().getSubsubdomain().getSubsubdomain() != null) { 
			article.setSubsubdomain(context.getSource().getSubsubdomain());
		}
		if(context.getSource().getMaxAge().getMaxAge() != null) { 
			article.setMaxAge(context.getSource().getMaxAge());
		}
		if(context.getSource().getMinAge().getMinAge() != null) { 
			article.setMinAge(context.getSource().getMinAge());
		}
		if(context.getSource().getUploadDate().getUploadDate() != null) { 
			article.setUploadDate(context.getSource().getUploadDate());
		}
		if(context.getSource().getLanguage().getLanguage() != null) { 
			article.setLanguage(context.getSource().getLanguage());
		}
		if(context.getSource().getDescription().getDescription() != null) { 
			article.setDescription(context.getSource().getDescription());
		}
		if(context.getSource().getType().getType() != null) { 
			article.setType(context.getSource().getType());
		}
		if(context.getSource().getTitle().getTitle() != null) { 
			article.setTitle(context.getSource().getTitle());
		}
		if(context.getSource().getFormat().getFormat() != null) { 
			article.setFormat(context.getSource().getFormat());
		}
		return article;
	};


}

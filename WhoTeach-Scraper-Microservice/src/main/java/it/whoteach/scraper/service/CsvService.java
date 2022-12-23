package it.whoteach.scraper.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;

import it.whoteach.scraper.connector.GoogleCloudConnector;
import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;
import lombok.extern.java.Log;

@Log
@Service
public class CsvService {

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	GoogleCloudConnector googleCloudConnector;

	public List<Long> postFromBucket(String fileName) {
		List<Long> ids = new ArrayList<>();
		
		for(ArticleDto a : googleCloudConnector.retrieveCsv(fileName)) {
			if(articleRepository.existsByUrl(a.getUrl()))
				log.log(Level.INFO, String.format("Exist an Article with this url yet: ", a.getUrl()));
			else {
				Article art = this.modelMapper.map(a, Article.class); 
				if(art != null)
					ids.add(art.getId());		
			}
		}
		
		return ids;
	}

	public List<Long> putFromBucket(String fileName) {
		List<Long> ids = new ArrayList<>();
		
		for(ArticleDto a : googleCloudConnector.retrieveCsv(fileName)) {
			Article art = this.modelMapper.map(a, Article.class);
			if(art != null)
				ids.add(art.getId());		
		}
		
		return ids;
	}
	
	public void postFromBucketLocal(String fileName) throws IllegalStateException, FileNotFoundException {
		for(ArticleDto a : localToList(fileName)) {
			this.modelMapper.map(a, Article.class);
		}
	}
	
	public List<ArticleDto> localToList(String fileName) throws IllegalStateException, FileNotFoundException {	
		List<ArticleDto> articles = new CsvToBeanBuilder<ArticleDto>(new FileReader("src/main/resources/" + fileName))
				.withSeparator(';')
				.withIgnoreQuotations(false)
				.withType(ArticleDto.class)
				.build()
				.parse();
		return articles;
	}

}

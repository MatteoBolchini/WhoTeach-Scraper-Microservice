package it.whoteach.scraper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.whoteach.scraper.connector.GoogleCloudConnector;
import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.pojo.ResponsePost;
import it.whoteach.scraper.pojo.ResponsePut;
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
	
	// POST
	public ResponsePost postFromBucket(String fileName) {
		List<Long> ids = new ArrayList<>();
		List<ArticleDto> articles = googleCloudConnector.retrieveCsv(fileName);
		int c = 0;
		for(ArticleDto a : articles) {
			if(articleRepository.existsByUrl(a.getUrl())) {
				c++;
				log.log(Level.SEVERE, String.format("Cannot POST the Article, his url exists yet [%s]", 
						a.getUrl()));
			}
			else {
				try {
					ids.add(this.modelMapper.map(a, Article.class).getId());
				} catch (Exception e) {
					c++;
					log.log(Level.WARNING, String.format("Cannot POST the Article, his url is invalid or null [%s]", 
							a.getUrl()));
				}	
			}
		}
		
		return new ResponsePost(ids, articles.size(), c);
	}

	// PUT	
	public ResponsePut putFromBucket(String fileName) {
		List<Long> ids = new ArrayList<>();
		List<ArticleDto> articles = googleCloudConnector.retrieveCsv(fileName);
		int c = 0;
		for(ArticleDto a : articles) {
			try {
				ids.add(this.modelMapper.map(a, Article.class).getId());
			} catch (Exception e) {
				c++;
				log.log(Level.WARNING, String.format("Cannot POST the Article, his url is invalid or null [%s]", 
						a.getUrl()));
			}		
		}
		
		return new ResponsePut(ids, articles.size(), c);
	}
	
}

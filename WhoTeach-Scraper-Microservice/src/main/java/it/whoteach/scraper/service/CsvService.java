package it.whoteach.scraper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

	public ResponseEntity<List<Long>> postFromBucket(String fileName) {
		if(fileName == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		int invalidArticles = 0;
		List<Long> ids = new ArrayList<>();
		
		for(ArticleDto a : googleCloudConnector.retrieveCsv(fileName)) {
			if(articleRepository.existsByUrl(a.getUrl()))
				log.log(Level.INFO, String.format("Exist an Article with this url yet: ", a.getUrl()));
			else {
				var art = this.modelMapper.map(a, Article.class); 
				if(art != null)
					ids.add(art.getId());		
				else {
					invalidArticles++;
				}
			}
		}
		log.log(Level.INFO, "Total invalid articles: " + invalidArticles);
		
		return new ResponseEntity<>(ids, HttpStatus.OK);
	}

	public ResponseEntity<List<Long>> putFromBucket(String fileName) {
		if(fileName == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		int invalidArticles = 0;
		List<Long> ids = new ArrayList<>();
		
		for(ArticleDto a : googleCloudConnector.retrieveCsv(fileName)) {
			var art = this.modelMapper.map(a, Article.class);
			if(art != null)
				ids.add(art.getId());		
			else {
				invalidArticles++;
			}
		}
		log.log(Level.INFO, "Total invalid articles: " + invalidArticles);
		
		return new ResponseEntity<>(ids, HttpStatus.OK);
	}

}

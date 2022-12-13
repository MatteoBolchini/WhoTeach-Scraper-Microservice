package it.whoteach.scraper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;
import lombok.extern.java.Log;

@Log
@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	
	public Article getById(Long id) {
		return articleRepository.getById(id);
	}
	
	public ResponseEntity<Article> findById(Long id) {
		if(articleRepository.existsById(id))
			return new ResponseEntity<Article>(getById(id), HttpStatus.OK);
		else {
			return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<List<Article>> findAllById(List<Long> ids) {
		List<Article> list = new ArrayList<>();
		for(Long id : ids) {
			if(articleRepository.existsById(id))
				list.add(getById(id));		
			else {
				log.log(Level.INFO, String.format("Article with id [%s] not found", id));
			}
		}
		return new ResponseEntity<List<Article>>(list, HttpStatus.OK);
	}

	public ResponseEntity<List<Article>> findAll() {
		return new ResponseEntity<List<Article>>(articleRepository.findAll(), HttpStatus.OK);	
	}

	public Article save(Article article) {
		articleRepository.save(article);
		deleteAlone();
		return article;
	}

	public List<Article> saveAll(List<Article> articles) {
		return articleRepository.saveAll(articles);
	}

	public Article update(Article article) {
		return save(article);
	}

	public ResponseEntity<Long> deleteById(Long id) {
		articleRepository.deleteById(id);
		deleteAlone();
		return new ResponseEntity<Long>(id, HttpStatus.OK);

	}

	public ResponseEntity<Void> deleteAlone() {
		articleRepository.deleteAlone();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> clearDatabase() {
		articleRepository.deleteAll();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Long> delete(Article article) {
		Long id = article.getId();
		articleRepository.delete(article);
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}

}

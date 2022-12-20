package it.whoteach.scraper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;
import lombok.extern.java.Log;

@Log
@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	@Lazy
	private ModelMapper modelMapper;

	public Article getById(Long id) {
		return articleRepository.getById(id);
	}

	public Article getByUrl(String url) {
		return articleRepository.getByUrl(url);
	}

	public boolean existsByUrl(String url) {
		return articleRepository.existsByUrl(url);
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

	public ResponseEntity<Long> deleteById(Long id) {
		articleRepository.deleteById(id);
		deleteAlone();
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteAlone() {
		articleRepository.deleteAlone();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<List<Long>> newArticles(List<ArticleDto> articles) {
		int invalidArticles = 0;
		List<Long> ids = new ArrayList<>();
		for(ArticleDto a : articles) {
			var id = newArticle(a);
			if(id != null)
				ids.add(id.getBody());
			else {
				invalidArticles++;
			}
		}
		log.log(Level.INFO, "Total invalid articles: " + invalidArticles);
		
		return new ResponseEntity<List<Long>>(ids, HttpStatus.OK);
	}

	public ResponseEntity<Long> newArticle(ArticleDto article) {
		if(existsByUrl(article.getUrl())) {
			log.info(String.format("Exist an Article with this url yet: ", article.getUrl()));
			return null;
		}
		else {
			Article a = this.modelMapper.map(article, Article.class);
			if(a == null) {
				return null;
			}
			return new ResponseEntity<Long>(a.getId(),
					HttpStatus.OK);
		}
	}

	public ResponseEntity<List<Long>> updateAll(List<ArticleDto> articles) {
		int invalidArticles = 0;
		List<Long> ids = new ArrayList<>();
		for(ArticleDto a : articles) {
			var id = update(a);
			if(id != null)
				ids.add(id.getBody());
			else {
				invalidArticles++;
			}
		}	
		log.log(Level.INFO, "Total invalid articles: " + invalidArticles);

		return new ResponseEntity<List<Long>>(ids, HttpStatus.OK);
	}

	public ResponseEntity<Long> update(ArticleDto article) {
		var a = this.modelMapper.map(article, Article.class);
		if(a == null) {
			return null;
		}
		
		return new ResponseEntity<Long>(a.getId(), HttpStatus.OK);
	}

}

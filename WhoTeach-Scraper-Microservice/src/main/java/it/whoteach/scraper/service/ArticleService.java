package it.whoteach.scraper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.whoteach.scraper.exception.EntityNotFoundException;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	
	public Article findById(Long id) {
		return articleRepository.findById(id).orElseThrow(()-> 
		new EntityNotFoundException(String.format("Article with id [%s] not found", id)));
	}
	
	public List<Article> findAllById(List<Long> ids) {
		return articleRepository.findAllById(ids);
	}
	
	public List<Article> findAll() {
		return articleRepository.findAll();
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

	public void deleteById(Long id) {
		articleRepository.deleteById(id);
		deleteAlone();
	}

	public void deleteAlone() {
		articleRepository.deleteAlone();
	}

	public void clearDatabase() {
		articleRepository.deleteAll();
	}

	public void delete(Article article) {
		articleRepository.delete(article);
	}
}

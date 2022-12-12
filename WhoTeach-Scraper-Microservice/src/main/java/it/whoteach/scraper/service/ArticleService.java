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

	public List<Article> findAllById(List<Long> ids) {
		return articleRepository.findAllById(ids);
	}

	public Article save(Article article) {
		return articleRepository.save(article);
	}

	public List<Article> saveAll(List<Article> articles) {
		return articleRepository.saveAll(articles);
	}

	public Article update(Article article) {
		return save(article);
	}

	public List<Article> findAll() {
		return articleRepository.findAll();
	}

	public Article findById(Long id) {
		return articleRepository.findById(id).orElseThrow(()-> 
		new EntityNotFoundException(String.format("article with id [%s] not found", id)));
	}

	public void clearById(Long id) {
		articleRepository.clearById(id);
		deleteAlone();
	}

	public void deleteAlone() {
		articleRepository.deleteAlone();
	}

	public void clearDatabase() {
		articleRepository.deleteAll();
	}
}

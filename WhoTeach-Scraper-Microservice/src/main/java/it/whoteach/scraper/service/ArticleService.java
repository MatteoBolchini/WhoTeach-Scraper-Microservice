package it.whoteach.scraper.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.whoteach.scraper.dto.ArticleDTO;
import it.whoteach.scraper.exception.EntityNotFoundException;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;

	public List<Article> allByIdItem(Long[] ids) {
		List<Article> list = new ArrayList<>();
		for(Long id : ids) {
			list.add(findById(id));
		}
		return list;
	}

	public Article save(Article article) {
		articleRepository.save(article);
		return article;
	}

	public List<Article> saveAll(ModelMapper modelMapper,List<ArticleDTO> articles) {
		List<Article> list = new ArrayList<>();
		for(ArticleDTO a : articles) {
			list.add(save(modelMapper.map(a, Article.class)));
		}
		return list;
	}

	public Article update(Article article) {
		return articleRepository.save(article);
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
		clearAlone();
	}

	public void clearAlone() {
		articleRepository.clearAlone();
	}

	public void clearDatabase() {
		articleRepository.clearDatabase();
	}
}

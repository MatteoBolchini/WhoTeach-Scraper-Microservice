package it.whoteach.scraper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	public boolean existsByUrl(String url) {
		return articleRepository.existsByUrl(url);
	}
	
	public void deleteAlone() {
		articleRepository.deleteAlone();
	}
	
	// GET
	public Article findById(Long id) {
		return articleRepository.findById(id).get();
	}

	public Article getByUrl(String url) {
		return articleRepository.getByUrl(url);
	}

	// si può cambiare in maniera tale che richiami findById e nel ritorno ci siano segnati come null, gli articoli non trovati
	public List<Article> findAllById(List<Long> ids) {
		List<Article> list = new ArrayList<>();
		for(Long id : ids) {
			try {
				list.add(findById(id));
			} catch (Exception e) {
				log.log(Level.WARNING, String.format("Article with id [%s] not found", id));
			}
		}
		return list;
	}

	public List<Article> findAll() {
		return articleRepository.findAll();	
	}

	// POST
	public Article save(Article article) {
		articleRepository.save(article);
		deleteAlone();
		return article;
	}

	public List<Article> saveAll(List<Article> articles) {
		return articleRepository.saveAll(articles);
	}

	// POST
	public Long newArticle(ArticleDto article) {
		if(existsByUrl(article.getUrl())) {
			log.info(String.format("Exist an Article with this url yet: ", article.getUrl()));
			return null;
		}
		else {
			Article a = this.modelMapper.map(article, Article.class);
			if(a == null) {
				return null;
			}
			return a.getId();
		}
	}
	
	public List<Long> newArticles(List<ArticleDto> articles) {
		List<Long> ids = new ArrayList<>();
		for(ArticleDto a : articles) {
			Long id = newArticle(a);
			if(id != null)
				ids.add(id);
		}
		
		return ids;
	}

	// UPDATE
	public Long update(ArticleDto article) {
		Article a = this.modelMapper.map(article, Article.class);
		if(a == null) {
			return null;
		}
		
		return a.getId();
	}
	
	public List<Long> updateAll(List<ArticleDto> articles) {
		List<Long> ids = new ArrayList<>();
		for(ArticleDto a : articles) {
			Long id = update(a);
			if(id != null)
				ids.add(id);
		}	

		return ids;
	}
	
	// DELETE
	public Long deleteById(Long id) {
		articleRepository.deleteById(id);
		deleteAlone();
		return id;
	}
	
	public List<Long> deleteAllBydId(List<Long> ids) {
		List<Long> list = new ArrayList<>();
		for(Long id : ids) {
			deleteById(id);
		}
		return list;
	}		

}

package it.whoteach.scraper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.exception.BadRequestException;
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
			throw new BadRequestException(String.format("Cannot POST the Article, his url exists yet [%s]", 
					article.getUrl()));
		}
		
		else {
			try {
				return this.modelMapper.map(article, Article.class).getId();
			} catch (Exception e) {
				throw new BadRequestException(String.format("Cannot POST the Article, his url is invalid or null [%s]", 
						article.getUrl()));
			}
		}
	}
	
	public List<Long> newArticles(List<ArticleDto> articles) {
		List<Long> ids = new ArrayList<>();
		for(ArticleDto a : articles) {
			if(existsByUrl(a.getUrl())) {
				log.log(Level.SEVERE, String.format("Cannot POST the Article, his url exists yet [%s]", 
						a.getUrl()));
			}
			
			else {
				try {
					ids.add(this.modelMapper.map(a, Article.class).getId());
				} catch (Exception e) {
					log.log(Level.WARNING, String.format("Cannot POST the Article, his url is invalid or null [%s]", 
							a.getUrl()));
				}
			}	
		}
		
		return ids;
	}

	// UPDATE
	public Long update(ArticleDto article) {
		try {
			return this.modelMapper.map(article, Article.class).getId();
		} catch (Exception e) {
			throw new BadRequestException(String.format("Cannot PUT the Article, his url is invalid or null [%s]", 
					article.getUrl()));
		}
	}
	
	public List<Long> updateAll(List<ArticleDto> articles) {
		List<Long> ids = new ArrayList<>();
		for(ArticleDto a : articles) {
			try {
				ids.add(this.modelMapper.map(a, Article.class).getId());
			} catch (Exception e) {
				log.log(Level.WARNING, String.format("Cannot PUT the Article, his url is invalid or null [%s]", 
						a.getUrl()));
			}
		}	

		return ids;
	}
	
	// DELETE
	public Long deleteById(Long id) {
		Article a = findById(id);
		try {
			articleRepository.delete(a);
			deleteAlone();
			return id;
		} catch (Exception e) {
			throw new BadRequestException(String.format("There is no Article found with given id [%s]", id));
		}
	}
	
	public List<Long> deleteAllBydId(List<Long> ids) {
		List<Long> list = new ArrayList<>();
		for(Long id : ids) {
			try {
				deleteById(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}		

}

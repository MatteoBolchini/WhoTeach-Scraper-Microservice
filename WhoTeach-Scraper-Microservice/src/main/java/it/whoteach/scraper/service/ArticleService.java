package it.whoteach.scraper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;

@Service
public class ArticleService {
	
	public List<Article> allByIdItem(ArticleRepository articleRepository, String[] idItems) {
		List<Article> list = new ArrayList<>();
		for(String idItem : idItems) {
			list.add(articleRepository.findByIdItem(idItem));
		}
		return list;
	}
	
	public Article save(ArticleRepository articleRepository, Article article) {
		try {
			if(article.getSource() == null || article.getUrl() == null || article.getIdItem() == null )
				return null;
		}
		catch (Exception e) {
			System.out.println("L'articolo è null");
			return null;
		}
		articleRepository.save(article);
		return article;
	}

	public List<Article> saveAll(ArticleRepository articleRepository, List<Article> articles) {
		List<Article> list = new ArrayList<>();
		for(Article a : articles) {
			try {
				if(a.getSource() != null && a.getUrl() != null && a.getIdItem() != null)
					list.add(a);
			} 
			catch (Exception e) {
				System.out.println("L'articolo è null");
			}
		}
		articleRepository.saveAll(list);
		return list;
	}
	
	public Article update(Article article, Article newArticle, ArticleRepository articleRepository) {
		return null;
	}
}

package it.whoteach.scraper;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;
import it.whoteach.scraper.service.ArticleService;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
	
	@InjectMocks
	private ArticleService articleService;
	
	@Mock 
	ArticleRepository articleRepository;
	
	private Long id = 123456789L;
	
	@Test
	public void findAllArticles() {
		articleService.findAll();
		verify(articleRepository).findAll();
	}
	
	@Test
	public void getArticleById() {
		articleService.getById(id);
		verify(articleRepository).getById(id);
	}
	
	@Test
	public void saveArticle() {
		Article article = new Article();
		article.setUrl("https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html");
		article.setSource("Indian Java");
		article.setId(id);
		
		articleService.save(article);
		verify(articleRepository).save(article);
	}
	
	@Test
	public void deleteArticleById() {
		articleService.deleteById(id);
		verify(articleRepository).deleteById(id);
	}
	
}

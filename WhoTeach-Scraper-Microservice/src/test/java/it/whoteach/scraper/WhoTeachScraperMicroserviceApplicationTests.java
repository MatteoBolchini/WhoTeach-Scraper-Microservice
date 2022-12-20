package it.whoteach.scraper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;
import it.whoteach.scraper.service.ArticleService;

@SpringBootTest
class WhoTeachScraperMicroserviceApplicationTests {
	/*
	@Autowired
	ArticleService articleService;
	
	@MockBean
	ArticleRepository articleRepository;
	
	@Test
	public void testPostArticle() {
		Article article = new Article();
		article.setUrl("https://www.merlot.org/merlot/viewMaterial.htm?id=123456");
		article.setSource("Merlot");
		
		Mockito.when(articleService.save(article)).thenReturn(article);
		
	}*/

}

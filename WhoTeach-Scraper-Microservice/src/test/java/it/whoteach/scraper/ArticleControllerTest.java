package it.whoteach.scraper;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.grpc.internal.JsonUtil;
import it.whoteach.scraper.controller.ArticleController;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;
import it.whoteach.scraper.service.ArticleService;
import it.whoteach.scraper.service.CsvService;

@WebMvcTest(ArticleController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class ArticleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ArticleService articleService;

	@MockBean
	private ArticleRepository articleRepository;

	@MockBean
	private CsvService csvService;

	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	@WithMockUser
	public void getArticlesByIdTest() throws Exception {
		List<Article> articles = new ArrayList<>();
		Article article1 = new Article();
		article1.setUrl("https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html");
		article1.setSource("Indian Java");
		article1.setId(123456789L);
		Article article2 = new Article();
		article2.setUrl("https://www.facebook.net/2022/03/mockito.html");
		article2.setSource("Facebook Java");
		article2.setId(12324245L);
		articles.add(article1);
		articles.add(article2);
		List<Long> ids = new ArrayList<>();
		ids.add(article1.getId());
		ids.add(article2.getId());
		
		when(articleService.findAllById(any(List.class))).thenReturn(articles);
		ResultActions response = mockMvc.perform(get("/api/getAll/{ids}", article1.getId())
				.with(csrf()));
		response.andDo(print()).andExpect(status().isOk());
		
	}
	
	@Test
	@WithMockUser
	public void getAllArticlesTest() throws Exception {
		List<Article> articles = new ArrayList<>();
		Article article1 = new Article();
		article1.setUrl("https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html");
		article1.setSource("Indian Java");
		article1.setId(123456789L);
		Article article2 = new Article();
		article2.setUrl("https://www.facebook.net/2022/03/mockito.html");
		article2.setSource("Facebook Java");
		article2.setId(12324245L);
		articles.add(article1);
		articles.add(article2);

		when(articleService.findAll()).thenReturn(articles);
		ResultActions response = mockMvc.perform(get("/api/articles")
				.with(csrf()));
		response.andExpect(status().isOk()).andDo(print())
		.andExpect(jsonPath("$.size()", is(articles.size())));
	}

	@Test
	@WithMockUser
	public void postArticleTest() throws JsonProcessingException, Exception {
		Article article = new Article();
		article.setUrl("https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html");
		article.setSource("Indian Java");
		article.setId(123456789L);

		when(articleService.save(any(Article.class))).thenReturn(article);
		ResultActions response = mockMvc.perform(post("/api/article")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(article)));
		response.andDo(print()).andExpect(status().isOk());
		/*
		.andExpect(jsonPath("$.url", is(article.getUrl())))
		.andExpect(jsonPath("$.source", is(article.getSource())))
		.andExpect(jsonPath("$.id", is(article.getId())));
		*/
	}
	
	@Test
	@WithMockUser
	public void postAllArticleTest() throws JsonProcessingException, Exception {
		List<Article> articles = new ArrayList<>();
		Article article1 = new Article();
		article1.setUrl("https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html");
		article1.setSource("Indian Java");
		article1.setId(123456789L);
		Article article2 = new Article();
		article2.setUrl("https://www.facebook.net/2022/03/mockito.html");
		article2.setSource("Facebook Java");
		article2.setId(12324245L);
		articles.add(article1);
		articles.add(article2);

		when(articleService.saveAll(any(List.class))).thenReturn(articles);
		ResultActions response = mockMvc.perform(post("/api/articles")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(articles)));
		response.andDo(print()).andExpect(status().isOk());
	}
	














}

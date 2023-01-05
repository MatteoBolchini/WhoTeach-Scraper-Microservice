package it.whoteach.scraper;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.whoteach.scraper.controller.ArticleController;
import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.pojo.ResponseGet;
import it.whoteach.scraper.pojo.ResponsePut;
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
	public void getArticleByIdTest() throws Exception {
		Article article = new Article();
		article.setUrl("https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html");
		article.setSource("Indian Java");
		article.setId(123456789L);

		when(articleService.findById(any(Long.class))).thenReturn(article);
		ResultActions response = mockMvc.perform(get("/api/article/{id}", article.getId())
				.with(csrf()));
		response.andDo(print()).andExpect(status().isOk());

	}

	//@Test
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

		when(articleService.findAllById(any())).thenReturn(new ResponseGet(articles, 0, 0));
		ResultActions response = mockMvc.perform(get("/api/articles/{ids}", ids)
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
		ResultActions response = mockMvc.perform(get("/api/all")
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

		when(articleService.saveAll(any())).thenReturn(articles);
		ResultActions response = mockMvc.perform(post("/api/articles")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(articles)));
		response.andDo(print()).andExpect(status().isOk());
				
	}
	
	@Test
	@WithMockUser
	public void updateArticleTest() throws JsonProcessingException, Exception {
		ArticleDto articleDto1 = new ArticleDto();
		articleDto1.setUrl("https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html");
		articleDto1.setSource("Indian Java");
		articleDto1.setId(123456789L);
		ArticleDto articleDto2 = new ArticleDto();
		articleDto2.setUrl("https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html");
		articleDto2.setSource("Facebook Java");
		articleDto2.setId(12349L);
		
		when(articleService.update(any(ArticleDto.class))).thenReturn(articleDto1.getId());
		ResultActions response = mockMvc.perform(put("/api/article")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(articleDto2)));
		response.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void updateArticlesTest() throws JsonProcessingException, Exception {
		ArticleDto articleDto1 = new ArticleDto();
		articleDto1.setUrl("https://www.javaguides1.net");
		articleDto1.setSource("Indian Java");
		articleDto1.setId(123456789L);
		ArticleDto articleDto2 = new ArticleDto();
		articleDto2.setUrl("https://www.javaguides2.net");
		articleDto2.setSource("Facebook Java");
		articleDto2.setId(12349L);
		ArticleDto articleDto3 = new ArticleDto();
		articleDto3.setUrl("https://www.javaguides1.net");
		articleDto3.setSource("Urban Java");
		articleDto3.setId(123456789L);
		ArticleDto articleDto4 = new ArticleDto();
		articleDto4.setUrl("https://www.javaguides2.net");
		articleDto4.setSource("Test Java");
		articleDto4.setId(12349L);
		List<ArticleDto> articles = new ArrayList<>();
		articles.add(articleDto3);
		articles.add(articleDto4);
		List<Long> ids = new ArrayList<>();
		ids.add(articleDto1.getId());
		ids.add(articleDto2.getId());
		
		when(articleService.updateAll(any())).thenReturn(new ResponsePut(ids, 0, 0));
		ResultActions response = mockMvc.perform(put("/api/articles")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(articles)));
		response.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void deleteByIdTest() throws Exception {
		Long articleId = 123456789L;
		
		when(articleService.deleteById(any(Long.class))).thenReturn(articleId);
		ResultActions response = mockMvc.perform(delete("/api/article/{id}", articleId).with(csrf()));
		response.andDo(print()).andExpect(status().isOk());
	}
	
	/*@Test
	@WithMockUser
	public void deleteAllByIdTest() throws Exception {
		Long articleId1 = 123456789L;
		Long articleId2 = 12349L;
		List<Long> ids = new ArrayList<>();
		ids.add(articleId1);
		ids.add(articleId2);
		
		when(articleService.deleteAllById(any())).thenReturn(ids);
		ResultActions response = mockMvc.perform(delete("/api/deleteAll/{ids}", ids).with(csrf()));
		response.andDo(print()).andExpect(status().isOk());
	}*/

}

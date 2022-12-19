package it.whoteach.scraper.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.service.ArticleService;

@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ArticleService articleService;

	/**
	 * get all articles in the database
	 * 
	 * @return the list of all articles
	 */
	@ApiOperation(value = "Get the list of all articles in the database", 
			response = Article.class, 
			responseContainer = "List")
	@GetMapping("/articles")
	public ResponseEntity<List<Article>> all() {
		return articleService.findAll();
	}

	/**
	 * get the article by his id
	 * 
	 * @param id the article's id
	 * @return the article specified by the id
	 */
	@ApiOperation(value = "Get the article specified by the id given",
			response = Article.class)
	@GetMapping("/get/{id}")
	public ResponseEntity<Article> getById(@ApiParam(value = "ID of the article", 
	required = true) 
	@PathVariable Long id) {
		return articleService.findById(id);
	}
	
	/**
	 * get all articles by their id
	 * 
	 * @param ids the list of the articles's id
	 * @return the list of articles specified by their id
	 */
	@ApiOperation(value = "Get the list of articles specified by their id",
			notes = "If the id is not found in the persistence store it is silentily ignored",
			response = Article.class, 
			responseContainer = "List")
	@GetMapping("/getAll/{ids}") 
	public ResponseEntity<List<Article>> allById(@ApiParam(value = "IDs of the articles", 
	required = true)
	@PathVariable List<Long> ids) {
		return articleService.findAllById(ids);
	}

	/**
	 * insert the article in the database
	 * 
	 * @param article the article to be added to the database
	 * @return the added article's id
	 */
	@ApiOperation(value = "Post the article in the database", 
			notes = "Receives an ArticleDto, checks it and creates an Article if it is a valid ArticleDto",
			response = Long.class)
	@PostMapping("/article")
	public ResponseEntity<Long> newArticle(@ApiParam(value = "ArticleDto to post", 
	required = true) 
	@RequestBody ArticleDto article) {
		return new ResponseEntity<Long>(articleService.save(this.modelMapper.map(article, Article.class)).getId(),
				HttpStatus.OK);
	}

	/**
	 * insert all articles in the database
	 * 
	 * @param articles the list of articles to be added to the database
	 * @return the list of added article's id
	 */
	@ApiOperation(value = "Post the articles in the database", 
			notes = "Receives a list of ArticleDto, checks them and creates an Article from each valid ArticleDto",
			response = Long.class,
			responseContainer = "List")
	@PostMapping("/articles")
	public ResponseEntity<List<Long>> newArticles(@ApiParam(value = "List of ArticleDto to post", 
	required = true) 
	@RequestBody List<ArticleDto> articles) {
		List<Long> ids = new ArrayList<>();
		List<Article> articleList = modelMapper.map(articles, new TypeToken<List<Article>>() {}.getType());
		articleService.saveAll(articleList);
		for(Article a : articleList)
			ids.add(a.getId());
		return new ResponseEntity<List<Long>>(ids, HttpStatus.OK);
	}

	/**
	 * update the article in the database
	 * 
	 * @param article the ArticleDto (da convertire per aggiornare)
	 * @return the upgraded article's id
	 */
	
	@ApiOperation(value = "Put the article in the database", 
			notes = "Receives an ArticleDto, checks it and updates or creates an Article if it is a valid ArticleDto",
			response = Long.class)
	@PutMapping("/update")
	public ResponseEntity<Long> update(@ApiParam(value = "ArticleDto to put", 
	required = true) 
	@RequestBody ArticleDto article) {
		return new ResponseEntity<Long>(articleService.update(this.modelMapper.map(article, Article.class)).getId(),
				HttpStatus.OK);
	}

	/**
	 * update all articles in the database
	 * 
	 * @param articles the list of article (same)
	 * @return the list of upgraded articles's id
	 */
	@ApiOperation(value = "Put the article in the database", 
			notes = "Receives a list of ArticleDto, checks them and updates or creates an Article from each valid ArticleDto",
			response = Long.class,
			responseContainer = "List")
	@PutMapping("/updateAll")
	public ResponseEntity<List<Long>> updateAll(@ApiParam(value = "List of ArticleDtos to post", 
			required = true) 
	@RequestBody List<ArticleDto> articles) {
		List<Long> ids = new ArrayList<>();
		for(ArticleDto a : articles) {
			Article art = articleService.update(this.modelMapper.map(a, Article.class));
			ids.add(art.getId());
		}	
		return new ResponseEntity<List<Long>>(ids, HttpStatus.OK);
	}

	/**
	 * delete the article by the given id
	 * 
	 * @param id the article's id 
	 * @return the deleted article's id
	 */
	@ApiOperation(value = "Delete the article by his id", 
			notes = "If the id is not found in the persistence store it is silentily ignored",
			response = Long.class)
	@DeleteMapping("/delete/{id}") 
	public ResponseEntity<Long> clearById(@PathVariable Long id) {
		return articleService.deleteById(id);
	}

}

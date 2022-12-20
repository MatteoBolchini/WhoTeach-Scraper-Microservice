package it.whoteach.scraper.controller;

import java.util.ArrayList;
import java.util.List;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.service.ArticleService;

@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	/**
	 * get all articles in the database
	 * 
	 * @return the list of all articles
	 */
	@Operation(summary = "Get the list of all articles in the database")	
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
	@Operation(summary = "Get the article specified by the id given")
	@GetMapping("/get/{id}")
	public ResponseEntity<Article> getById(@Parameter(description = "ID of the article", 
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
	@Operation(summary = "Get the list of articles specified by their id",
			description = "If the id is not found in the database it is silentily ignored")
	@GetMapping("/getAll/{ids}") 
	public ResponseEntity<List<Article>> allById(@Parameter(description = "IDs of the articles", 
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
	@Operation(summary = "Post the article in the database", 
			description = "If the article is invalid (wrong arguments or inserted yet) "
					+ "it is logged and operation returns null")
	@PostMapping("/article")
	public ResponseEntity<Long> newArticle(@Parameter(description = "ArticleDto to post", 
	required = true) 
	@RequestBody ArticleDto article) {
		return articleService.newArticle(article);
	}

	/**
	 * insert all articles in the database
	 * 
	 * @param articles the list of articles to be added to the database
	 * @return the list of added article's id
	 */
	@Operation(summary = "Post the articles in the database", 
			description = "Receives a list of ArticleDto, checks them and creates an Article "
					+ "from each valid ArticleDto without a Url yet present in the database")
	@PostMapping("/articles")
	public ResponseEntity<List<Long>> newArticles(@Parameter(description = "List of ArticleDto to post", 
	required = true) 
	@RequestBody List<ArticleDto> articles) {
		return articleService.newArticles(articles);
		
	}

	/**
	 * update the article in the database
	 * 
	 * @param article the ArticleDto (da convertire per aggiornare)
	 * @return the upgraded article's id
	 */
	
	@Operation(summary = "Put the article in the database", 
			description = "Receives an ArticleDto, checks it and updates or creates an Article if it is a valid ArticleDto")
	@PutMapping("/update")
	public ResponseEntity<Long> update(@Parameter(description = "ArticleDto to put", 
	required = true) 
	@RequestBody ArticleDto article) {
		return articleService.update(article);
	}

	/**
	 * update all articles in the database
	 * 
	 * @param articles the list of article (same)
	 * @return the list of upgraded articles's id
	 */
	@Operation(summary = "Put the articles in the database", 
			description = "Receives a list of ArticleDto, checks them and updates or creates an Article "
					+ "from each valid ArticleDto")
	@PutMapping("/updateAll")
	public ResponseEntity<List<Long>> updateAll(@Parameter(description = "List of ArticleDto to post", 
			required = true) 
	@RequestBody List<ArticleDto> articles) {
		return articleService.updateAll(articles);
	}

	/**
	 * delete the article by the given id
	 * 
	 * @param id the article's id 
	 * @return the deleted article's id
	 */
	@Operation(summary = "Delete the article by his id", 
			description = "If the id is not found in the database it is silentily ignored")
	@DeleteMapping("/delete/{id}") 
	public ResponseEntity<Long> deleteById(@Parameter(description = "ID of the article") 
	@PathVariable Long id) {
		return articleService.deleteById(id);
	}
	
	/**
	 * delete the articles by their id
	 * 
	 * @param ids the list of the articles's id
	 * @return the deleted articles's id
	 */
	@Operation(summary = "Delete the articles by their id", 
			description = "Each id not found in the database it is silentily ignored")
	@DeleteMapping("/deleteAll/{ids}") 
	public ResponseEntity<List<Long>> deleteAllById(@Parameter(description = "IDs of the articles") 
	@PathVariable List<Long> ids) {
		List<Long> list = new ArrayList<>();
		for(Long id : ids) {
			deleteById(id);
		}
		return new ResponseEntity<List<Long>>(list, HttpStatus.OK);
	}

}

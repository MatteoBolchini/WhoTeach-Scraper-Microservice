package it.whoteach.scraper.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;

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
import it.whoteach.scraper.exception.BadRequestException;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.pojo.ResponseDelete;
import it.whoteach.scraper.pojo.ResponseGet;
import it.whoteach.scraper.pojo.ResponsePost;
import it.whoteach.scraper.pojo.ResponsePut;
import it.whoteach.scraper.service.ArticleService;
import lombok.NonNull;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	/**
	 * Gets all articles in the database
	 * 
	 * @return the list of all articles
	 */
	@Operation(summary = "Get the list of all articles in the database")	
			@GetMapping("/all")
	public ResponseEntity<List<Article>> all() {
		return new ResponseEntity<List<Article>>(articleService.findAll(), HttpStatus.OK);
	}

	/**
	 * Gets the article by his id
	 * 
	 * @param id the article's id
	 * @return the article specified by the id
	 * @throws NoSuchElementException if no value is present
	 * @throws NullPointerException in case the id is null
	 */
	@Operation(summary = "Gets the article specified by the id given",
			description = "If the id is not found in the database it is logged")
	@GetMapping("/article/{id}")
	public ResponseEntity<Article> getById(@Parameter(description = "ID of the article", 
	required = true) 
	@PathVariable @NonNull Long id) {
		try {
			return new ResponseEntity<Article>(articleService.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			log.log(Level.WARNING, String.format("Article with id [%s] not found", id));
			return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Gets all articles by their id
	 * 
	 * @param ids the list of the articles's id
	 * @return the list of articles specified by their id
	 * @throws NullPointerException in case the given list is null
	 */
	@Operation(summary = "Gets the list of articles specified by their id",
			description = "If the id is not found in the database it is logged")
	@GetMapping("/articles/{ids}") 
	public ResponseEntity<ResponseGet> getAllById(@Parameter(description = "IDs of the articles", 
	required = true)
	@PathVariable @NonNull List<Long> ids) {
			return new ResponseEntity<ResponseGet>(articleService.findAllById(ids), HttpStatus.OK);	
	}

	/**
	 * Creates the article in the database
	 * 
	 * @param article the article to be added to the database
	 * @return the added article's id
	 * @throws BadRequestException in case the article is presented yet in the database or it has an invalid url
	 * @throws NullPointerException in case the article is null
	 */
	@Operation(summary = "Creates the article in the database", 
			description = "If the article is invalid (wrong arguments or inserted yet) "
					+ "it throws and exception")
	@PostMapping("/article")
	public ResponseEntity<Long> newArticle(@Parameter(description = "ArticleDto to create", 
	required = true) 
	@RequestBody @NonNull ArticleDto article) {
		try {
			return new ResponseEntity<Long>(articleService.newArticle(article), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/**
	 * Creates all articles in the database
	 * 
	 * @param articles the list of articles to be added to the database
	 * @return the list of added article's id
	 * @throws NullPointerException in case the given list is null
	 */
	@Operation(summary = "Creates the articles in the database", 
			description = "Receives a list of ArticleDto, checks them and creates an Article "
					+ "from each valid ArticleDto")
	@PostMapping("/articles")
	public ResponseEntity<ResponsePost> newArticles(@Parameter(description = "List of ArticleDto to create", 
	required = true) 
	@RequestBody @NonNull List<ArticleDto> articles) {
			return new ResponseEntity<ResponsePost>(articleService.newArticles(articles), HttpStatus.OK);
	}

	/**
	 * Updates the article in the database
	 * 
	 * @param article the ArticleDto (da convertire per aggiornare)
	 * @return the upgraded article's id
	 * @throws BadRequestException in case the article has an invalid url
	 * @throws NullPointerException in case the article is null
	 */
	
	@Operation(summary = "Updates the article in the database", 
			description = "Receives an ArticleDto, checks it and updates or creates an Article")
	@PutMapping("/article")
	public ResponseEntity<Long> update(@Parameter(description = "ArticleDto to update", 
	required = true) 
	@RequestBody @NonNull ArticleDto article) {
		try {
			return new ResponseEntity<Long>(articleService.update(article), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Updates all articles in the database
	 * 
	 * @param articles the list of article (same)
	 * @return the list of upgraded articles's id
	 * @throws NullPointerException in case the given list is null
	 */
	@Operation(summary = "Updates the articles in the database", 
			description = "Receives a list of ArticleDto, checks them and updates or creates an Article "
					+ "from each valid ArticleDto")
	@PutMapping("/articles")
	public ResponseEntity<ResponsePut> updateAll(@Parameter(description = "List of ArticleDto to update", 
			required = true) 
	@RequestBody @NonNull List<ArticleDto> articles) {
		return new ResponseEntity<ResponsePut>(articleService.updateAll(articles), HttpStatus.OK);
	}

	/**
	 * Deletes the article by the given id
	 * 
	 * @param id the article's id 
	 * @return the deleted article's id
	 * @throws BadRequestException in case the article is not present in the database
	 * @throws NullPointerException in case the given id is null
	 */
	@Operation(summary = "Deletes the article by his id", 
			description = "If the id is not found in the database it is throws and exception")
	@DeleteMapping("/article/{id}") 
	public ResponseEntity<Long> deleteById(@Parameter(description = "ID of the article") 
	@PathVariable @NonNull Long id) {
		try {
			return new ResponseEntity<Long>(articleService.deleteById(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Deletes the articles by their id
	 * 
	 * @param ids the list of the articles's id
	 * @return the deleted articles's id
	 * @throws NullPointerException in case the given list is null
	 */
	@Operation(summary = "Deletes the articles by their id", 
			description = "Each id not found in the database throws and exception")
	@DeleteMapping("/articles/{ids}") 
	public ResponseEntity<ResponseDelete> deleteAllById(@Parameter(description = "IDs of the articles") 
	@PathVariable @NonNull List<Long> ids) {
		return new ResponseEntity<ResponseDelete>(articleService.deleteAllById(ids), HttpStatus.OK);
	}

}

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
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.service.ArticleService;
import lombok.extern.java.Log;

@Log
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
		return new ResponseEntity<List<Article>>(articleService.findAll(), HttpStatus.OK);
	}

	/**
	 * get the article by his id
	 * 
	 * @param id the article's id
	 * @return the article specified by the id
	 * @throws NoSuchElementException if no value is present
	 */
	@Operation(summary = "Gets the article specified by the id given",
			description = "If the id is not found in the database it is logged")
	@GetMapping("/get/{id}")
	public ResponseEntity<Article> getById(@Parameter(description = "ID of the article", 
	required = true) 
	@PathVariable Long id) {
		try {
			return new ResponseEntity<Article>(articleService.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			log.log(Level.WARNING, String.format("Article with id [%s] not found", id));
			return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * get all articles by their id
	 * 
	 * @param ids the list of the articles's id
	 * @return the list of articles specified by their id
	 */
	@Operation(summary = "Gets the list of articles specified by their id",
			description = "If the id is not found in the database it is logged")
	@GetMapping("/getAll/{ids}") 
	public ResponseEntity<List<Article>> allById(@Parameter(description = "IDs of the articles", 
	required = true)
	@PathVariable List<Long> ids) {
		return new ResponseEntity<List<Article>>(articleService.findAllById(ids), HttpStatus.OK);
	}

	/**
	 * insert the article in the database
	 * 
	 * @param article the article to be added to the database
	 * @return the added article's id
	 * @throws BadRequestException in case the article is presented yet in the database or it has an invalid url
	 */
	@Operation(summary = "Creates the article in the database", 
			description = "If the article is invalid (wrong arguments or inserted yet) "
					+ "it throws and exception")
	@PostMapping("/article")
	public ResponseEntity<Long> newArticle(@Parameter(description = "ArticleDto to create", 
	required = true) 
	@RequestBody ArticleDto article) {
		try {
			return new ResponseEntity<Long>(articleService.newArticle(article), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
		
	}

	/**
	 * insert all articles in the database
	 * 
	 * @param articles the list of articles to be added to the database
	 * @return the list of added article's id
	 */
	@Operation(summary = "Creates the articles in the database", 
			description = "Receives a list of ArticleDto, checks them and creates an Article "
					+ "from each valid ArticleDto")
	@PostMapping("/articles")
	public ResponseEntity<List<Long>> newArticles(@Parameter(description = "List of ArticleDto to create", 
	required = true) 
	@RequestBody List<ArticleDto> articles) {
			return new ResponseEntity<List<Long>>(articleService.newArticles(articles), HttpStatus.OK);
	}

	/**
	 * update the article in the database
	 * 
	 * @param article the ArticleDto (da convertire per aggiornare)
	 * @return the upgraded article's id
	 * @throws BadRequestException in case the article has an invalid url
	 */
	
	@Operation(summary = "Updates the article in the database", 
			description = "Receives an ArticleDto, checks it and updates or creates an Article")
	@PutMapping("/update")
	public ResponseEntity<Long> update(@Parameter(description = "ArticleDto to update", 
	required = true) 
	@RequestBody ArticleDto article) {
		try {
			return new ResponseEntity<Long>(articleService.update(article), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * update all articles in the database
	 * 
	 * @param articles the list of article (same)
	 * @return the list of upgraded articles's id
	 */
	@Operation(summary = "Updates the articles in the database", 
			description = "Receives a list of ArticleDto, checks them and updates or creates an Article "
					+ "from each valid ArticleDto")
	@PutMapping("/updateAll")
	public ResponseEntity<List<Long>> updateAll(@Parameter(description = "List of ArticleDto to update", 
			required = true) 
	@RequestBody List<ArticleDto> articles) {
		return new ResponseEntity<List<Long>>(articleService.updateAll(articles), HttpStatus.OK);
	}

	/**
	 * delete the article by the given id
	 * 
	 * @param id the article's id 
	 * @return the deleted article's id
	 * @throws BadRequestException in case the article is not present in the database
	 */
	@Operation(summary = "Deletes the article by his id", 
			description = "If the id is not found in the database it is throws and exception")
	@DeleteMapping("/delete/{id}") 
	public ResponseEntity<Long> deleteById(@Parameter(description = "ID of the article") 
	@PathVariable Long id) {
		try {
			return new ResponseEntity<Long>(articleService.deleteById(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * delete the articles by their id
	 * 
	 * @param ids the list of the articles's id
	 * @return the deleted articles's id
	 */
	@Operation(summary = "Deletes the articles by their id", 
			description = "Each id not found in the database throws and exception")
	@DeleteMapping("/deleteAll/{ids}") 
	public ResponseEntity<List<Long>> deleteAllById(@Parameter(description = "IDs of the articles") 
	@PathVariable List<Long> ids) {
		return new ResponseEntity<List<Long>>(articleService.deleteAllBydId(ids), HttpStatus.OK);
	}

}

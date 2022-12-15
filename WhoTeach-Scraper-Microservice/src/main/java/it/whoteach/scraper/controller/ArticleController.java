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

	/*
	 * @return the list of all articles
	 */
	@GetMapping("/articles")
	public ResponseEntity<List<Article>> all() {
		return articleService.findAll();
	}

	/*
	 * @param id the article's id
	 * @return the article specified by the id
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<Article> getById(@PathVariable Long id) {
		return articleService.findById(id);
	}
	
	/*
	 * @param ids the list of the articles's id
	 * @return the list of articles specified by the ids
	 */
	@GetMapping("/getAll/{ids}") 
	public ResponseEntity<List<Article>> allById(@PathVariable List<Long> ids) {
		return articleService.findAllById(ids);
	}

	/*
	 * @param article the ArticleDto (che deve essere convertito in articolo e aggiunto al db)
	 * @return the added article's id
	 */
	@PostMapping("/article")
	public ResponseEntity<Long> newArticle(@RequestBody ArticleDto article) {
		return new ResponseEntity<Long>(articleService.save(this.modelMapper.map(article, Article.class)).getId(),
				HttpStatus.OK);
	}

	/*
	 * @param articles the list of ArticleDtos (stesso di sopra)
	 * @return the list of added article's id
	 */
	@PostMapping("/articles")
	public ResponseEntity<List<Long>> newArticles(@RequestBody List<ArticleDto> articles) {
		List<Long> ids = new ArrayList<>();
		List<Article> articleList = modelMapper.map(articles, new TypeToken<List<Article>>() {}.getType());
		articleService.saveAll(articleList);
		for(Article a : articleList)
			ids.add(a.getId());
		return new ResponseEntity<List<Long>>(ids, HttpStatus.OK);
	}

	/*
	 * @param article the ArticleDto (da convertire per aggiornare)
	 * @return the upgraded article's id
	 */
	@PutMapping("/update")
	public ResponseEntity<Long> update(@RequestBody ArticleDto article) {
		return new ResponseEntity<Long>(articleService.update(this.modelMapper.map(article, Article.class)).getId(),
				HttpStatus.OK);
	}

	/*
	 * @param articles the list of article (same)
	 * @return the list of upgraded articles's id
	 */
	@PutMapping("/updateAll")
	public ResponseEntity<List<Long>> updateAll(@RequestBody List<ArticleDto> articles) {
		List<Long> ids = new ArrayList<>();
		for(ArticleDto a : articles) {
			Article art = articleService.update(this.modelMapper.map(a, Article.class));
			ids.add(art.getId());
		}	
		return new ResponseEntity<List<Long>>(ids, HttpStatus.OK);
	}

	// elimina un articolo
	@DeleteMapping("/delete")
	public ResponseEntity<Long> delete(@RequestBody Article article) {
		 return articleService.delete(article);
	}

	// elimina un articolo tramite id
	@DeleteMapping("/delete/{id}") 
	public ResponseEntity<Long> clearById(@PathVariable Long id) {
		return articleService.deleteById(id);
	}

	// elimina i nodi che non hanno relazioni
	@DeleteMapping("/deleteAloneNodes")
	public ResponseEntity<Void> deleteAloneNodes() {
		return articleService.deleteAlone();
	}

	// svuota tutto il database
	@DeleteMapping("/clear")
	public ResponseEntity<Void> clear() {
		return articleService.clearDatabase();
	}

}

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
import org.springframework.web.bind.annotation.RestController;

import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.exception.RequiredFieldNullException;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.service.ArticleService;

@RestController
public class ArticleController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ArticleService articleService;

	// ritorna la lista degli articoli
	@GetMapping("/articles")
	public ResponseEntity<List<Article>> all() {
		return articleService.findAll();
	}

	// restituisce l'articolo con l'idItem specificato
	@GetMapping("/get/{id}")
	public ResponseEntity<Article> getById(@PathVariable Long id) {
		return articleService.findById(id);
	}

	// restituisce gli articoli con la lista di idItem specificata
	@GetMapping("/getAll/{ids}") 
	public ResponseEntity<List<Article>> allById(@PathVariable List<Long> ids) {
		return articleService.findAllById(ids);
	}

	// aggiunge un articolo definito in un Json
	@PostMapping("/article")
	public ResponseEntity<Long> newArticle(@RequestBody ArticleDto article) {
		return new ResponseEntity<Long>(articleService.save(this.modelMapper.map(article, Article.class)).getId(),
				HttpStatus.OK);
	}

	// aggiunge gli articoli definiti in un JsonArray
	@PostMapping("/articles")
	public ResponseEntity<List<Long>> newArticles(@RequestBody List<ArticleDto> articles) {
		List<Long> ids = new ArrayList<>();
		List<Article> articleList = modelMapper.map(articles, new TypeToken<List<Article>>() {}.getType());
		articleService.saveAll(articleList);
		for(Article a : articleList)
			ids.add(a.getId());
		return new ResponseEntity<List<Long>>(ids, HttpStatus.OK);
	}

	// aggiorna l'articolo se è presente, altrimenti lo crea
	@PutMapping("/update")
	public ResponseEntity<Long> update(@RequestBody ArticleDto article) {
		return new ResponseEntity<Long>(articleService.update(this.modelMapper.map(article, Article.class)).getId(),
				HttpStatus.OK);
	}

	// aggiorna la lista degli articoli
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
	public void deleteAloneNodes() {
		articleService.deleteAlone();
	}

	// svuota tutto il database
	@DeleteMapping("/clear")
	public void clear() {
		articleService.clearDatabase();
	}

}

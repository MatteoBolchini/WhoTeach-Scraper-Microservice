package it.whoteach.scraper.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
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

import it.whoteach.scraper.dto.ArticleDTO;
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
	List<Article> all() {
		return articleService.findAll();
	}
	
	// restituisce l'articolo con l'idItem specificato
	@GetMapping("/read/{id}")
	Article singleByIdItem(@PathVariable Long id) {
		return articleService.findById(id);
	}
	
	// restituisce gli articoli con la lista di idItem specificata
	@GetMapping("/readAll/{ids}") 
	List<Article> allByIdItem(@PathVariable Long[] ids) {
		return articleService.allByIdItem(ids);
	}

	// aggiunge un articolo definito in un Json
	@PostMapping("/article")
	Article newArticle(@RequestBody ArticleDTO article) {
		return articleService.save(this.modelMapper.map(article, Article.class));
	}

	// aggiunge gli articoli definiti in un JsonArray
	@PostMapping("/articles")
	List<Article> newArticlesss(@RequestBody List<ArticleDTO> articles) {
		return articleService.saveAll(this.modelMapper, articles);
	}
	
	// aggiorna i valori di un articolo con una sua versione aggiornata
	@PutMapping("/update")
	public ResponseEntity<Article> updateById(@RequestBody ArticleDTO article) {
		if(article.getId() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(articleService.update(this.modelMapper.map(article, Article.class)), HttpStatus.OK);
	}

	// elimina un articolo e ripulisce il database dai nodi rimasti isolati
	@DeleteMapping("/clear/{id}") 
	void clearById(@PathVariable Long id) {
		articleService.clearById(id);
	}
	
	// svuota tutto il database
	@DeleteMapping("/clear")
	void clear() {
		articleService.clearDatabase();
	}

	// elimina i nodi che non hanno relazioni
	// da decidere se fixare questa cosa nei metodi/chiamarlo ogni volta che viene fatto un metodo/chiamarlo manualmente
	@DeleteMapping("/clearAloneNodes")
	void clearAloneNodes() {
		articleService.clearAlone();
	}
}

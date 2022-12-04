package it.whoteach.scraper.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;
import it.whoteach.scraper.service.ArticleService;

@RestController
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepository;
	
	private ArticleService articleService = new ArticleService();

	public ArticleController(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	// ritorna la lista degli articoli
	@GetMapping("/articles")
	List<Article> all() {
		return articleRepository.findAll();
	}
	
	// restituisce l'articolo con l'idItem specificato
	@GetMapping("/read/{idItem}")
	Article singleByIdItem(@PathVariable String idItem) {
		return articleRepository.findByIdItem(idItem);
	}
	
	// restituisce gli articoli con la lista di idItem specificata
	@GetMapping("/readAll/{idItems}")
	List<Article> allByIdItem(@PathVariable String[] idItems) {
		return articleService.allByIdItem(articleRepository, idItems);
	}

	// aggiunge un articolo definito in un Json
	@PostMapping("/article")
	Article newArticle(@RequestBody Article newArticle) {
		return articleService.save(articleRepository, newArticle);

	}

	// aggiunge gli articoli definiti in un JsonArray
	@PostMapping("/articles")
	List<Article> newArticles(@RequestBody List<Article> articles) {
		return articleService.saveAll(articleRepository, articles);
	}
	
	// aggiorna i valori di un articolo con una sua versione aggiornata
	@PutMapping("/update/{idItem}")
	Article updateById(@RequestBody Article updatedArticle, @PathVariable String idItem) {
		return articleService.update(articleRepository.findByIdItem(idItem), updatedArticle, articleRepository);
	}

	// elimina un articolo e ripulisce il database dai nodi rimasti isolati
	@DeleteMapping("/clear/{idItem}") 
	void clearById(@PathVariable String idItem) {
		articleRepository.clearById(idItem);
		articleRepository.clearAlone();
	}
	
	// svuota tutto il database
	@DeleteMapping("/clear")
	void clear() {
		articleRepository.clearDatabase();
	}

	// elimina i nodi che non hanno relazioni
	// da decidere se fixare questa cosa nei metodi/chiamarlo ogni volta che viene fatto un metodo/chiamarlo manualmente
	@DeleteMapping("/clearAloneNodes")
	void clearAloneNodes() {
		articleRepository.clearAlone();
	}
}

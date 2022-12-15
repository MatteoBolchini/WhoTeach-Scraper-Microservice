package it.whoteach.scraper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.whoteach.scraper.service.CsvService;

@RestController
@RequestMapping("/csv")
public class CsvController {
	
	@Autowired
	CsvService csvService;	
	
	@PostMapping("/read-bucket/{fileName}")
	public ResponseEntity<Void> readFromBucket(@PathVariable String fileName) {
		if(fileName == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		csvService.csvToArticle(fileName);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/update-bucket/{fileName}")
	public ResponseEntity<Void> updateFromBucket(@PathVariable String fileName) {
		if(fileName == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		csvService.csvToArticle(fileName);
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	/*
	 * @param fileName The bucket's name
	 * @return the status of the called method
	 */
	@PostMapping("/post-bucket/{fileName}")
	public ResponseEntity<Void> postFromBucket(@PathVariable String fileName) {
		if(fileName == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		csvService.retrieveCsv(fileName);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}

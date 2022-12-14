package it.whoteach.scraper.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.whoteach.connector.GoogleCloudConnector;
import it.whoteach.scraper.service.CsvService;

@RestController
@RequestMapping("/csv")
public class CsvController {
	
	@Autowired
	CsvService csvService;
	
	private GoogleCloudConnector googleCloudConnector = new GoogleCloudConnector();
	
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
	
	@PostMapping("/getBlob/{fileName}")
	public ResponseEntity<Void> getBlob(@PathVariable String fileName) {
		if(fileName == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		googleCloudConnector.retrieveCsv(fileName);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/getBlob/{fileName}")
	public ResponseEntity<Void> getBlob2(@PathVariable String fileName) throws IOException {
		if(fileName == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		googleCloudConnector.getSecret();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}

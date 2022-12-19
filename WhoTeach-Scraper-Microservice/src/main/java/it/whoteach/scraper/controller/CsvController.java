package it.whoteach.scraper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.whoteach.scraper.service.CsvService;

@RestController
@RequestMapping("/csv")
public class CsvController {
	
	@Autowired
	CsvService csvService;	
	
	/**
	 * insert the articles from the csv file to the database
	 * 
	 * @param fileName The bucket's name
	 * @return the Http status
	 */
	@ApiOperation(value = "Creates new articles in the database from a csv file contained in the bucket",
			notes = "If one article is invalid, the operation stops and nothing get posted into the database")
	@PostMapping("/post-bucket/{fileName}")
	public ResponseEntity<Void> postFromBucket(@ApiParam(value = "Name of the csv file", required = true) 
	@PathVariable String fileName) {
		if(fileName == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		csvService.retrieveCsv(fileName);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * update the articles from the csv file to the database
	 * 
	 * @param fileName The bucket's name
	 * @return the Http status
	 */
	@ApiOperation(value = "Creates new articles if their Url is not present in the database yet, "
			+ "otherwise it updates them.",
			notes = "If one article is invalid, the operation stops and nothing get put into the database")
	@PutMapping("/put-bucket/{fileName}")
	public ResponseEntity<Void> putFromBucket(@ApiParam(value = "Name of the csv file", required = true) 
	@PathVariable String fileName) {
		return postFromBucket(fileName);
	}
	
}

package it.whoteach.scraper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
	@Operation(summary = "Creates new articles in the database",
			description = "If one article is invalid (wrong arguments or inserted yet) "
					+ "it is logged and the operation continues")
	@PostMapping("/post-bucket/{fileName}")
	public ResponseEntity<List<Long>> postFromBucket(@Parameter(description = "Name of the csv file in the bucket", required = true) 
	@PathVariable String fileName) {
		return csvService.postFromBucket(fileName);
	}
	
	/**
	 * update the articles from the csv file to the database
	 * 
	 * @param fileName The bucket's name
	 * @return the Http status
	 */
	@Operation(summary = "Creates new articles if their Url is not present in the database yet, "
			+ "otherwise it updates them.",
			description = "If one article is invalid (wrong arguments), "
					+ "it is logged and the operation continues")
	@PutMapping("/put-bucket/{fileName}")
	public ResponseEntity<List<Long>> putFromBucket(@Parameter(description = "Name of the csv file in the bucket", required = true) 
	@PathVariable String fileName) {
		return csvService.putFromBucket(fileName);
	}
	
}

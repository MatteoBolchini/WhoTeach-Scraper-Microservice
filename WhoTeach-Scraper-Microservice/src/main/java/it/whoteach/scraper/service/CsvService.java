package it.whoteach.scraper.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;

import it.whoteach.scraper.connector.GoogleCloudConnector;
import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;
import lombok.extern.java.Log;

@Log
@Service
public class CsvService {

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	GoogleCloudConnector googleCloudConnector;
	
	public void csvToArticle(String fileName) {
		try {
			log.log(Level.INFO, "Scansione iniziata: " + LocalTime.now());
			List<ArticleDto> articles = new CsvToBeanBuilder<ArticleDto>(new FileReader(String.format("src/main/resources/%s", fileName)))
					.withSeparator(';')
					.withIgnoreQuotations(false)
					.withType(ArticleDto.class)
					.build()
					.parse();
			for(ArticleDto a : articles) {
				articleRepository.save(this.modelMapper.map(a, Article.class));
			}			log.log(Level.INFO, "SCANSIONE EFFETTUATA: " + LocalTime.now());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

	public void retrieveCsv(String fileName) {
		for(ArticleDto a : googleCloudConnector.retrieveCsv(fileName)) {
			articleRepository.save(this.modelMapper.map(a, Article.class));
		}
	}
	
	

}

package it.whoteach.scraper.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;

import it.whoteach.connector.GoogleCloudConnector;
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
			List<ArticleDto> articles = new CsvToBeanBuilder(new FileReader(String.format("src/main/resources/%s", fileName)))
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

}

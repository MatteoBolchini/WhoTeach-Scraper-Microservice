package it.whoteach.scraper.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;

import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.dto.AuthorDto;
import it.whoteach.scraper.dto.DescriptionDto;
import it.whoteach.scraper.dto.DestinationPublicDto;
import it.whoteach.scraper.dto.DifficultyDto;
import it.whoteach.scraper.dto.DomainDto;
import it.whoteach.scraper.dto.DurationDto;
import it.whoteach.scraper.dto.FormatDto;
import it.whoteach.scraper.dto.KeywordDto;
import it.whoteach.scraper.dto.LanguageDto;
import it.whoteach.scraper.dto.MaxAgeDto;
import it.whoteach.scraper.dto.MinAgeDto;
import it.whoteach.scraper.dto.SubdomainDto;
import it.whoteach.scraper.dto.SubsubdomainDto;
import it.whoteach.scraper.dto.TitleDto;
import it.whoteach.scraper.dto.TypeDto;
import it.whoteach.scraper.dto.UploadDateDto;
import it.whoteach.scraper.exception.RequiredFieldNullException;
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
			}
			log.log(Level.INFO, "SCANSIONE EFFETTUATA: " + LocalTime.now());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

}

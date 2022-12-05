package it.whoteach.scraper.config;

import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import it.whoteach.scraper.exception.RequiredFieldNullException;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.pojo.Author;
import it.whoteach.scraper.pojo.Description;
import it.whoteach.scraper.pojo.DestinationPublic;
import it.whoteach.scraper.pojo.Difficulty;
import it.whoteach.scraper.pojo.Domain;
import it.whoteach.scraper.pojo.Duration;
import it.whoteach.scraper.pojo.Format;
import it.whoteach.scraper.pojo.Keyword;
import it.whoteach.scraper.pojo.Language;
import it.whoteach.scraper.pojo.MaxAge;
import it.whoteach.scraper.pojo.MinAge;
import it.whoteach.scraper.pojo.Subdomain;
import it.whoteach.scraper.pojo.Subsubdomain;
import it.whoteach.scraper.pojo.Title;
import it.whoteach.scraper.pojo.Type;
import it.whoteach.scraper.pojo.UploadDate;
import it.whoteach.scraper.repository.ArticleRepository;

//@Configuration
public class LoadDatabase {
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			csvToArticle(articleRepository);
		};
	}
	
	public static void csvToArticle(ArticleRepository articleRepository) {
		int i = 0; // contatore per i test
		try {
			FileReader fileReader = new FileReader("src/main/resources/MERLOT_NER.csv");
			CSVParser parser = new CSVParserBuilder()
					.withSeparator(';')
					.withIgnoreQuotations(false)
					.build();
			CSVReader csvReader = new CSVReaderBuilder(fileReader)
					.withSkipLines(1)
					.withCSVParser(parser)
					.build();
			String[] row;
			System.out.println("Scansione iniziata: " + LocalTime.now());
			
			while((row = csvReader.readNext()) != null  && i != 20) { //  && i != 5000
				Article article = new Article();
				if(row[15] == null || row[1] == null)
					throw new RequiredFieldNullException("Url and Source are mandatory");
				article.setSource(row[15]);
				article.setUrl(row[1]);
				// crea la lista degli autori
				String[] authors = row[7].split(",");
				List<Author> list1 = new ArrayList<>();
				for(int j = 0;j < authors.length;j++) {
					list1.add(new Author(authors[j]));
				}
				// crea la lista delle keywords
				String[] keywords = row[2].split(",");
				List<Keyword> list2 = new ArrayList<>();
				for(int k = 0;k < keywords.length;k++) {
					list2.add(new Keyword(keywords[k]));
				}
				// crea la lista delle destination public
				String[] destinations = row[12].split(",");
				List<DestinationPublic> list3 = new ArrayList<>();
				for(int l = 0;l < destinations.length;l++) {
					list3.add(new DestinationPublic(destinations[l]));
				}
				// crea la lista dei subdomain
				String[] subdomains = row[17].split(",");
				List<Subdomain> list4 = new ArrayList<>();
				for(int m = 0;m < subdomains.length;m++) {
					list4.add(new Subdomain(subdomains[m]));
				}
				// aggiunge i dati
				article.setAuthors(list1);
				article.setKeywords(list2);
				article.setDestinationPublic(list3);
				article.setSubdomain(list4);
				article.setDescription(new Description(row[4]));
				article.setDifficulty(new Difficulty(row[9]));
				article.setDomain(new Domain(row[16]));
				article.setDuration(new Duration(row[11]));
				article.setFormat(new Format(row[10]));
				article.setLanguage(new Language(row[8]));
				article.setMaxAge(new MaxAge(row[14]));
				article.setMinAge(new MinAge(row[13]));
				article.setSubsubdomain(new Subsubdomain(row[18]));
				article.setTitle(new Title(row[3]));
				article.setType(new Type(row[5]));
				article.setUploadDate(new UploadDate(row[6]));
				i++;
				articleRepository.save(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SCANSIONE EFFETTUATA: " + LocalTime.now());
	}

}

package it.whoteach.scraper.service;

import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

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

@Service
public class CsvService {
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	ModelMapper modelMapper;
		
	public void csvToArticle(String fileName) {
		int i = 0; // contatore per i test
		try {
			FileReader fileReader = new FileReader(String.format("src/main/resources/%s", fileName));
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

			while((row = csvReader.readNext()) != null  && i != 100) { //  && i != 5000
				ArticleDto article = new ArticleDto();
				if(row[15] == null || row[1] == null)
					throw new RequiredFieldNullException("Url and Source are mandatory");
				article.setSource(row[15]);
				article.setUrl(row[1]);
				// crea la lista degli autori
				String[] authors = row[7].split(",");
				List<AuthorDto> list1 = new ArrayList<>();
				for(int j = 0;j < authors.length;j++) {
					list1.add(new AuthorDto(authors[j]));
				}
				// crea la lista delle keywords
				String[] keywords = row[2].split(",");
				List<KeywordDto> list2 = new ArrayList<>();
				for(int k = 0;k < keywords.length;k++) {
					list2.add(new KeywordDto(keywords[k]));
				}
				// crea la lista delle destination public
				String[] destinations = row[12].split(",");
				List<DestinationPublicDto> list3 = new ArrayList<>();
				for(int l = 0;l < destinations.length;l++) {
					list3.add(new DestinationPublicDto(destinations[l]));
				}
				// crea la lista dei subdomain
				String[] subdomains = row[17].split(",");
				List<SubdomainDto> list4 = new ArrayList<>();
				for(int m = 0;m < subdomains.length;m++) {
					list4.add(new SubdomainDto(subdomains[m]));
				}
				// aggiunge i dati
				if(list1 != null || list1.isEmpty())
					article.setAuthors(list1);
				if(list2 != null || list2.isEmpty())
					article.setKeywords(list2);
				if(list3 != null || list3.isEmpty())
					article.setDestinationPublic(list3);
				if(list4 != null || list4.isEmpty())
					article.setSubdomain(list4);
				if(row[4] != null)
					article.setDescription(new DescriptionDto(row[4]));
				if(row[9] != null)
					article.setDifficulty(new DifficultyDto(row[9]));
				if(row[16] != null)
					article.setDomain(new DomainDto(row[16]));
				if(row[11] != null)
					article.setDuration(new DurationDto(row[11]));
				if(row[10] != null)
					article.setFormat(new FormatDto(row[10]));
				if(row[8] != null)
					article.setLanguage(new LanguageDto(row[8]));
				if(row[14] != null)
					article.setMaxAge(new MaxAgeDto(row[14]));
				if(row[13] != null)
					article.setMinAge(new MinAgeDto(row[13]));
				if(row[18] != null)
					article.setSubsubdomain(new SubsubdomainDto(row[18]));
				if(row[3] != null)
					article.setTitle(new TitleDto(row[3]));
				if(row[5] != null)
					article.setType(new TypeDto(row[5]));
				if(row[6] != null)
					article.setUploadDate(new UploadDateDto(row[6]));
				i++;
				articleRepository.save(this.modelMapper.map(article, Article.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SCANSIONE EFFETTUATA: " + LocalTime.now());
	}
}

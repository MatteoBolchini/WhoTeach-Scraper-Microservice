package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Language;

@Repository
public interface LanguageRepository extends Neo4jRepository<Language, String> {
	Language findByLanguage(String language);
}

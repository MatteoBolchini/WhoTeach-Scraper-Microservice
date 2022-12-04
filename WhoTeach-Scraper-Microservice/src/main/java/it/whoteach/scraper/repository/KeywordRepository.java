package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Keyword;

@Repository
public interface KeywordRepository extends Neo4jRepository<Keyword, String> {
	Keyword findByKeywords(String keywords);
}

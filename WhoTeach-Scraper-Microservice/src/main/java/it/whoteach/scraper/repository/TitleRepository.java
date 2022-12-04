package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Title;

@Repository
public interface TitleRepository extends Neo4jRepository<Title, String> {
	Title findByTitle(String title);
}

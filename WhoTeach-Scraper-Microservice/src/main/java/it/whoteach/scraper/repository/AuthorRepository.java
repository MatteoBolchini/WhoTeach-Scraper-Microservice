package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Author;

@Repository
public interface AuthorRepository extends Neo4jRepository<Author, String> {
	Author findByName(String name);
}

package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.MaxAge;

@Repository
public interface MaxAgeRepository extends Neo4jRepository<MaxAge, String> {
	MaxAge findByMaxAge(String maxAge);
}

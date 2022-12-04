package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.MinAge;

@Repository
public interface MinAgeRepository extends Neo4jRepository<MinAge, String> {
	MinAge findByMinAge(String minAge);
}

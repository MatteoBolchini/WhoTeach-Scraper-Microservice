package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Description;

@Repository
public interface DescriptionRepository extends Neo4jRepository<Description, String> {
	Description findByDescription(String description);
}

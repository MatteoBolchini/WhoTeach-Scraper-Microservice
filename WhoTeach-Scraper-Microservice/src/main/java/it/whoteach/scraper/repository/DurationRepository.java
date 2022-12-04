package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Duration;

@Repository
public interface DurationRepository extends Neo4jRepository<Duration, String> {
	Duration findByDuration(String duration);
}

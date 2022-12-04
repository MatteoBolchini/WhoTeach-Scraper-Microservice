package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Difficulty;

@Repository
public interface DifficultyRepository extends Neo4jRepository<Difficulty, String> {
	Difficulty findByDifficulty(String difficulty);
}

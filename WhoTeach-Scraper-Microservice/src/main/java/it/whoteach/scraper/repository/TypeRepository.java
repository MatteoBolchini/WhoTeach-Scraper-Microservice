package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Type;

@Repository
public interface TypeRepository extends Neo4jRepository<Type, String> {
	Type findByType(String type);
}

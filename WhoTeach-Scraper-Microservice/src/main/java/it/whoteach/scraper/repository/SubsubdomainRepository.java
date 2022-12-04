package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Subsubdomain;

@Repository
public interface SubsubdomainRepository extends Neo4jRepository<Subsubdomain, String> {
	Subsubdomain findBySubsubdomain(String subsubdomain);
}

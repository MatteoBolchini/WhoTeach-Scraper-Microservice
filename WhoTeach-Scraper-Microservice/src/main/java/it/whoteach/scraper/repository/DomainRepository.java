package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Domain;

@Repository
public interface DomainRepository extends Neo4jRepository<Domain, String>{
	Domain findByDomain(String domain);
}

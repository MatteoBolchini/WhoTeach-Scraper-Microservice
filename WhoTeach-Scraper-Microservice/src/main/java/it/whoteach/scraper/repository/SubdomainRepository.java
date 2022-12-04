package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Subdomain;

@Repository
public interface SubdomainRepository extends Neo4jRepository<Subdomain, String>{
	Subdomain findBySubdomain(String subdomain);
}

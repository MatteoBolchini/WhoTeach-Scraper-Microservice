package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Format;

@Repository
public interface FormatRepository extends Neo4jRepository<Format, String> {
	Format findByFormat(String format);
}

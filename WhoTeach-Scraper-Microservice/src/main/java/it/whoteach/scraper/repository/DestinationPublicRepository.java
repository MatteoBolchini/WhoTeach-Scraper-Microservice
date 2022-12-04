package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.DestinationPublic;

@Repository
public interface DestinationPublicRepository extends Neo4jRepository<DestinationPublic, String> {
	DestinationPublic findByDestinationPublic(String destinationPublic);
}

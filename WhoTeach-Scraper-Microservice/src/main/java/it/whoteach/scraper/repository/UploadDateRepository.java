package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.UploadDate;

@Repository
public interface UploadDateRepository extends Neo4jRepository<UploadDate, String> {
	UploadDate findByUploadDate(String uploadDate);
}

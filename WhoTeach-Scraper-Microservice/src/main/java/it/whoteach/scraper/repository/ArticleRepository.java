package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.pojo.Author;
import it.whoteach.scraper.pojo.Description;
import it.whoteach.scraper.pojo.DestinationPublic;
import it.whoteach.scraper.pojo.Difficulty;
import it.whoteach.scraper.pojo.Domain;
import it.whoteach.scraper.pojo.Duration;
import it.whoteach.scraper.pojo.Language;
import it.whoteach.scraper.pojo.MaxAge;
import it.whoteach.scraper.pojo.MinAge;
import it.whoteach.scraper.pojo.Subdomain;
import it.whoteach.scraper.pojo.Subsubdomain;
import it.whoteach.scraper.pojo.Title;
import it.whoteach.scraper.pojo.Type;
import it.whoteach.scraper.pojo.UploadDate;

@Repository
public interface ArticleRepository extends Neo4jRepository<Article, String> {
	Article findByIdItem(String idItem);
	@Query("MATCH(n) DETACH DELETE n")
	void clearDatabase();
	@Query("MATCH (n) WHERE NOT (n)--() DELETE n")
	void clearAlone();
	@Query("MATCH (n:Article)-[r]->(m) WHERE n.idItem=$idItem DETACH DELETE n")
	void clearById(String idItem);
}

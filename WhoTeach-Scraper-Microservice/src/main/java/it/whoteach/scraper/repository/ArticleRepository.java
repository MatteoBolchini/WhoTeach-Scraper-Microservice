package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Article;

@Repository
public interface ArticleRepository extends Neo4jRepository<Article, Long> {
	@Query("MATCH (n) WHERE NOT (n)--() DELETE n")
	void deleteAlone();
	@Query("MATCH (n) WHERE ID(n) = $id DETACH DELETE n")
	void clearById(Long id);
}

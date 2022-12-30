package it.whoteach.scraper.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import it.whoteach.scraper.pojo.Article;

@Repository
public interface ArticleRepository extends Neo4jRepository<Article, Long> {
	@Query("match(n) where not(n)--() delete n")
	void deleteAlone();
	@Query("match(n) where id(n) = $id detach delete n")
	void clearById(Long id);
	@Query("match(n)-[r]-(m) where n.url = $url return n,collect(r),collect(m)")
	Article getByUrl(String url);
	boolean existsByUrl(String url);
}

package it.whoteach.scraper.pojo;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Node
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {
	@Id
	private String keywords;
}

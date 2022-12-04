package it.whoteach.scraper.pojo;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Node
@Getter
@Setter
@AllArgsConstructor
public class Description {
	@Id
	private String description;

}

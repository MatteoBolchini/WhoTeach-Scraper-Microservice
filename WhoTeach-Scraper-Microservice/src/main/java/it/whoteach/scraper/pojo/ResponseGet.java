package it.whoteach.scraper.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGet {
	
	private List<Article> articles;
	private int objectsProcessed;
	private int objectsDeclined;
}

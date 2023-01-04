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
public class ResponsePost {
	
	private List<Long> ids;
	private int objectProcessed;
	private int objectDeclined;
}

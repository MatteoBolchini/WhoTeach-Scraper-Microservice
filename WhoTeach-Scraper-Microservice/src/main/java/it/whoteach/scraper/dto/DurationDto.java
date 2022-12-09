package it.whoteach.scraper.dto;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DurationDto {
	@CsvBindByName(column = "duration")
	private String duration;
}

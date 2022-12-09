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
public class MaxAgeDto {
	@CsvBindByName(column = "max_age")
	private String maxAge;
}

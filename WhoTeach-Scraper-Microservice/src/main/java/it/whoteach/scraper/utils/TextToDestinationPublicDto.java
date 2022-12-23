package it.whoteach.scraper.utils;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import it.whoteach.scraper.dto.DestinationPublicDto;

public class TextToDestinationPublicDto extends AbstractCsvConverter {

	@Override
	public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		DestinationPublicDto d = new DestinationPublicDto(value.trim());
		return d;
	}

}

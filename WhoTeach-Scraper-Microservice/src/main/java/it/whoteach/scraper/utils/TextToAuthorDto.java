package it.whoteach.scraper.utils;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import it.whoteach.scraper.dto.AuthorDto;

public class TextToAuthorDto extends AbstractCsvConverter {

	@Override
	public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		AuthorDto a = new AuthorDto(value);
		return a;
	}

}

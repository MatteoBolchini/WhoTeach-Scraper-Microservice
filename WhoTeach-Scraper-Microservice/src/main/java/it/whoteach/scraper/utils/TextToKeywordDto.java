package it.whoteach.scraper.utils;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import it.whoteach.scraper.dto.KeywordDto;

public class TextToKeywordDto extends AbstractCsvConverter {

	@Override
	public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		KeywordDto k = new KeywordDto(value.trim());
		return k;
	}

}

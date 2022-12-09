package it.whoteach.scraper.csvConverter;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import it.whoteach.scraper.dto.SubdomainDto;

public class TextToSubdomainDto extends AbstractCsvConverter {

	@Override
	public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		SubdomainDto s = new SubdomainDto(value);
		return s;
	}

}

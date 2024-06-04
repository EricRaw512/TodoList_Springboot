package com.eric.todolist.service;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eric.todolist.mappingstrategy.CustomMappingStrategy;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@Service
public class CSVService{
	
	public <T> void beanToCsv(PrintWriter writer,  List<T> items, CustomMappingStrategy<T> mappingStrategy) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
    	StatefulBeanToCsv<T> csvWriter= new StatefulBeanToCsvBuilder<T>(writer)
    			.withMappingStrategy(mappingStrategy)
    			.withQuotechar(ICSVWriter.NO_QUOTE_CHARACTER)
    			.withSeparator(';')
    			.build();
    	
    	csvWriter.write(items);
	}
}

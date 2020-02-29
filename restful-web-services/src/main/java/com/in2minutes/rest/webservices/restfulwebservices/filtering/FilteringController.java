package com.in2minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public SomeBean retrieveSomeBean() {
		return new SomeBean("value1", "value2","value3");
	}
	
	@GetMapping("/filtering-list")
	public List<SomeBean> retrieveListofSomeBean() {
		return Arrays.asList(new SomeBean("Value11","Value21","Value31"),
				new SomeBean("Value12","Value22","Value32"),
				new SomeBean("Value13","Value23","Value33"));
	}
	
	@GetMapping("/filtering-dynamicly")
	public MappingJacksonValue retrieveSomeBeanDynamicly() {
		SomeBean someBean= new SomeBean("value1", "value2","value3");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		MappingJacksonValue mapping = new  MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
	@GetMapping("/filtering-dynamicly2")
	public MappingJacksonValue retrieveSomeBeanDynamicly2() {
		SomeBean someBean= new SomeBean("value1", "value2","value3");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		MappingJacksonValue mapping = new  MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		
		return mapping;
	}
}

package spring.rest.exampleproject.airport.metarservice.repository;

import spring.rest.exampleproject.airport.metarservice.model.Metar;

interface CustomizedMetarRepository {
	
	Metar findLatestMetarEntry(String icaoCode);
		
}

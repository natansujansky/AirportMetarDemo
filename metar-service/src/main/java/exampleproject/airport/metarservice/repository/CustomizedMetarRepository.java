package exampleproject.airport.metarservice.repository;

import exampleproject.airport.metarservice.model.Metar;

interface CustomizedMetarRepository {
	
	Metar findLatestMetarEntry(String icaoCode);
		
}

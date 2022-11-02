package spring.rest.exampleproject.airport.metarservice.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import spring.rest.exampleproject.airport.metarservice.model.Metar;
import spring.rest.exampleproject.airport.metarservice.repository.MetarRepository;

@Service
@Transactional
public class MetarService {

	@Autowired
	private MetarRepository repository;

	public List<Metar> fetchAllMetarEntries() {
		return repository.findAll();
	}

	public void insertMetarEntry(String icaoCode, String data) {
		Metar metar = new Metar(icaoCode, data);
		repository.save(metar);
	}

	public List<Metar> fetchAllMetarEntriesForSingleAirport(String icaoCode) {
		if (repository.existsMetarByIcaoCode(icaoCode)) {
			return repository.findByIcaoCode(icaoCode).get();
		} else {
			throw new NoSuchElementException("No METAR data stored for airport with ICAO code: " + icaoCode);
		}
	}

	public Metar fetchLatestMetarEntry(String icaoCode) {
		if (repository.existsMetarByIcaoCode(icaoCode)) {
			return repository.findLatestMetarEntry(icaoCode);
		} else {
			throw new NoSuchElementException("No METAR data stored for airport with ICAO code: " + icaoCode);
		}
	}

	public void deleteMetarEntry(String icaoCode) {
		if (repository.existsMetarByIcaoCode(icaoCode)) {
			repository.deleteByIcaoCode(icaoCode);
		} else {
			throw new NoSuchElementException("No METAR data stored for airport with ICAO code: " + icaoCode);
		}
	}

}

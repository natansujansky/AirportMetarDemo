package exampleproject.airport.metarservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import exampleproject.airport.metarservice.model.Metar;
import exampleproject.airport.metarservice.repository.MetarRepository;

public class MetarServiceTest {
	
	private MetarService metarServiceTest;
	
	private MetarRepository metarRepositoryMock = Mockito.mock(MetarRepository.class);
	
	List<Metar> resultListMock = new ArrayList<Metar>();
	
	private Metar metar1, metar2, metar3;
	
	@BeforeEach
	void initMetarService() {
		metarServiceTest = new MetarService(metarRepositoryMock);
		metar1 = new Metar("AAAA", "METAR entry 1");
		metar2 = new Metar("AAAA", "Metar Entry 2");
		metar3 = new Metar ("AAAA", "METAR entry 3");
		resultListMock.add(metar1);
		resultListMock.add(metar2);
		resultListMock.add(metar3);
	}
	
	@Test
	void fetchAllMetarEntriesForSingleAirportTest() {
		when(metarRepositoryMock.existsMetarByIcaoCode("AAAA")).thenReturn(true);
		when(metarRepositoryMock.findByIcaoCode("AAAA")).thenReturn(Optional.of(resultListMock));
		assertEquals(metarServiceTest.fetchAllMetarEntriesForSingleAirport("AAAA"), resultListMock);
	}
	
	@Test
	void fetchAllMetarEntriesForSingleAirportTestNotFound() {
		when(metarRepositoryMock.existsMetarByIcaoCode("AAAA")).thenReturn(false);
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> metarServiceTest.fetchAllMetarEntriesForSingleAirport("AAAA"));
		assertEquals("No METAR data stored for airport with ICAO code: AAAA", exception.getMessage());
	}
	
	@Test
	void fetchLatestMetarEntryTest() {
		when(metarRepositoryMock.existsMetarByIcaoCode("AAAA")).thenReturn(true);
		when(metarRepositoryMock.findLatestMetarEntry("AAAA")).thenReturn(metar3);
		assertEquals(metarServiceTest.fetchLatestMetarEntry("AAAA"), metar3);
	}
	
	@Test
	void fetchLatestMetarEntryTestNotFound() {
		when(metarRepositoryMock.existsMetarByIcaoCode("AAAA")).thenReturn(false);
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> metarServiceTest.fetchLatestMetarEntry("AAAA"));
		assertEquals("No METAR data stored for airport with ICAO code: AAAA", exception.getMessage());
	}
	
	@Test
	void deleteMetarEntryTest() {
		when(metarRepositoryMock.existsMetarByIcaoCode("AAAA")).thenReturn(true);
		metarServiceTest.deleteMetarEntry("AAAA");
		Mockito.verify(metarRepositoryMock).deleteByIcaoCode("AAAA");
	}
	
	@Test
	void deleteMetarEntryTestNotFound() {
		when(metarRepositoryMock.existsMetarByIcaoCode("AAAA")).thenReturn(false);
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> metarServiceTest.deleteMetarEntry("AAAA"));
		assertEquals("No METAR data stored for airport with ICAO code: AAAA", exception.getMessage());
	}
}

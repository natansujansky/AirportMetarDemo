package spring.rest.exampleproject.airport.metarservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.rest.exampleproject.airport.metarservice.model.Metar;

public interface MetarRepository extends JpaRepository<Metar, Integer>, CustomizedMetarRepository {
	
	boolean existsMetarByIcaoCode(String icaoCode);
	
	Optional<List<Metar>> findByIcaoCode(String icaoCode);
	
	void deleteByIcaoCode(String icaoCode);

}

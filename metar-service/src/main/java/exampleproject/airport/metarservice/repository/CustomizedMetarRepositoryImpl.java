package exampleproject.airport.metarservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import exampleproject.airport.metarservice.model.Metar;

public class CustomizedMetarRepositoryImpl implements CustomizedMetarRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Metar findLatestMetarEntry(String icaoCode) {
		return (Metar) entityManager.createNativeQuery("select * from metar where icao_code = '" + icaoCode + "' order by created_at desc;",
				Metar.class).setMaxResults(1).getSingleResult();
	}
}

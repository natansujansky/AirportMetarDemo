package spring.rest.exampleproject.airport.metarservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.rest.exampleproject.airport.metarservice.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
	
	boolean existsSubscriptionByIcaoCode(String icaoCode);
	
	Optional<Subscription> findByIcaoCode(String icaoCode);
	
	void deleteByIcaoCode(String icaoCode);

}

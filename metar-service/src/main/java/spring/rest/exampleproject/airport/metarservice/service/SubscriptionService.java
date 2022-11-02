package spring.rest.exampleproject.airport.metarservice.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import spring.rest.exampleproject.airport.metarservice.model.Subscription;
import spring.rest.exampleproject.airport.metarservice.repository.SubscriptionRepository;

@Service
@Transactional
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository repository;

	public List<Subscription> fetchAllSubscriptions() {
		return repository.findAll();
	}

	public void insertSubscription(Subscription subscription) {
		if (repository.existsSubscriptionByIcaoCode(subscription.getIcaoCode())) {
			throw new IllegalArgumentException(
					"Already subscribed to airport with ICAO code " + subscription.getIcaoCode());
		} else {
			repository.save(subscription);
		}
	}

	public Subscription fetchSubscription(String icaoCode) {
		return repository.findByIcaoCode(icaoCode).get();
	}

	public void deleteSubscription(String icaoCode) {
		if (repository.existsSubscriptionByIcaoCode(icaoCode)) {
			repository.deleteByIcaoCode(icaoCode);
		} else {
			throw new NoSuchElementException("Subscription for airport with ICAO code " + icaoCode + " not found!");
		}
	}
}

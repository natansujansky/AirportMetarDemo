package exampleproject.airport.metarservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import exampleproject.airport.metarservice.model.Subscription;
import exampleproject.airport.metarservice.repository.SubscriptionRepository;

class SubscriptionServiceTest {

	private SubscriptionService subServiceTest;

	private SubscriptionRepository subRepositoryMock = Mockito.mock(SubscriptionRepository.class);

	@BeforeEach
	void initSubService() {
		subServiceTest = new SubscriptionService(subRepositoryMock);
	}

	@Test
	void insertSubscriptionTest() {
		Subscription subscription = new Subscription("AAAA");
		when(subRepositoryMock.existsSubscriptionByIcaoCode("AAAA")).thenReturn(false);
		subServiceTest.insertSubscription(subscription);
		Mockito.verify(subRepositoryMock).save(subscription);
	}

	@Test
	void insertSubscriptionTestAlreadyPresent() {
		Subscription subscription = new Subscription("AAAA");
		when(subRepositoryMock.existsSubscriptionByIcaoCode("AAAA")).thenReturn(true);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> subServiceTest.insertSubscription(subscription));
		assertEquals("Already subscribed to airport with ICAO code AAAA", exception.getMessage());
	}

	@Test
	void deleteSubscriptionTest() {
		when(subRepositoryMock.existsSubscriptionByIcaoCode("AAAA")).thenReturn(true);
		subServiceTest.deleteSubscription("AAAA");
		Mockito.verify(subRepositoryMock).deleteByIcaoCode("AAAA");
	}

	@Test
	void deleteSubscriptionTestNotFound() {
		when(subRepositoryMock.existsSubscriptionByIcaoCode("AAAA")).thenReturn(false);
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> subServiceTest.deleteSubscription("AAAA"));
		assertEquals("Subscription for airport with ICAO code AAAA not found!", exception.getMessage());
	}
}

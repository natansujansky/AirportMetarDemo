package spring.rest.exampleproject.airport.metarservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import spring.rest.exampleproject.airport.metarservice.model.Subscription;
import spring.rest.exampleproject.airport.metarservice.service.SubscriptionService;

@Validated
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

	@Autowired
	SubscriptionService subService;

	@GetMapping("")
	public List<Subscription> getAll() {
		return subService.fetchAllSubscriptions();
	}

	@GetMapping("/{icaoCode}")
	public Subscription getOne(@Pattern(regexp = "[A-Z0-9]{4}") @PathVariable String icaoCode) {
		return subService.fetchSubscription(icaoCode);
	}

	@PostMapping("")
	public void post(@Valid @RequestBody Subscription sub) {
		if (java.util.regex.Pattern.compile("[A-Z0-9]{4}").matcher(sub.getIcaoCode()).matches()) {
			subService.insertSubscription(sub);
		} else {
			throw new IllegalArgumentException("Incorrect payload format!");
		}
	}

	@DeleteMapping("/{icaoCode}")
	public ResponseEntity<?> delete(@Pattern(regexp = "[A-Z0-9]{4}") @PathVariable String icaoCode) {
		subService.deleteSubscription(icaoCode);
		return ResponseEntity.noContent().build();
	}
}

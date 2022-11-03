package spring.rest.exampleproject.airport.metarservice.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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

import jakarta.validation.constraints.Pattern;
import spring.rest.exampleproject.airport.metarservice.model.Metar;
import spring.rest.exampleproject.airport.metarservice.service.MetarService;

@Validated
@RestController
@RequestMapping("/airport")
public class MetarController {

	@Autowired
	MetarService metarService;

	@GetMapping("")
	public List<Metar> getAll() {
		return metarService.fetchAllMetarEntries();
	}

	@GetMapping("/{icaoCode}")
	public List<Metar> getAllWithSameIcaoCode(@Pattern(regexp = "[A-Z0-9]{4}") @PathVariable String icaoCode) {
		return metarService.fetchAllMetarEntriesForSingleAirport(icaoCode);
	}

	@GetMapping("/{icaoCode}/METAR")
	public Metar getLatestAirportMetarData(@Pattern(regexp = "[A-Z0-9]{4}") @PathVariable String icaoCode) {
		return metarService.fetchLatestMetarEntry(icaoCode);
	}

	@PostMapping("/{icaoCode}/METAR")
	public void post(@Pattern(regexp = "[A-Z0-9]{4}") @PathVariable String icaoCode,
			@RequestBody String data) {
		JSONObject jsonData = (JSONObject) JSONValue.parse(data);
		try {
			String metarData = (String) jsonData.get("data");
			if (metarData == null) {
				throw new IllegalArgumentException("Incorrect payload format!");
			} else {
				metarService.insertMetarEntry(icaoCode, metarData);
			}
		} catch (ClassCastException ex) {
			throw new IllegalArgumentException("Incorrect payload format!");
		}
	}
	
	@DeleteMapping("/{icaoCode}")
	public ResponseEntity<?> delete(@Pattern(regexp = "[A-Z0-9]{4}") @PathVariable String icaoCode) {
		metarService.deleteMetarEntry(icaoCode);
		return ResponseEntity.noContent().build();
	}
}

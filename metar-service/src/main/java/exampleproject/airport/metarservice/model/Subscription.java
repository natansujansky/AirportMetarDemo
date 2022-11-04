package exampleproject.airport.metarservice.model;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "subscriptions")
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NaturalId
	@Valid
	@NotNull(message = "Must provide icaoCode")
	@Size(min=4, max=4)
	@Column(columnDefinition="char(4) not null", unique=true)
	private String icaoCode;
	
	public Subscription() {
	}

	public Subscription(String icaoCode) {
		this.icaoCode = icaoCode;
	}

	public String getIcaoCode() {
		return icaoCode;
	}
}

package io.github.crystalplanetcode.demo.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import io.github.crystalplanetcode.aischema.core.annotation.AIField;
import io.github.crystalplanetcode.aischema.core.annotation.AIRelation;
import io.github.crystalplanetcode.aischema.core.annotation.AITable;

@Entity
@AITable(
	value = "Customers",
	description = "Customers who place orders in the system.",
	aliases = {"clients", "buyers", "users"},
	granularity = "Each record represents a single customer."
)
public class Customer {

	@Id
	private Long id;

	@AIField(
		value = "First name",
		examples = {"John", "Jane"},
		filterable = true
	)
	private String firstName;

	@AIField(
		value = "Last name",
		examples = {"Smith", "Johnson"},
		filterable = true
	)
	private String lastName;

	@OneToMany(mappedBy = "customer")
	@AIRelation("One customer can place many orders")
	private List<Order> orders;
}

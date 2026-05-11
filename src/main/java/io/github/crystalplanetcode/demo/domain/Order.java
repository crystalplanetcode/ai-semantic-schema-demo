package io.github.crystalplanetcode.demo.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import io.github.crystalplanetcode.aischema.core.annotation.AIEnum;
import io.github.crystalplanetcode.aischema.core.annotation.AIField;
import io.github.crystalplanetcode.aischema.core.annotation.AIMetric;
import io.github.crystalplanetcode.aischema.core.annotation.AIRelation;
import io.github.crystalplanetcode.aischema.core.annotation.AITable;

@Entity
@Table(name = "orders") // "order" is a reserved keyword in SQL, so we specify a different table name
@AITable(
	value = "Orders",
	description = "Customer orders. Each order is placed by one customer and contains multiple products via order items.",
	aliases = {"purchases", "sales"},
	granularity = "Each record represents a single order."
)
@AIMetric(
	name = "total_revenue",
	label = "Total Revenue",
	description = "Sum of all order total amounts",
	aggregation = "SUM",
	field = "totalAmount"
)
public class Order {

	public enum OrderStatus {
		NEW,
		PAID,
		SHIPPED,
		CANCELLED
	}

	@Id
	private Long id;

	@AIField(
		value = "Order date",
		description = "Date when the order was placed",
		examples = {"2026-04-08"},
		filterable = true
    )
	private LocalDate orderDate;

	@AIField(
		value = "Total amount",
		description = "Total value of the order",
		examples = {"150.00"},
		aggregatable = true
	)
	private BigDecimal totalAmount;

	@Enumerated(EnumType.STRING)
	@AIField(
		value = "Status",
		description = "Current status of the order",
		filterable = true
	)
	@AIEnum({
		@AIEnum.Entry(key = "NEW", description = "Order created"),
		@AIEnum.Entry(key = "PAID", description = "Order paid"),
		@AIEnum.Entry(key = "SHIPPED", description = "Order shipped"),
		@AIEnum.Entry(key = "CANCELLED", description = "Order cancelled")
	})
	private OrderStatus status;

	@ManyToOne
	@AIRelation("Each order belongs to one customer")
	private Customer customer;

	@OneToMany(mappedBy = "order")
	@AIRelation("One order contains multiple order items (products with quantities)")
	private List<OrderItem> items;
}

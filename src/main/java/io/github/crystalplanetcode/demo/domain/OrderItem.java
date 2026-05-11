package io.github.crystalplanetcode.demo.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import io.github.crystalplanetcode.aischema.core.annotation.AIField;
import io.github.crystalplanetcode.aischema.core.annotation.AIMetric;
import io.github.crystalplanetcode.aischema.core.annotation.AIRelation;
import io.github.crystalplanetcode.aischema.core.annotation.AITable;

@Entity
@AITable(
	value = "Order Items",
	description = "Items within an order. Each record links a product with an order and defines quantity and price.",
	aliases = {"order lines", "line items"},
	granularity = "Each record represents a single product within an order."
)
@AIMetric(
	name = "total_items_sold",
	label = "Total Items Sold",
	description = "Total quantity of sold items",
	aggregation = "SUM",
	field = "quantity"
)
public class OrderItem {

	@Id
	private Long id;

	@AIField(
		value = "Quantity",
		description = "Number of units of the product",
		examples = {"1", "3"},
		aggregatable = true
	)
	private Integer quantity;

	@AIField(
		value = "Unit price",
		description = "Price per single unit at time of purchase",
		examples = {"50.00"},
		aggregatable = true
	)
	private BigDecimal unitPrice;

	@ManyToOne
	@AIRelation("Each item belongs to one order")
	private Order order;

	@ManyToOne
	@AIRelation("Each item refers to one product")
	private Product product;
}

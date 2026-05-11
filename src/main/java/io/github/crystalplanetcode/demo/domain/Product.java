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
	value = "Products",
	description = "Products available for purchase. Each product belongs to exactly one category.",
	aliases = {"items", "goods", "store products"},
	granularity = "Each record represents a single product."
)
@AIMetric(
	name = "avg_product_price",
	label = "Average Product Price",
	description = "Average price of products",
	aggregation = "AVG",
	field = "price"
)
public class Product {

	@Id
	private Long id;

	@AIField(
		value = "Product name",
		description = "Name of the product",
		examples = {"Laptop", "Phone", "T-shirt"},
		filterable = true
	)
	private String name;

	@AIField(
		value = "Price",
		description = "Base price of the product",
		examples = {"999.99"},
		aggregatable = true
	)
	private BigDecimal price;

	@ManyToOne
	@AIRelation("Each product belongs to one category")
	private Category category;
}

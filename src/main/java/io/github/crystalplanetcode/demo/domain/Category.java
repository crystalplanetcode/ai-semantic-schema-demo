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
	value = "Categories",
	description = "Product categories used to group products. Categories are used for filtering, aggregation and reporting (e.g. revenue by category).",
	aliases = {"product categories", "groups", "product groups"},
	granularity = "Each record represents a single product category."
)
public class Category {

	@Id
	private Long id;

	@AIField(
		value = "Category name",
		description = "Name of the category used for grouping products",
		examples = {"Electronics", "Books", "Clothing"},
		filterable = true
	)
	private String name;

	@OneToMany(mappedBy = "category")
	@AIRelation("One category can contain many products")
	private List<Product> products;
}

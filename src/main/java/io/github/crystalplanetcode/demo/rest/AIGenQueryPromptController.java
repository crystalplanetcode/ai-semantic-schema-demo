package io.github.crystalplanetcode.demo.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.crystalplanetcode.aischema.hibernate.spi.AISemanticContext;
import io.github.crystalplanetcode.aischema.hibernate.spi.AISemanticContextProvider;
import io.github.crystalplanetcode.demo.domain.Category;
import io.github.crystalplanetcode.demo.domain.Customer;
import io.github.crystalplanetcode.demo.domain.Order;
import io.github.crystalplanetcode.demo.domain.OrderItem;
import io.github.crystalplanetcode.demo.domain.Product;

@RestController
@RequestMapping("/aiGenQuery")
public class AIGenQueryPromptController {

	private final Logger log = LoggerFactory.getLogger(AIGenQueryPromptController.class);

	private final ChatClient chatClient;
	private final AISemanticContextProvider aiSemanticContextProvider;
	private final JdbcTemplate jdbcTemplate;

	@Value("classpath:/templates/prompt-template.st")
	private Resource promptTemplateResource;

	public AIGenQueryPromptController(ChatClient.Builder builder, AISemanticContextProvider aiSemanticContextProvider,
			JdbcTemplate jdbcTemplate) {
		this.chatClient = builder.build();
		this.aiSemanticContextProvider = aiSemanticContextProvider;
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostMapping("/generate")
	public ResponseEntity<?> testPrompt(@RequestBody String prompt) {

		AISemanticContext aiSemanticContext = aiSemanticContextProvider.buildContext(Customer.class, Order.class,
				OrderItem.class, Product.class, Category.class);

		String semanticContextYaml = aiSemanticContext.toYaml();

		log.info("Semantic context:\n{}", semanticContextYaml);

		String chatClientResponse = chatClient
				.prompt()
				.user(userSpec -> userSpec
						.text(promptTemplateResource)
						.param("semantic_ddl", semanticContextYaml)
						.param("question", prompt))
				.call()
				.content();

		log.info("Chat client response:\n{}", chatClientResponse);

		return ResponseEntity.ok(simplisticQueryExecute(chatClientResponse));

	}

	private String simplisticQueryExecute(String sqlResponse) {
		String sql = sqlResponse.trim();

		if (sql.toUpperCase().contains("INSERT") || sql.toUpperCase().contains("UPDATE")
				|| sql.toUpperCase().contains("DELETE")) {

			return "Only SELECT queries are allowed.";
		}

		if (sql.toUpperCase().contains("SELECT")) {
			sql = sql.substring(sql.toUpperCase().indexOf("SELECT"));
		}

		List<Map<String, Object>> results = null;
		try {
			results = jdbcTemplate.queryForList(sql);
		} catch (DataAccessException e) {
			log.error("Error executing SQL query: {}", e.getMessage());

			return "Error executing SQL query: " + e.getMessage();
		}

		String resultString = results.stream()
				.map(row -> row.values().toString())
				.reduce("", (a, b) -> a + "\n" + b);

		log.info("Query results:\n{}", resultString);
		String strResult = String.format("Generated SQL: \n\n%s\n\nSQL Results:\n%s\n", sql, resultString);

		return strResult;
	}

}


# AI Semantic Schema Demo

This demo project showcases the practical use of the **AI Semantic Schema** library: https://github.com/crystalplanetcode/ai-semantic-schema

## What this demo does and which parts are in play?

1. **Spring JPA** creates domain model from annotated POJOs with relationships (example data are loaded during start):
```
Customer (1) ── (N) Order (1) ── (N) OrderItem (N) ── (1) Product (N) ── (1) Category
```
2. **AI Semantic Schema** extracts the actual JPA / Hibernate data model (exact names of tables, fields etc.), enriches it (with semantic annotations) and combines everything into AI Schema Context.
3. **Spring AI** takes the AI context and human natural language questions regarding the underlying data model (delivered by REST endpoint) and processes it with use of LLM into the valid SQL query.
4. **Spring Web** REST endpoint is responsible for receiving natural language question, processing it through the pipeline and returning the results. By default the REST endpoint listens on port 8080. You are free to use any client tool you like or use simple curl to communicate with it.

**For example:** the following natural language question: **"Which customers have orders in status shipped for products, which belong to category Electronics?"** will be transformed into SQL statement using this curl command:

```curl
curl --location 'http://localhost:8080/aiGenQuery/generate' \
--header 'Content-Type: text/plain' \
--data 'Which customers have orders in status shipped for products, which belong to category Electronics?'
```

If everything is configured correctly, you should expect the following result:

```SQL
Generated SQL: 

SELECT DISTINCT
  c.first_name,
  c.last_name
FROM customer c
JOIN orders o ON c.id = o.customer_id
JOIN order_item oi ON o.id = oi.order_id
JOIN product p ON oi.product_id = p.id
JOIN category cat ON p.category_id = cat.id
WHERE
  o.status = 'SHIPPED' AND cat.name = 'Electronics'

SQL Results:

[John, Smith]
[Paul, Johnson]
```

## What do I need this library for?
It does a couple of things automatically for you: 
 - extracts schema from your JPA annotated POJOs, 
 - extracts semantic information (AI annotations),
 - combines all of them into concise semantic context useful for LLMs.


### ORM models are great for persistence — but not for AI, have a look at this:

```SQL
orders(id, total_amount, customer_id)
```

An LLM has to guess:

what is total_amount?
is it net or gross?
what does customer_id represent?

Sometimes it guesses right. Often it doesn’t.

AI Semantic Schema solves this by making meaning explicit:

```java
@AITable("Represents a commercial transaction initiated by a customer")
class Order {

    @AIField("Total financial value of the order, including all items and applicable charges")
    BigDecimal totalAmount;
}
```

Now the LLM doesn’t guess — **it knows**.

### Do I need to add all those annotations?
You can go without any annotations at all if you wish, but the context will be no more than just simple DDL, leaving your LLM puzzled...
The more detailed and accurate annotations are the better quality AI context you get.
What's good for LLM is good for documentation as well.
It's worth it after all.

## What this library is and what it is not trying to be...

### This library is intentionally:

 - Explicit over implicit
 - No heuristics. No guessing. No hidden logic.
 - If something has meaning — you define it.

### Deterministic

The same model always produces the same semantic output.
No surprises. No “why did it interpret it like that?”.

### Minimalistic

No DSLs. No schema duplication. Just annotations on your existing JPA model.

### AI-friendly by design

Output is structured and ready to be used directly in prompts or AI pipelines.

### Why no heuristics?

Many tools try to infer meaning from:

 - field names
 - types
 - conventions

This quickly becomes:

 - unpredictable
 - hard to debug
 - wrong in edge cases

AI Semantic Schema takes a different approach:

Domain knowledge should come from developers — not guesses.

### AI-assisted workflow (recommended)

You don’t have to write all annotations manually.
Use tools like GitHub Copilot or other AI assistants:

"Add AI Semantic Schema annotations to this entity"

#### In practice:

Annotations are generated quickly, only minor corrections are needed. You stay in full control.

## Requirements
- Java 17+ (Java 21+ recommended)
- Build of the AI Semantic Schema library published to mavenLocal
- This example uses Spring JPA and Spring AI (see **build.gradle** for details)

## Library installation
1. Clone the repository:
   ```bash
   git clone https://github.com/crystalplanetcode/ai-semantic-schema.git
   cd ai-semantic-schema
   ```
2. Build the library and publish to **mavenLocal** (you don't need to install anything to do it):
   ```bash
   ./gradlew build
   ./gradlew clean publishToMavenLocal
   ```

## Demo installation
1. Clone the repository:
   ```bash
   git clone https://github.com/crystalplanetcode/ai-semantic-schema-demo.git
   cd ai-semantic-schema-demo
   ```
2. Build the demo project:
   ```bash
   ./gradlew build
   ```
   You may want to use this command if you introduced any changes to the library in mavenLocal
   ```bash
   ./gradlew --refresh-dependencies clean build
   ```

## Demo configuration
You will need to configure connection to AI Large Language Model to make it work.
At the time of this publication, the most convenient free-of-charge LLM available was from Google.
You can get the free API key there:
```URL
https://aistudio.google.com/api-keys
```

then you need to export it as an environment variable, e.g. on Linux systems:

```bash
export GOOGLE_GENAI_API_KEY=...
```

Check if variable has been setup correctly:

```bash
echo $GOOGLE_GENAI_API_KEY
```

The following **application.properties** are applicable for Google LLM:
~~~
spring.ai.google.genai.api-key=${GOOGLE_GENAI_API_KEY}
spring.ai.google.genai.chat.model=gemini-3.1-flash-lite

spring.ai.google.genai.chat.temperature=0.1
spring.ai.google.genai.chat.max-output-tokens=500
spring.ai.google.genai.chat.top-p=0.95
spring.ai.google.genai.chat.top-k=2
spring.ai.google.genai.chat.stop-sequences=;
spring.ai.google.genai.chat.presence-penalty=0.0
spring.ai.google.genai.chat.frequency-penalty=0.0
~~~

You can decide to switch to different model or model provider at any time.
If your preference is OpenAI or locally served Ollama, you can go for it (with Spring AI or native client). 

Please note that Google is changing the lineup of models quite often... You can check current available models by invoking the following curl:

```bash
curl \
  -H "x-goog-api-key: $GOOGLE_GENAI_API_KEY" \
  https://generativelanguage.googleapis.com/v1beta/models
```

## Running
To run the application locally (default port: 8080):
```bash
./gradlew bootRun
```

## Project Structure
- `src/main/java/io/github/crystalplanetcode/demo/domain/` — domain entities
- `src/main/java/io/github/crystalplanetcode/demo/rest/` — REST controllers
- `src/main/resources/` — configuration files, initial data and LLM prompt template: **prompt-template.st**
- `build.gradle` — Gradle configuration

## License

Licensed under the Apache License, Version 2.0. See the LICENSE file in the project root.

## Author
**AI Semantic Schema Demo** — Marcin Nowicki [Crystal Planet Code]

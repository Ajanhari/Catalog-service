package com.store.catalogservice;

import com.store.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

// annotation configured to provide a full Spring application context, including a running server that exposes its services through a random port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Provides a setup for testing Spring Boot applications
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoads() { // Empty test used to verify that the application context is loaded correctly
    }

    @Test
    void whenPostRequestThenBookCreated() {
        //var expectedBook = new Book("1231231231", "Title", "Author", 9.90);
        var expectedBook = Book.of("1231231231", "Title", "Author", 9.90, "Ajan");

        webTestClient.post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
        });
    }

}

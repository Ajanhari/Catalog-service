package com.store.catalogservice.loader;

import com.store.catalogservice.domain.Book;
import com.store.catalogservice.repo.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/*
Instead of using profiles as feature flags, a more scalable and structured approach is defining custom properties
to configure functionality, and relying on annotations such as @ConditionalOnProperty and @ConditionalOnCloudPlatform to control
when certain beans should be loaded into the Spring application context.
That’s one of the foundations of Spring Boot auto-configuration.
For example, you could define a polar.testdata.enabled custom property and
use the @ConditionalOnProperty(name = "polar.testdata .enabled", havingValue = "true") annotation on the BookDataLoader class.
 */
@Component
@Profile("testData") //  It will be registered only when the testdata profile is active.
public class BookDataLoader {

    private final BookRepository bookRepository;
    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        var book1 = new Book("1234567891", "Northern Lights",
                "Lyra Silverstar", 9.90);
        var book2 = new Book("1234567892", "Polar Journey",
                "Iorek Polarson", 12.90);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}

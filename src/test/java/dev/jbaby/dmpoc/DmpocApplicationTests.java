package dev.jbaby.dmpoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@Disabled("For manual execution only for now.")
class DmpocApplicationTests {

    @Test
    void contextLoads() {
    }

}

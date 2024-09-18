package kringlooptilburg.nl.productimageservice;

import kringlooptilburg.nl.productimageservice.config.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfiguration.class)
class ProductImageServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}

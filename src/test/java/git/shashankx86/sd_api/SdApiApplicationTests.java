package git.shashankx86.sd_api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Disabled("Temporarily disabled while fixing circular dependency")
@SpringBootTest
@TestPropertySource(properties = {
    "env.path=src/test/resources/.env.test"
})
class SdApiApplicationTests {
    @Test
    void contextLoads() {
    }
}

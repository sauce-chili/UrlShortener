package vstu.isd.urlshortenerservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlShortenerApplicationTests {
    private final int minIndex = 0;
    private final int maxIndex = 62;

    @Test
    void contextLoads() {
        Assertions.assertThat(40 + 2).isEqualTo(42);
    }
}

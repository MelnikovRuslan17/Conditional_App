package ru.netology.conditional_app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalAppApplicationTests {

@Autowired
    TestRestTemplate restTemplate;

    private GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeEach
    public void setUp(){
        devApp.start();
        prodApp.start();
    }
    @Test
    void contextLoadsDev() {
        Integer devPort = devApp.getMappedPort(8080);
        ResponseEntity<String> forEntityResult = restTemplate.getForEntity("http://localhost:" + devPort + "/profile", String.class);
        String expected = "Current profile is dev";
        Assertions.assertEquals(expected, forEntityResult.getBody());
    }
    @Test
    void contextLoadsProd() {
        Integer prodPort = prodApp.getMappedPort(8081);
        ResponseEntity<String> forEntityResult = restTemplate.getForEntity("http://localhost:" + prodPort + "/profile", String.class);
        String expected = "Current profile is production";
        Assertions.assertEquals(expected, forEntityResult.getBody());
    }
}

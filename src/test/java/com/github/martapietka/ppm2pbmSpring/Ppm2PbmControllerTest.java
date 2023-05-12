package com.github.martapietka.ppm2pbmSpring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Ppm2PbmControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnCorrectImage() throws Exception {

        String imageName = "3x3.ppm";
        byte[] expectedBytes = Files.readAllBytes(Paths.get("src/test/resources/" + imageName));
        String expectedContentType = "image/x-portable-graymap";
        String url = "http://localhost:" + port + "/image/" + imageName;

        //WHEN
        ResponseEntity response = restTemplate.getForEntity(url, byte[].class);

        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType().toString()).isEqualTo(expectedContentType);
        assertThat(response.getBody()).isEqualTo(expectedBytes);
    }
}
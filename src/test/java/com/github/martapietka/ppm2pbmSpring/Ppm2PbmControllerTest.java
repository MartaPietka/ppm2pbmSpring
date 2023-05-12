package com.github.martapietka.ppm2pbmSpring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class Ppm2PbmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnCorrectImage() throws Exception {

        String imageName = "3x3.ppm";
        byte[] expectedBytes = Files.readAllBytes(Paths.get("src/test/resources/" + imageName));
        String expectedContentType = "image/x-portable-graymap";

        this.mockMvc.perform(get("/image/{imageName}", imageName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType(expectedContentType)))
                .andExpect(content().bytes(expectedBytes));
    }
}
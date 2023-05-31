package com.github.martapietka.ppm2pbmSpring;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;

@SpringBootTest
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Test
    public void testGetConvertedInputStream() throws Exception {

        String imageName = "3x3.ppm";

        String expectedOutputString = """
                P1
                3 3
                1 0 1
                0 1 0
                0 0 1
                """;

        try (InputStream inputStream = getClass().getResourceAsStream("/" + imageName)) {

            InputStream convertedInputStream = imageService.getConvertedInputStream(inputStream);
            byte[] convertedBytes = convertedInputStream.readAllBytes();

            Assertions.assertThat(convertedBytes).asString().isEqualTo(expectedOutputString);
        }
    }

//    @Test
//    public void testGetImage() throws IOException {
//
//        String imageName = "3x3.ppm";
//
//        try (InputStream inputStream = getClass().getResourceAsStream("/" + imageName)) {
//
//            Resource result = imageService.getImage(inputStream);
//            String filename = result.getFilename();
//
//            Assertions.assertThat(result).isInstanceOf(ByteArrayResource.class);
//            Assertions.assertThat(filename).endsWith(".png");
//        }
//    }
}
package com.github.martapietka.ppm2pbmSpring;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;

import java.io.InputStream;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class Ppm2PbmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Captor
    private ArgumentCaptor<InputStream> inputStreamArgumentCaptor;

    @Test
    public void shouldReturnCorrectImage() throws Exception {

        String imageName = "3x3.ppm";
        String expectedContentType = "image/png";

        try (InputStream inputStream = getClass().getResourceAsStream("/" + imageName)) {

            byte[] expectedBytes = inputStream.readAllBytes();

            given(imageService.getImage(any(InputStream.class))).willReturn(new ByteArrayResource(expectedBytes));

            this.mockMvc.perform(multipart("/convert")
                            .file(new MockMultipartFile("file", imageName, "image/png", expectedBytes)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.parseMediaType(expectedContentType)))
                    .andExpect(content().bytes(expectedBytes));

            verify(imageService).getImage(inputStreamArgumentCaptor.capture());
        }
    }
}
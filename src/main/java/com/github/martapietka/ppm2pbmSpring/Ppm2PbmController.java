package com.github.martapietka.ppm2pbmSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Ppm2PbmController {

    @Autowired
    private ImageService imageService;

    @GetMapping(value = "/image/{imageName}", produces = "image/x-portable-graymap")
    public ResponseEntity getImage(@PathVariable(value = "imageName") String imageName) {

        try {
            byte[] imageBytes = imageService.getImage(imageName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/x-portable-graymap"))
                    .body((imageBytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


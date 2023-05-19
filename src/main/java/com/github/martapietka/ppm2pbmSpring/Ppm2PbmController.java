package com.github.martapietka.ppm2pbmSpring;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
public class Ppm2PbmController {

    private final ImageService imageService;

    public Ppm2PbmController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/image/{imageName}", produces = "image/png")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {

        try {
            Resource imageResource = imageService.getImage(imageName);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/png"))
                    .body(imageResource);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading image", e);
        }
    }
}


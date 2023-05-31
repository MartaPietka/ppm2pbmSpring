package com.github.martapietka.ppm2pbmSpring;

import com.github.martapietka.ppm2pbm.InvalidImageException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class Ppm2PbmController {

    private final ImageService imageService;

    public Ppm2PbmController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/convert", produces = "image/png")
    public ResponseEntity<Resource> convertPpmToPbm(@RequestParam("file")MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            Resource imageResource = imageService.getImage(imageService.getConvertedInputStream(inputStream));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/png"))
                    .body(imageResource);

        } catch (IOException | InvalidImageException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading image", e);
        }
    }
}


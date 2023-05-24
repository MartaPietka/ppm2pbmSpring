package com.github.martapietka.ppm2pbmSpring;

import com.github.martapietka.ppm2pbm.InvalidImageException;
import com.github.martapietka.ppm2pbm.PpmConverter;
import com.github.martapietka.ppm2pbm.PpmToPbmConverter;
import com.github.martapietka.ppm2pbm.RgbToGrayscaleByAverageConverter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class Ppm2PbmController {

    private final ImageService imageService;

    public Ppm2PbmController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/convert")
    public ResponseEntity<Resource> convertPpmToPbm(@RequestParam("file")MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PpmConverter ppmConverter = new PpmToPbmConverter(128, new RgbToGrayscaleByAverageConverter());
            ppmConverter.convert(inputStream, outputStream);

            InputStream convertedInputStream = new ByteArrayInputStream(outputStream.toByteArray());
            Resource imageResource = imageService.getImage(convertedInputStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/png"))
                    .body(imageResource);

        } catch (IOException | InvalidImageException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading image", e);
        }
    }
}


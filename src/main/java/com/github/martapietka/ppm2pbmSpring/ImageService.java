package com.github.martapietka.ppm2pbmSpring;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageService {

    public Resource getImage(String imagePath) throws IOException {

        File imageFile = new File("/Users/martapietka/Downloads/" + imagePath);

        if (!imageFile.exists()) {
            throw new FileNotFoundException("File not found: " + imagePath);
        }

        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        return new ByteArrayResource(imageBytes);
    }
}

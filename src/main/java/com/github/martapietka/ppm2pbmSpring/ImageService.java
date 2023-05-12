package com.github.martapietka.ppm2pbmSpring;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageService {

    public byte[] getImage(String imagePath) throws IOException {

        File imageFile = new File("/Users/martapietka/Downloads/" + imagePath);

        if (!imageFile.exists()) {
            throw new FileNotFoundException("File not found: " + imagePath);
        }

        return Files.readAllBytes(imageFile.toPath());
    }
}

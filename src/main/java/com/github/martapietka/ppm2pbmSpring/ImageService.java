package com.github.martapietka.ppm2pbmSpring;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class ImageService {

    public byte[] getImage(String imagePath) throws IOException {

        File imageFile = new File("/Users/martapietka/Downloads/" + imagePath);

        if (!imageFile.exists()) {
            throw new FileNotFoundException("File not found: " + imagePath);
        }

        var image = ImageIO.read(imageFile);
        var byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
}

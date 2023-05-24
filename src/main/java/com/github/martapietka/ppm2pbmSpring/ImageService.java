package com.github.martapietka.ppm2pbmSpring;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.*;

@Service
public class ImageService {

    public Resource getImage(InputStream inputStream) throws IOException {

        var image = ImageIO.read(inputStream);
        var byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);

        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }
}

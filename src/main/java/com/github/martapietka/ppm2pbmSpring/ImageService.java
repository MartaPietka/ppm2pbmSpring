package com.github.martapietka.ppm2pbmSpring;

import com.github.martapietka.ppm2pbm.InvalidImageException;
import com.github.martapietka.ppm2pbm.PpmConverter;
import com.github.martapietka.ppm2pbm.PpmToPbmConverter;
import com.github.martapietka.ppm2pbm.RgbToGrayscaleByAverageConverter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.*;

@Service
public class ImageService {

    public InputStream getConvertedInputStream(InputStream inputStream) throws IOException, InvalidImageException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PpmConverter ppmConverter = new PpmToPbmConverter(128, new RgbToGrayscaleByAverageConverter());
        ppmConverter.convert(inputStream, outputStream);

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public Resource getImage(InputStream inputStream) throws IOException {

        var image = ImageIO.read(inputStream);
        var byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);

        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }
}

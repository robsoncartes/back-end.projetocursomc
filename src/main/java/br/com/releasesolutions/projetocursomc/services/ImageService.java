package br.com.releasesolutions.projetocursomc.services;

import br.com.releasesolutions.projetocursomc.services.exceptions.FileException;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public BufferedImage getPngImageFromFile(MultipartFile uploadedFile) {

        String extension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());

        if (!"png".equals(extension) && !"jpg".equals(extension))
            throw new FileException("Somente imagens com formato PGN ou JPG são permitidas.");

        try {
            BufferedImage image = ImageIO.read(uploadedFile.getInputStream());
            if ("jpg".equals(extension))
                return jpgToPng(image);
            return image;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo.");
        }
    }

    public BufferedImage jpgToPng(BufferedImage image) {

        BufferedImage pngImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        pngImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

        return pngImage;
    }

    public InputStream getInputStream(BufferedImage image, String extension) {

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, extension, outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler o arquivo.");
        }
    }

    public BufferedImage cropSquareImage(BufferedImage sourceImage) {

        int min = (sourceImage.getHeight() <= sourceImage.getWidth()) ? sourceImage.getHeight() : sourceImage.getWidth();

        return Scalr.crop(sourceImage, (sourceImage.getWidth() / 2) - (min / 2), (sourceImage.getHeight() / 2) - (min / 2), min, min);
    }

    public BufferedImage resizeImage(BufferedImage sourceImage, int size) {

        return Scalr.resize(sourceImage, Scalr.Method.ULTRA_QUALITY, size);
    }
}

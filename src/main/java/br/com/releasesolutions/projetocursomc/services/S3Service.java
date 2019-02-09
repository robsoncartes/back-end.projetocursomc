package br.com.releasesolutions.projetocursomc.services;

import br.com.releasesolutions.projetocursomc.services.exceptions.FileException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    private AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String s3BucketName;

    public S3Service(AmazonS3 s3Client) {
        this.amazonS3 = s3Client;
    }

    public URI uploadFile(MultipartFile multipartFile) {

        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();

            return uploadFile(inputStream, fileName, contentType);

        } catch (IOException e) {
            throw new FileException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentType) {

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            LOG.info("Iniciando o upload...");
            amazonS3.putObject(s3BucketName, fileName, inputStream, objectMetadata);
            LOG.info("Upload finalizado.");

            return amazonS3.getUrl(s3BucketName, fileName).toURI();

        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI.");
        }
    }
}

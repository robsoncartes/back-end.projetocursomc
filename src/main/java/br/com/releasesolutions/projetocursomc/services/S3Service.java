package br.com.releasesolutions.projetocursomc.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    private AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String s3BucketName;

    public S3Service(AmazonS3 s3Client) {
        this.amazonS3 = s3Client;
    }

    public void uploadFile(String localFilePath) {

        try {
            LOG.info("Iniciando upload...");
            File file = new File(localFilePath);
            amazonS3.putObject(new PutObjectRequest(s3BucketName, "teste.png", file));
            LOG.info("Upload finalizado.");

        } catch (AmazonServiceException e) {
            LOG.info("AmazonServiceException: " + e.getErrorMessage());
            LOG.info("Status code: " + e.getErrorCode());
        } catch (AmazonClientException e) {
            LOG.info("AmazonClientException: " + e.getMessage());
        }
    }
}

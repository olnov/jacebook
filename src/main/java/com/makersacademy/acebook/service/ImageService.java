package com.makersacademy.acebook.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

@Service
public class ImageService {

    private final S3Client s3Client;

    @Value("${idrive.e2.bucket-name}")
    private String bucketName;

    @Value("${idrive.e2.endpoint}")
    private String endpoint;

    @Value("${idrive.e2.public_endpoint}")
    private String public_endpoint;

    public ImageService(@Value("${idrive.e2.access-key}") String accessKey,
                        @Value("${idrive.e2.secret-key}") String secretKey,
                        @Value("${idrive.e2.endpoint}") String endpoint) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .endpointOverride(URI.create(endpoint))
                .region(Region.AWS_GLOBAL)
                .build();
    }

    public String uploadImage(InputStream imageStream, long contentLength, String contentType) {
        String key = "profiles/" + UUID.randomUUID(); // unique file name

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .contentLength(contentLength) // Specify the content length
                .build();

        s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(imageStream, contentLength));

        // Return the image URL
        return public_endpoint + "/" + bucketName + "/" + key;
    }
}

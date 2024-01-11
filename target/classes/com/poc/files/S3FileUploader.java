import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class S3FileUploader {

    private AmazonS3 s3client;

    public S3FileUploader(String accessKey, String secretKey, String endpoint) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, "us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    public void uploadFiles(String bucketName, String localFolderPath, String awsFolderPath) {
        try {
            List<File> files = Files.list(Paths.get(localFolderPath))
                                    .map(path -> path.toFile())
                                    .collect(Collectors.toList());

            for (File file : files) {
                String key = awsFolderPath.endsWith("/") ? awsFolderPath + file.getName() : awsFolderPath + "/" + file.getName();
                s3client.putObject(new PutObjectRequest(bucketName, key, file));
                System.out.println("Uploaded file: " + file.getName());
            }
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String accessKey = "test";
        String secretKey = "jsahuhsajd87298982sjdhudjsahdsbhjsdh";
        String bucketName = "BDDBUCKET";
        String endpoint = "https://object.lb4.rbsgrp.net";

        String localFolderPath = "path_to_your_local_folder"; // Replace with your local folder path
        String awsFolderPath = "bddbacs/nft/keys/";

        S3FileUploader uploader = new S3FileUploader(accessKey, secretKey, endpoint);
        uploader.uploadFiles(bucketName, localFolderPath, awsFolderPath);
    }
}

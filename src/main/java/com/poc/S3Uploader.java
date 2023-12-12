import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.io.File;

public class S3Uploader {

    private final AmazonS3 s3Client;

    public S3Uploader(String accessKeyId, String secretAccessKey) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(RegionUtils.getRegion().getName()) // Automatically determine the region
                .build();
    }

    public String uploadFile(String bucketName, String key, String filePath) {
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, new File(filePath));
            s3Client.putObject(putObjectRequest);
            System.out.println("File uploaded to S3: " + key);

            // Construct the S3 file URL
            String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, s3Client.getRegionName(), key);
            return fileUrl;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public void deleteExistingFiles(String bucketName, String prefix) {
        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName).withPrefix(prefix);
            ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);

            while (true) {
                for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
                    s3Client.deleteObject(bucketName, summary.getKey());
                }

                // Check if the list is truncated, and get the next batch of objects
                if (objectListing.isTruncated()) {
                    objectListing = s3Client.listNextBatchOfObjects(objectListing);
                } else {
                    break;
                }
            }

            System.out.println("Deleted existing files in S3 bucket: " + bucketName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }
}

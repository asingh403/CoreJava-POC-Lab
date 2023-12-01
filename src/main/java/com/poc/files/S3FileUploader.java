import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class S3FileUploader {

    private AmazonS3 s3client;

    public S3FileUploader(String accessKey, String secretKey) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.DEFAULT_REGION)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    public void uploadFiles(String bucketName, String folderPath, List<String> fileNames) {
        try {
            for (String fileName : fileNames) {
                File file = new File(folderPath + "/" + fileName);
                s3client.putObject(new PutObjectRequest(bucketName, "dev/pgpkeys/" + file.getName(), file));
                System.out.println("Uploaded file: " + fileName);
            }
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }

    public static void main(String[] args) {
        // TODO: Replace with your AWS credentials and the files you want to upload
        String accessKey = "YOUR_ACCESS_KEY";
        String secretKey = "YOUR_SECRET_KEY";
        String bucketName = "bddbucket";

        List<String> fileNames = // Add list of files to upload
        String folderPath = "path_to_your_local_folder";

        S3FileUploader uploader = new S3FileUploader(accessKey, secretKey);
        uploader.uploadFiles(bucketName, folderPath, fileNames);
    }
}

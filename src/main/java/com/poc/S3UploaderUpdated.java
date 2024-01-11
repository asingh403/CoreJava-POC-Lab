import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class S3UploaderUpdated {

    public static void main(String[] args) {
        String accessKeyId = "BDD"; // Replace with your access key
        String secretAccessKey = "Uhjdhhdshhds7w989we,jsdjdsjhdsjhds"; // Replace with your secret key
        String bucketName = "TestSit"; // Replace with your S3 bucket name
        String localFilePath = "/path/to/your/local/file.txt"; // Replace with your local file path
        String s3Key = "path/in/bucket/filename.txt"; // Replace with your desired S3 key (path)

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://object.ib4.jpmc.net", null))
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        try {
            // Upload the file
            s3Client.putObject(new PutObjectRequest(bucketName, s3Key, new File(localFilePath)));
            System.out.println("File uploaded to S3: " + s3Key);

            // Construct the S3 file URL
            String fileUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, s3Key);
            System.out.println("Uploaded file URL: " + fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

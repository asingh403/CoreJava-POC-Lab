public class MainApplication {

    public static void main(String[] args) {
        String accessKeyId = "your_access_key_id";
        String secretAccessKey = "your_secret_access_key";

        S3Uploader s3Uploader = new S3Uploader(accessKeyId, secretAccessKey);

        String bucketName = "your-s3-bucket-name";
        String prefix = "path/in/bucket/";

        // Delete existing files
        s3Uploader.deleteExistingFiles(bucketName, prefix);

        String localFilePath = "/path/to/your/local/file.txt";
        String s3ObjectKey = "path/in/bucket/filename.txt";

        try {
            String uploadedFileUrl = s3Uploader.uploadFile(bucketName, s3ObjectKey, localFilePath);
            System.out.println("Uploaded file URL: " + uploadedFileUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

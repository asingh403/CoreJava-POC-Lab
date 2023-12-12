public class YourMainClass {
    public static void main(String[] args) {
        // Create an instance of S3Uploader
        S3Uploader s3Uploader = new S3Uploader("YourAccessKey", "YourSecretKey", "https://s3.your-region.amazonaws.com");

        // Specify the local file to be uploaded
        String localFilePath = "/path/to/your/local/file.txt"; // Replace with your local file path

        // Specify the S3 bucket name and the key (path in the bucket)
        String bucketName = "your-bucket-name";
        String s3Key = "folderInBucket/file.txt"; // This could be just "file.txt" if you don't want it in a folder

        // Upload the file
        s3Uploader.uploadFile(bucketName, s3Key, localFilePath);
    }
}

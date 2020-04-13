package edc.aws.encryption;

public class BucketClient {

	public static void main(String[] args) {
		
		//String bucketName="coba-edc-bucket";
		
		String bucketName="rinkiya";
		new AwsBucketOperations().createBucket(bucketName);
		new AwsBucketOperations().putDataInBucket( bucketName, "edc", "edc2019");
		//new AwsBucketOperations().putFileInBucket( bucketName,"marksheet","C:\\Users\\earnjra\\eclipse-workspace\\test_files\\first_sem.txt");
	}

}

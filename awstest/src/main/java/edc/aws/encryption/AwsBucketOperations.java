package edc.aws.encryption;

import java.io.File;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AwsBucketOperations {
	
	public void createBucket(String bucketName) 
	{
        try {
        	AmazonS3 s3client=AwsConnection.createConnection();
        	 if (!s3client.doesBucketExistV2(bucketName)) {
        		 s3client.createBucket(new CreateBucketRequest(bucketName));
        		 System.out.println("Bucket name " + bucketName + " has been created");
        	 }else {
        		 System.out.println("Bucket name " + bucketName + " alraedy exist");
        	 }
        	 String bucketLocation = s3client.getBucketLocation(new GetBucketLocationRequest(bucketName));
             System.out.println("Bucket location: " + bucketLocation);
            /* 
             PutObjectRequest request = new PutObjectRequest(bucketName, "", new File(fileName));
             ObjectMetadata metadata = new ObjectMetadata();
             metadata.setContentType("plain/text");
             metadata.addUserMetadata("x-amz-meta-title", "someTitle");
             request.setMetadata(metadata);
             s3Client.putObject(request);*/
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it and returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
	}
	public void putDataInBucket(String bucketName, String dataKey, String dataValue) {
		AmazonS3 s3client=AwsConnection.createConnection();
		s3client.putObject(bucketName, dataKey, dataValue);
		System.out.println("The object has been uploaded");
	}
	
	public void putFileInBucket(String bucketName, String dataKey, String fileNameWithPath) {
		AmazonS3 s3client=AwsConnection.createConnection();
		try{
		PutObjectRequest request = new PutObjectRequest(bucketName, "marksheets", new File(fileNameWithPath));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/text");
        metadata.addUserMetadata("x-amz-meta-title", "marksheet");
        request.setMetadata(metadata);
        s3client.putObject(request);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

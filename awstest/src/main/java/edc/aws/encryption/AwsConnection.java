package edc.aws.encryption;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AwsConnection {

	public static AmazonS3 createConnectionToAwsS3() {
		String accessKeyId="AKIAYK5Y5FWALQRR72EG";
		String accessKeyPassword="ilUkeK03699obzKVjUuqs97vPJs0pp+wuG/n62oO";
		ClientConfiguration config = new ClientConfiguration();
    	config.setProtocol(Protocol.HTTPS);
    	AmazonS3 s3=  new AmazonS3Client(new BasicAWSCredentials(accessKeyId, accessKeyPassword), config);
    	s3.setEndpoint("us-east-2");
    	return s3;
	}
	public static AmazonS3 createConnection() {
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials( new ProfileCredentialsProvider())
                .withRegion("us-east-2")
                .build();
		return s3client;
	}
}

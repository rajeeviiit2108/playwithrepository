package edc.test.homomorphic;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class HomomorphicUtil {
	private static AmazonDynamoDB client =null;
	private static EdcHomorphicEncryption edcHomomorphicEncryption=null;
	public static DynamoDB getDynamoDbInstance() 
	{
		client = AmazonDynamoDBClientBuilder.standard()
				.withRegion(Regions.US_EAST_2)//us-east-2
				.build();  
		return new DynamoDB(client);
	}
}

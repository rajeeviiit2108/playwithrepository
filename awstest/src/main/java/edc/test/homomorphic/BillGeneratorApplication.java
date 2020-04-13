package edc.test.homomorphic;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class BillGeneratorApplication {
	
	public static void main(String[] args) 
	{
		EdcHomorphicEncryption edcHomomorphicEncryption = new EdcHomorphicEncryption();
		saveHomomorphicObject(edcHomomorphicEncryption);
		String tableName="Customer_Billing";
		//createTable(tableName);
		Scanner sc=new Scanner(System.in);
		insertItems(tableName, sc,edcHomomorphicEncryption);
		sc.close();
	}
	private static BigInteger getGenerateBill(EdcHomorphicEncryption edcHomomorphicEncryption, Scanner sc)
	{
		System.out.println("Enter the value of bill generated::: ");
		
		String bill_generated=sc.next();
		BigInteger bill_generated_value = new BigInteger(bill_generated);
		BigInteger bill_generated_value_enc = edcHomomorphicEncryption.Encryption(bill_generated_value);
		System.out.println("Homomorphic encryption of bill generated value " + bill_generated + "::: \n"+ bill_generated_value_enc);
		return bill_generated_value_enc;
	}
	private static BigInteger getCustomerId(EdcHomorphicEncryption edcHomomorphicEncryption, Scanner sc)
	{
		System.out.println("Enter the customer id::: ");
        String cust_id=sc.next();
		BigInteger cust_id_value = new BigInteger(cust_id);
		BigInteger cust_id_value_enc = edcHomomorphicEncryption.Encryption(cust_id_value);
		System.out.println("Homomorphic encryption of customer id  " + cust_id + "::: \n"+ cust_id_value_enc);
		return cust_id_value_enc;
	}
	private static BigInteger getLateFine(EdcHomorphicEncryption edcHomomorphicEncryption, Scanner sc)
	{
		System.out.println("Enter the value of late fine (if any) ::: ");
		String late_fine=sc.next();
		BigInteger late_fine_value = new BigInteger(late_fine);
		BigInteger late_fine_value_enc = edcHomomorphicEncryption.Encryption(late_fine_value);
		System.out.println("Homomorphic encryption of late fine value  " + late_fine + "::: \n"+ late_fine_value_enc);
		return late_fine_value_enc;
	}
	
	private static void createTable(String tableName) 
	{
		try 
		{
            System.out.println("Attempting to create table; please wait...");
            Table table = HomomorphicUtil.getDynamoDbInstance().createTable(tableName,
                Arrays.asList(new KeySchemaElement("cust_id", KeyType.HASH), // Partition
                   new KeySchemaElement("name", KeyType.RANGE)),
                Arrays.asList(new AttributeDefinition("cust_id", ScalarAttributeType.N),
                    new AttributeDefinition("name", ScalarAttributeType.S)),
                new ProvisionedThroughput(10L, 10L));
            	table.waitForActive();
            	System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
        }
        catch (Exception e)
		{
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }	
	}
	private static void insertItems(String tableName,Scanner sc,EdcHomorphicEncryption edcHomomorphicEncryption)
	{
        Table table = HomomorphicUtil.getDynamoDbInstance().getTable(tableName);
        System.out.println("Enter the customer id::: ");
        int cust_id=sc.nextInt();
        System.out.println("Enter the customer name::: ");
        String cust_name=sc.next();
        BigInteger bill_generated_value_enc=getGenerateBill(edcHomomorphicEncryption, sc);
		BigInteger late_fine_value_enc=getLateFine(edcHomomorphicEncryption, sc);
		BigInteger encrypted_sum = bill_generated_value_enc.multiply(late_fine_value_enc).mod(edcHomomorphicEncryption.nsquare);
        try 
        {
            Item item1 = new Item().withPrimaryKey("cust_id", cust_id)
            		.withString("name", cust_name)
            		.withString("bill_generated", bill_generated_value_enc.toString())
            		.withString("late_fine", late_fine_value_enc.toString())
            		.withString("total_bill", encrypted_sum.toString());
            table.putItem(item1);
            System.out.println("!!!!!Data inserted!!!!!");
        }
        catch (Exception e) {
            System.err.println("insert items failed.");
            System.err.println(e.getMessage());
        }
        sc.close();
    }
	private static void saveHomomorphicObject(EdcHomorphicEncryption edcHomomorphicEncryption)
	{
		 try
		 {
	         FileOutputStream fileOut =
	         new FileOutputStream("C:\\Users\\earnjra\\homomorphicobject\\test.txt");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(edcHomomorphicEncryption);
	         out.close();
	         fileOut.close();
	      } catch (IOException i) 
		 {
	         i.printStackTrace();
	      }
	}
}

package edc.test.homomorphic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Scanner;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class CustomerApplication {

	public static void main(String[] args) {
		EdcHomorphicEncryption edcHomomorphicEncryption = getHomomorphicObject();
		String tableName="Customer_Billing";
		String total_bill=selectItems(tableName, edcHomomorphicEncryption);	
		System.out.println("After the homomorphic decryption, the total bill is::: " + edcHomomorphicEncryption.Decryption(new BigInteger(total_bill)).toString());
	}
	private static String selectItems(String tableName, EdcHomorphicEncryption edcHomomorphicEncryption)
	{
		System.out.println("Enter the custome id to get total bill ::: ");
		Scanner sc=new Scanner(System.in);
		int cust_id=sc.nextInt();
		/*String cust_id_input=sc.next();
		BigInteger cust_id=edcHomomorphicEncryption.Encryption(new BigInteger(cust_id_input));*/
		sc.close();
        Table table = HomomorphicUtil.getDynamoDbInstance().getTable(tableName);
        String total_bill=null;
        try 
        {
            QuerySpec querySpec = new QuerySpec()
            	    .withKeyConditionExpression("cust_id = :cust_id ")
            	    .withValueMap(new ValueMap()
            	        .withInt(":cust_id",cust_id));
            ItemCollection<QueryOutcome> items = null;
            Iterator<Item> iterator = null;
            Item item = null;
            items = table.query(querySpec);
            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                 System.out.println( "The total bill fetched from the the table:::" + item.getString("total_bill"));
                 System.out.println("The total bill is in encrypted format.");
                 total_bill=item.getString("total_bill");
            }
        }
        catch (Exception e) {
            System.err.println("Select items failed.");
            System.err.println(e.getMessage());
        }
        return total_bill;
    }
	
	private static EdcHomorphicEncryption getHomomorphicObject()
	{
		EdcHomorphicEncryption edcHomomorphicEncryption=null;
		try
        {    
            FileInputStream file = new FileInputStream("C:\\Users\\earnjra\\homomorphicobject\\test.txt"); 
            ObjectInputStream in = new ObjectInputStream(file); 
            edcHomomorphicEncryption = (EdcHomorphicEncryption)in.readObject(); 
            in.close(); 
            file.close(); 
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
		return edcHomomorphicEncryption;
	}
}

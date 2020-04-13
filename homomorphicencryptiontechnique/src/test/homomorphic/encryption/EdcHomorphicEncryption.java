package test.homomorphic.encryption;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
public class EdcHomorphicEncryption 
{
	private BigInteger random1, random2, key;
	public BigInteger n;
	public BigInteger nsquare;
	private BigInteger g;
	private int bitLength;
	private static String dataValue1;
	private static String dataValue2;
	private static String nextOperation;
	
	public EdcHomorphicEncryption(int bitLengthVal, int certainty)
	{
	KeyGeneration(bitLengthVal, certainty);
	}
	
	public EdcHomorphicEncryption() 
	{
	KeyGeneration(512, 64);
	}
	
	/**
	* Sets up the public key and private key.
	* @param bitLengthVal number of bits of modulus.
	* @param certainty The probability that the new BigInteger represents a prime number 
	* will exceed (1 - 2^(-certainty)). 
	* The execution time of this constructor is proportional to the value of this parameter.
	*/
	
	public void KeyGeneration(int bitLengthVal, int certainty)
	{
	bitLength = bitLengthVal;
	/**
	* random1 and random2 are two large primes. 
	* key = lcm(random1-1, random2-1) = (random1-1)*(random2-1)/gcd(random1-1, random2-1).
	*/
	random1 = new BigInteger(bitLength / 2, certainty, new Random());
	random2 = new BigInteger(bitLength / 2, certainty, new Random());
	n = random1.multiply(random2);
	nsquare = n.multiply(n);
	g = new BigInteger("2");
	key = random1.subtract(BigInteger.ONE).multiply(random2.subtract(BigInteger.ONE)).divide(
			random1.subtract(BigInteger.ONE).gcd(random2.subtract(BigInteger.ONE)));
	}
	
	/**
	* Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. 
	* This function automatically generates random input r (to help with encryption).
	* @param m plaintext as a BigInteger
	* @return ciphertext as a BigInteger
	*/
	public BigInteger Encryption(BigInteger m) 
	{
	BigInteger r = new BigInteger(bitLength, new Random());
	return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
	}
	
	/**
	* Decrypts ciphertext c. plaintext m = L(c^key mod n^2) * u mod n, 
	* where u = (L(g^key mod n^2))^(-1) mod n.
	* @param c ciphertext as a BigInteger
	* @return plaintext as a BigInteger
	*/
	public BigInteger Decryption(BigInteger c) 
	{
	BigInteger u = g.modPow(key, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
	return c.modPow(key, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
	}
	public BigInteger Encryption(BigInteger m, BigInteger r) 
	{
	return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
	}
	public static void main(String[] str) 
	{
	EdcHomorphicEncryption edcHomomorphicEncryption = new EdcHomorphicEncryption();
	Scanner sc=null;
	try 
	{
	sc=new Scanner(System.in);
	System.out.println("Enter the first data value::: ");
	dataValue1=sc.next();
	BigInteger m1 = new BigInteger(dataValue1);
	BigInteger em1 = edcHomomorphicEncryption.Encryption(m1);
	System.out.println("Homomorphic encryption of " + dataValue1 + "::: \n"+ em1);
	
	System.out.println("Enter the second data value::: ");
	dataValue2=sc.next();
	BigInteger m2 = new BigInteger(dataValue2);
	BigInteger em2 = edcHomomorphicEncryption.Encryption(m2);
	System.out.println("Homomorphic encryption of " + dataValue2 + "::: \n"+ em2);
	
	do
	{
		System.out.println("Choose the type of operation from below ::: \n");
		System.out.println("1. Addition");
		System.out.println("2. Multiplication");
		System.out.println("3. Substraction");
		System.out.println("4. Division");
		int operationType=sc.nextInt();
	
		if(1==operationType)
		{
		addition( m1,  m2,  em1,  em2,  edcHomomorphicEncryption);
		}
		else if(2==operationType) 
		{
		multiplication( m1,  m2,  em1,  em2,  edcHomomorphicEncryption);
		}
		else if(3==operationType) 
		{
		substraction( m1,  m2,  em1,  em2,  edcHomomorphicEncryption);
		}
		else if(4==operationType) 
		{
		division( m1,  m2,  em1,  em2,  edcHomomorphicEncryption);
		}
		
		System.out.println("Do you want to perform another operation on the same data set? (Y or N)");
		nextOperation=sc.next();
		if(Objects.equals("N", nextOperation)) 
		{
			System.out.println("\n::: Thank you so much for playing with the Homomorphic encryption:::");
		}
		}while(Objects.equals("Y", nextOperation));
		}
		finally 
		{
			if(Objects.nonNull(sc))
			sc.close();
		}
	}
	private static void addition(BigInteger m1, BigInteger m2, BigInteger em1, BigInteger em2, EdcHomorphicEncryption edcHomomorphicEncryption)
	{
		BigInteger product_em1em2 = em1.multiply(em2).mod(edcHomomorphicEncryption.nsquare);
		BigInteger sum_m1m2 = m1.add(m2).mod(edcHomomorphicEncryption.n);
		System.out.println("The original sum should be ::::" + sum_m1m2.toString());
		System.out.println("The sum of encrypted value of " + dataValue1 + " and " + dataValue2+ " ::: \n"+ em1+em2);
		System.out.println("After the decryption ::: " + edcHomomorphicEncryption.Decryption(product_em1em2).toString());
	}
	private static void multiplication(BigInteger m1, BigInteger m2, BigInteger em1, BigInteger em2, EdcHomorphicEncryption edcHomomorphicEncryption)
	{
		BigInteger expo_em1m2 = em1.modPow(m2, edcHomomorphicEncryption.nsquare);
		BigInteger prod_m1m2 = m1.multiply(m2).mod(edcHomomorphicEncryption.n);
		System.out.println("The original product should be ::::" + prod_m1m2.toString());
		System.out.println("The product of encrypted value of " + dataValue1 + " and " + dataValue2+ " ::: \n"+ em1.multiply(em2));
		System.out.println("After the decryption ::: " + edcHomomorphicEncryption.Decryption(expo_em1m2).toString());
	}
	private static void substraction(BigInteger m1, BigInteger m2, BigInteger em1, BigInteger em2, EdcHomorphicEncryption edcHomomorphicEncryption)
	{
		//work in progress
	}
	private static void division(BigInteger m1, BigInteger m2, BigInteger em1, BigInteger em2, EdcHomorphicEncryption edcHomomorphicEncryption)
	{
		//work in progress
	}
}

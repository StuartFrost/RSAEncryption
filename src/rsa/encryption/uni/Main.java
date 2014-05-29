package rsa.encryption.uni;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

public class Main {
	private Random random = new Random();
	private BigInteger p;
	private BigInteger q;
	public BigInteger n;	
	private BigInteger phi;
	public BigInteger e;
	public BigInteger d;
	private int primeSize = 256;
	
	public Main() {
		p = BigInteger.probablePrime(primeSize, random);
		q = BigInteger.probablePrime(primeSize, random);
		n = p.multiply(q);		
		phi = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
		e = new BigInteger("65537"); //Public key
		checkE();
		d = getD(phi, e); //Private key
	}
	
	/** Calculates the greatest common divisor of two numbers.
	 * @return - A BigInteger with a value of the GCD of the two parameters. */
	public BigInteger gcd(BigInteger a, BigInteger b) {
		if(b.compareTo(BigInteger.ZERO) == 0) {
			return a;
		} else {
			return gcd(b, a.mod(b));
		}
	}
	
	/** Checks 1 < e < phi and that e and phi are co-prime. If they are not, it generates new primes for p & q and checks again.
	 * @param e - Value of e.
	 * @param phi - Value of phi calculated earlier. */
	public void checkE() {
		if(e.compareTo(BigInteger.ONE) == 1 && e.compareTo(phi) == -1 && gcd(phi, e).compareTo(BigInteger.ONE) != 0) {
			p = BigInteger.probablePrime(primeSize, random);
			q = BigInteger.probablePrime(primeSize, random);
			n = p.multiply(q);		
			phi = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
			checkE();
		}
	}
	
	/** Generates the private key using extendedEuclid method.
	 * @param phi - The value of phi passed into extendedEuclid method.
	 * @param e - The value of e to be passed into extendedEuclid method.
	 * @return BigInteger value of d.*/
	public BigInteger getD(BigInteger phi, BigInteger e) {
		BigInteger[] results = extendedEuclid(e, phi);
		BigInteger b = results[1]; //Only 2nd item in array required, others are only used for the calculations
		BigInteger d = BigInteger.ZERO;
		
		//There are 3 possible outcomes for value of b, depending on which IF it falls under determines value of d
		if(b.compareTo(BigInteger.ONE) == 1 && b.compareTo(phi) == -1) {
			d = b;
		} else if(b.compareTo(phi) >= 0) {
			d = b.mod(phi);
		} else if(b.compareTo(BigInteger.ZERO) == -1) {
			d = b.add(phi);
		}
		return d;
	}
	
	/** Recursive algorithm that given 2 BigIntegers, solves euclids extended algorithm. 
	 * @param a - First number, in this case the value of e.
	 * @param b - Second number, in this case value of phi.
	 * @return An array of BigIntegers, the 2nd object used to determine d in getD method.*/
	public BigInteger[] extendedEuclid(BigInteger a, BigInteger b) {
		BigInteger[] results = new BigInteger[3];
		
		if(a.compareTo(BigInteger.ZERO) == 0) {
			results[0] = b;
			results[1] = BigInteger.ZERO;
			results[2] = BigInteger.ONE;
			return results;
		} else {
			BigInteger[] recurse = extendedEuclid(b.mod(a), a);
			BigInteger g = recurse[0];
			BigInteger y = recurse[1];
			BigInteger x = recurse[2];
			results[0] = g;
			results[1] = x.subtract(b.divide(a).multiply(y));
			results[2] = y;
		}				
		return results;
	}
	
	/** Takes a string message and produces an encrypted cipher. Performs message(int form) ^ e % n.
	 * @param message - String to be encrypted.
	 * @param e - Exponent, the power of the message will be raised to.
	 * @param n - The 'mod' number in the equation.
	 * @return BigInteger containing encrypted cipher of message. */
	public BigInteger produceCipher(String message, BigInteger e, BigInteger m) {
		BigInteger msg = new BigInteger(message.getBytes());
		return msg.modPow(new BigInteger("" + e), new BigInteger("" + m));
	}
	
	/** Takes a cipher and decrypts it to its original string form.
	 * @param c - Encrypted message. 
	 * @param d - Value of d calculate in getD method.
	 * @param m - Modulus value, in this case p * q as calculated earlier.*/
	public String decipher(BigInteger c, BigInteger d, BigInteger m) {
		c = c.modPow(d, m);
		byte[] letters = c.toByteArray(); //Transforms decrypted cipher into ascii values, with each item in array representing 1 character
		String result = "";
		for(int i = 0; i < letters.length; i++) {
			result += (char) letters[i]; //Casts ascii values from each item in array to a char which is added to final string
		}
		return result;
	}

	/** Writes a single line of content to a .txt file. 
	 * @param content - String to be written to file.
	 * @param filePath - Location of file to be written to.*/
	public void writeToFile(String content, String filePath) {
		try {
			PrintWriter p = new PrintWriter(filePath);
			p.print(content);
			p.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/** Reads a single line from a .txt file.
	 * @param filePath - Location of file to be read.
	 * @return A String containing content read from file. */
	public String readFile(String filePath) {
		String toReturn = "";
		try {
			BufferedReader r = new BufferedReader(new FileReader(filePath));
			toReturn = r.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
}
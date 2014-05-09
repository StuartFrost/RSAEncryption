package rsa.encryption.uni;

import java.math.BigInteger;
import java.util.Random;

public class Main {
	public Random random = new Random();
	private BigInteger p;
	private BigInteger q;
	private int primeSize = 256;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		p = BigInteger.probablePrime(primeSize, random);
		q = BigInteger.probablePrime(primeSize, random);
		BigInteger n = p.multiply(q);		
		BigInteger phi = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
		BigInteger e = new BigInteger("65537");
		checkE(e, phi);
		BigInteger d = getD(phi, e);
		
		BigInteger cipher = produceCipher("Hello Mr Bond, i've been expecting you since we last met...", e, n);
		System.out.println("Cipher: " + cipher);
		System.out.println("Deciphered: " + decipher(cipher, d, n));
	}
	
	public BigInteger gcd(BigInteger a, BigInteger b) {
		if(b.compareTo(BigInteger.ZERO) == 0) {
			return a;
		} else {
			return gcd(b, a.mod(b));
		}
	}
	
	public void checkE(BigInteger e, BigInteger phi) {
		if(e.compareTo(BigInteger.ONE) == 1 && e.compareTo(phi) == -1 && gcd(phi, e).compareTo(BigInteger.ONE) != 0) {
			p = BigInteger.probablePrime(primeSize, random);
			q = BigInteger.probablePrime(primeSize, random);
			checkE(e, phi);
		}
	}
	
	public BigInteger getD(BigInteger phi, BigInteger e) {
		BigInteger[] results = extendedEuclid(e, phi);
		BigInteger b = results[1];
		BigInteger d = BigInteger.ZERO;
		
		if(b.compareTo(BigInteger.ONE) == 1 && b.compareTo(phi) == -1) {
			d = b;
		} else if(b.compareTo(phi) >= 0) {
			d = b.mod(phi);
		} else if(b.compareTo(BigInteger.ZERO) == -1) {
			d = b.add(phi);
		}
		return d;
	}
	
	/* def egcd(a, b):
	if a == 0:
		return (b, 0, 1)
	else:
		g, y, x = egcd(b % a, a)
	return (g, x - (b // a) * y, y)
	 */
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

	/*BigInteger cipher = new BigInteger("" + 1);
	BigInteger base = new BigInteger(message.getBytes());
	BigInteger modulus = new BigInteger("" + m);
	while(exponent > 0) {
		int currentBit = exponent % 2;
		if(currentBit == 1) {
			cipher = (base.modPow(new BigInteger("" + 2), modulus));
		}
		exponent = exponent / 2;
		base = base.multiply(base).mod(modulus);
	}
	return cipher;*/
	/** Takes a string message and produces an encrypted cipher. Performs message(int form) ^ e % n.
	 * @param message - String to be encrypted.
	 * @param e - Exponent, the power of the message will be raised to.
	 * @param n - The 'mod' number in the equation.
	 * @return int - Encrypted cipher of message.*/
	public BigInteger produceCipher(String message, BigInteger e, BigInteger m) {
		BigInteger msg = new BigInteger(message.getBytes());
		return msg.modPow(new BigInteger("" + e), new BigInteger("" + m));
	}
	
	public String decipher(BigInteger c, BigInteger d, BigInteger n) {
		c = c.modPow(d, n);
		byte[] letters = c.toByteArray();
		String result = "";
		for(int i = 0; i < letters.length; i++) {
			result += (char) letters[i];
		}
		return result;
	}
}
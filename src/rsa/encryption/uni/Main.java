package rsa.encryption.uni;

import java.math.BigInteger;
import java.util.Random;

public class Main {
	public Random random = new Random();
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		BigInteger p = BigInteger.probablePrime(8, random);
		BigInteger q = BigInteger.probablePrime(8, random);
		BigInteger n = p.multiply(q);		
		BigInteger phi = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
		BigInteger e = new BigInteger("65537");
		int d = getTheD(phi.intValue(), e.intValue());
		BigInteger cipher = produceCipher("670", e, n);
		System.out.println(cipher);
		System.out.println(decipher(cipher, d, n));
	}
	
	public int gcd(int a, int b) {
		if(b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}
	
	public int getTheD(int phi, int e) {
		int[] results = extendedEuclid(e, phi);
		int b = results[1];
		int d = 0;
		
		if(0 <= b && b < phi) {
			d = b;
		} else if(b >= phi) {
			d = b % phi;
		} else if(b < 0) {
			d = b + phi;
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
	public int[] extendedEuclid(int a, int b) {
		int[] results = new int[3];
		
		if(a == 0) {
			results[0] = b;
			results[1] = 0;
			results[2] = 1;
			return results;
		} else {
			int[] recurse = extendedEuclid(b % a, a);
			int g = recurse[0];
			int y = recurse[1];
			int x = recurse[2];
			results[0] = g;
			results[1] = x - (b / a) * y;
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
		BigInteger msg = new BigInteger(message);
		return msg.modPow(new BigInteger("" + e), new BigInteger("" + m));
	}
	
	public BigInteger decipher(BigInteger c, int d, BigInteger n) {
		return c.modPow(new BigInteger("" + d), n);
	}
}
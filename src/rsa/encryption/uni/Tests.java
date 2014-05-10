package rsa.encryption.uni;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class Tests {
	Main main = new Main();
	
	@Test
	public void checkExtdEuclid() {
		BigInteger a = new BigInteger("13");
		BigInteger b = new BigInteger("72");
		BigInteger ans = new BigInteger("-11");
		assertEquals(ans, main.extendedEuclid(a, b)[1]);
	}
	
	@Test
	public void checkGCD() {
		BigInteger a = new BigInteger("199");
		BigInteger b = new BigInteger("241");
		BigInteger ans = BigInteger.ONE;
		assertEquals(ans, main.gcd(a, b));
	}
	
	@Test
	public void checkD(){
		BigInteger phi = new BigInteger("60");
		BigInteger e = new BigInteger("13");
		BigInteger ans = new BigInteger("37");
		assertEquals(ans, main.getD(phi, e));
		
		e = new BigInteger("761");
		phi = new BigInteger("21580");
		ans = new BigInteger("7061");
		assertEquals(ans, main.getD(phi, e));
	}
	
	@Test
	public void checkCipher() {
		String str = "Hi";
		BigInteger e = new BigInteger("65537");
		BigInteger n = new BigInteger("223").multiply(new BigInteger("179"));
		BigInteger ans = new BigInteger("9717");
		assertEquals(ans, main.produceCipher(str, e, n));
	}
	
	@Test
	public void checkDecipher() {
		BigInteger phi = new BigInteger("196").multiply(new BigInteger("190"));
		BigInteger e = new BigInteger("65537");
		BigInteger d = main.getD(phi, e);
		BigInteger n = new BigInteger("197").multiply(new BigInteger("191"));
		BigInteger cipher = main.produceCipher("ab", e, n);
		
		assertEquals("ab", main.decipher(cipher, d, n));
	}
}
package rsa.encryption.uni;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class Tests {
	Main main = new Main();
	
	/*@Test
	public void checkExtdEuclid() {
		assertEquals(-11, main.extendedEuclid(13, 72)[1]);
	}
	
	@Test
	public void checkD(){
		int e = 13, phi = 60;
		assertEquals(37, main.getTheD(phi, e));
		
		e = 761;
		phi = 21580;
		assertEquals(7061, main.getTheD(phi, e));
	}
	
	@Test
	public void checkCipher() {
		String str = "Hello Mr Bond";
		int e = 13, n = 7 * 13;
		//assertEquals(73, main.produceCipher(str, e, n));
		
		e = 35771;		
		//assertEquals(5, main.produceCipher(str, e, n));
	}
	
	@Test
	public void checkDecipher() {
		int cipher = 5;
		int p = 7, q = 13, n = p * q, phi = (p-1) * (q-1), e = 35771;
		//int d = main.getTheD(phi, e);
		
		//assertEquals("Hello Mr Bond", main.decipher(cipher, d, n));
	}*/
}
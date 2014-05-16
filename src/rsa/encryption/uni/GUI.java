package rsa.encryption.uni;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI {

	private JFrame frame;
	private Main main;
	private JTextField strToEncrypt;
	private JButton btnCreateCipher;
	private JButton btnDecrypt;
	private JTextField decryptedField;
	private JLabel lblDecryptedString;
	private JTextField cipher;
	private JLabel lblCipher;
	
	private String publicKeyLocation = System.getProperty("user.dir") + "\\Encryption\\publicKey.txt";
	private String privateKeyLocation = System.getProperty("user.dir") + "\\Decryption\\privateKey.txt";
	private String cipherLocation = System.getProperty("user.dir") + "\\Encryption\\cipher.txt";
	private String decryptedStrLocation = System.getProperty("user.dir") + "\\Decryption\\decrypted.txt";
	private String modulusLocation = System.getProperty("user.dir") + "\\Encryption\\modulus.txt";
	
	private BigInteger publicKey;
	private BigInteger privateKey;
	private BigInteger modulus;
	
	/** Launch the application. */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public GUI() {
		main = new Main();
		privateKey = new BigInteger(main.readFile(privateKeyLocation));
		publicKey = new BigInteger(main.readFile(publicKeyLocation));
		modulus = new BigInteger(main.readFile(modulusLocation));
		initialize();
	}

	/** Initialize the contents of the frame, including all action listeners for buttons. */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 200, 315);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		strToEncrypt = new JTextField();
		strToEncrypt.setBounds(10, 31, 159, 20);
		frame.getContentPane().add(strToEncrypt);
		strToEncrypt.setColumns(10);
		
		JLabel lblStringToEncrypt = new JLabel("String to Encrypt");
		lblStringToEncrypt.setBounds(10, 11, 102, 14);
		frame.getContentPane().add(lblStringToEncrypt);
		
		JButton btnGenerateKeys = new JButton("Generate Keys");
		btnGenerateKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main = new Main(); //Generates new values to be written
				main.writeToFile(main.e.toString(), publicKeyLocation);
				main.writeToFile(main.d.toString(), privateKeyLocation);
				main.writeToFile(main.n.toString(), modulusLocation);
				cipher.setText("");
				decryptedField.setText("");
			}
		});
		btnGenerateKeys.setBounds(10, 175, 159, 23);
		frame.getContentPane().add(btnGenerateKeys);
		
		btnCreateCipher = new JButton("Create Cipher");
		btnCreateCipher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				publicKey = new BigInteger(main.readFile(publicKeyLocation));
				modulus = new BigInteger(main.readFile(modulusLocation));
				String cipherText = main.produceCipher(strToEncrypt.getText(), publicKey, modulus).toString();
				cipher.setText(cipherText);
				main.writeToFile(cipherText, cipherLocation);
			}
		});
		btnCreateCipher.setBounds(10, 209, 159, 23);
		frame.getContentPane().add(btnCreateCipher);
		
		btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				privateKey = new BigInteger(main.readFile(privateKeyLocation));
				modulus = new BigInteger(main.readFile(modulusLocation));
				String decryptedText = main.decipher(new BigInteger(cipher.getText()), privateKey, modulus);
				decryptedField.setText(decryptedText);
				main.writeToFile(decryptedText, decryptedStrLocation);
			}
		});
		btnDecrypt.setBounds(10, 243, 159, 23);
		frame.getContentPane().add(btnDecrypt);
		
		decryptedField = new JTextField();
		decryptedField.setColumns(10);
		decryptedField.setBounds(10, 144, 159, 20);
		frame.getContentPane().add(decryptedField);
		decryptedField.setEditable(false);
		
		lblDecryptedString = new JLabel("Decrypted String");
		lblDecryptedString.setBounds(10, 119, 102, 14);
		frame.getContentPane().add(lblDecryptedString);
		
		cipher = new JTextField();
		//cipher.setEditable(false);
		cipher.setBounds(10, 87, 159, 20);
		frame.getContentPane().add(cipher);
		cipher.setColumns(10);
		
		lblCipher = new JLabel("Cipher");
		lblCipher.setBounds(10, 62, 46, 14);
		frame.getContentPane().add(lblCipher);
	}
}
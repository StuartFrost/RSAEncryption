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
	private Main main = new Main();
	private JTextField strToEncrypt;
	private JButton btnCreateCipher;
	private JButton btnDecrypt;
	private JTextField decryptedField;
	private JLabel lblDecryptedString;
	private JTextField cipher;
	private JLabel lblCipher;
	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
				main.writeToFile(main.e.toString(), System.getProperty("user.dir") + "\\publicKey.txt");
				main.writeToFile(main.d.toString(), System.getProperty("user.dir") + "\\privateKey.txt");		
				cipher.setText("");
				decryptedField.setText("");
				main = new Main();
			}
		});
		btnGenerateKeys.setBounds(10, 175, 159, 23);
		frame.getContentPane().add(btnGenerateKeys);
		
		btnCreateCipher = new JButton("Create Cipher");
		btnCreateCipher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cipherText = main.produceCipher(strToEncrypt.getText(), main.e, main.n).toString();
				cipher.setText(cipherText);
			}
		});
		btnCreateCipher.setBounds(10, 209, 159, 23);
		frame.getContentPane().add(btnCreateCipher);
		
		btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String decryptedText = main.decipher(new BigInteger(cipher.getText()), main.d, main.n);
				decryptedField.setText(decryptedText);
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
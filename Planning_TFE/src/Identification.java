import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Panel;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import java.awt.Label;


public class Identification {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Identification window = new Identification();
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
	public Identification() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 627, 469);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("top");
		panel_1.setBackground(new Color(51, 153, 204));
		panel_1.setBounds(161, 151, 302, 29);
		frame.getContentPane().add(panel_1);
		
		JLabel lblOuvrirSession = new JLabel("Ouvrir session");
		lblOuvrirSession.setForeground(new Color(255, 255, 255));
		panel_1.add(lblOuvrirSession);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(51, 153, 204), 1, true));
		panel.setBounds(161, 180, 302, 164);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Saisir votre nom d'utilisateur et mot de passe");
		lblNewLabel.setBounds(43, 6, 215, 14);
		lblNewLabel.setForeground(new Color(51, 153, 204));
		panel.add(lblNewLabel);
		
		JLabel lblNomDutilisateur = new JLabel("Nom d'utilisateur:");
		lblNomDutilisateur.setForeground(new Color(51, 153, 204));
		lblNomDutilisateur.setBounds(43, 49, 98, 14);
		panel.add(lblNomDutilisateur);
		
		textField = new JTextField();
		textField.setBounds(141, 46, 108, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setForeground(new Color(51, 153, 204));
		lblMotDePasse.setBounds(43, 86, 83, 14);
		panel.add(lblMotDePasse);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(141, 83, 108, 20);
		panel.add(passwordField);
		
		JButton btnNewButton = new JButton("Ovrir session");
		btnNewButton.setForeground(new Color(102, 153, 204));
		btnNewButton.setBounds(184, 130, 108, 23);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(Identification.class.getResource("/Icons/icon_ECN.JPG")));
		lblNewLabel_1.setBounds(24, 11, 107, 129);
		frame.getContentPane().add(lblNewLabel_1);
	}
}

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Identification {

	private JFrame frame;
	private JTextField loginField;
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
		frame.setBounds(100, 100, 668, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("top");
		panel_1.setBackground(new Color(51, 153, 204));
		panel_1.setBounds(161, 151, 361, 29);
		frame.getContentPane().add(panel_1);
		
		JLabel lblOuvrirSession = new JLabel("Ouvrir session");
		lblOuvrirSession.setForeground(new Color(255, 255, 255));
		panel_1.add(lblOuvrirSession);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(51, 153, 204), 1, true));
		panel.setBounds(161, 180, 361, 164);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Saisir votre nom d'utilisateur et mot de passe");
		lblNewLabel.setBounds(43, 6, 249, 14);
		lblNewLabel.setForeground(new Color(51, 153, 204));
		panel.add(lblNewLabel);
		
		JLabel lblNomDutilisateur = new JLabel("Nom d'utilisateur:");
		lblNomDutilisateur.setForeground(new Color(51, 153, 204));
		lblNomDutilisateur.setBounds(43, 49, 98, 14);
		panel.add(lblNomDutilisateur);
		
		loginField = new JTextField();
		loginField.setBounds(141, 46, 151, 20);
		panel.add(loginField);
		loginField.setColumns(10);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setForeground(new Color(51, 153, 204));
		lblMotDePasse.setBounds(43, 86, 83, 14);
		panel.add(lblMotDePasse);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(141, 83, 151, 20);
		panel.add(passwordField);
		
		JButton btnNewButton = new JButton("Ovrir session");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try{
					String login, mot_de_passe;
					login=loginField.getText();
					System.out.println(login);
					mot_de_passe=passwordField.getText();
					System.out.println(mot_de_passe);
					boolean b = login.equalsIgnoreCase("enseignant");
					System.out.println(b);
					if (login.equals("enseignant") && mot_de_passe.equals("enseignant"))
					{
					 // JOptionPane.showMessageDialog(null, "Correcte ! ");
			            Enseignant enseignant = new Enseignant();
			            System.out.println("enseignant inscrit");
			            enseignant.frame.setVisible(true);			            
					}
					else 
						if (login.equals("responsable") && mot_de_passe.equals("responsable"))
						{
						 // JOptionPane.showMessageDialog(null, "Correcte ! ");
				          Responsable1_Importer_donnes responsable= new Responsable1_Importer_donnes();
				            System.out.println("responsable inscrit");
				            responsable.frame.setVisible(true);			            
						}
						System.out.println("nn");
					}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Identifiant invalide");
				}
			}
		});
		
		
		/***Pour la connexion apres de la base 
		 * nous utilisons ce bout de code
		 *  try{
       
        Rs = St.executeQuery("Select * From acceuil WHERE nom = '"+nom.getText()+"'");
       SS = Si.executeQuery("Select * From acceuil WHERE passe = '"+passe.getText()+"'");
          
            if((Rs.next()) && (SS.next())){
             JOptionPane.showMessageDialog(null, "Correcte ! ");
            choix acc = new choix();
             acc.setVisible(true);
            
         }
         else 
             JOptionPane.showMessageDialog(null,"Identifiant invalide" );
     }
     catch(Exception e){
         JOptionPane.showMessageDialog(null,"Identifiant invalide" +e.getMessage());
     } 
		 */
		
		
		
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
			}
			});
		btnNewButton.setForeground(new Color(102, 153, 204));
		btnNewButton.setBounds(218, 130, 133, 23);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Identification.class.getResource("/Icons/icon_ECN.JPG")));
		lblNewLabel_1.setBounds(24, 11, 64, 107);
		frame.getContentPane().add(lblNewLabel_1);
	}
}

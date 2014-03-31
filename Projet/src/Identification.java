import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

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

import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

import com.lowagie.text.Font;

import choco.kernel.model.constraints.cnf.ALogicTree.Type;

import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.border.BevelBorder;


public class Identification {

	private JFrame frame;
	private JTextField loginField;
	private JPasswordField passwordField;
	private int id_utilisateur = 0;
	 String nomprenomutilisateur ="";
	
    
	public String getNomprenomutilisateur() {
		return nomprenomutilisateur;
	}

	public void setNomprenomutilisateur(String nomprenomutilisateur) {
		this.nomprenomutilisateur = nomprenomutilisateur;
	}

	public int getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Responsable1_Importer_donnes.class.getResource("/Icons/icon_ECN.JPG")));
		frame.getContentPane().setPreferredSize(new Dimension(200, 100));
		frame.setPreferredSize(new Dimension(68, 0));
		frame.setBounds(100, 100, 1700, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("top");
		panel_1.setBackground(new Color(51, 153, 204));
		panel_1.setBounds(556, 332, 453, 47);
		frame.getContentPane().add(panel_1);
		
		JLabel lblOuvrirSession = new JLabel("Ouvrir session");
		lblOuvrirSession.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 15));
		lblOuvrirSession.setForeground(new Color(255, 255, 255));
		panel_1.add(lblOuvrirSession);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(51, 153, 204), 1, true));
		panel.setBounds(556, 376, 453, 241);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Saisir votre nom d'utilisateur et mot de passe");
		lblNewLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 15));
		lblNewLabel.setBounds(59, 34, 349, 14);
		lblNewLabel.setForeground(new Color(51, 153, 204));
		panel.add(lblNewLabel);
		
		JLabel lblNomDutilisateur = new JLabel("Nom d'utilisateur:");
		lblNomDutilisateur.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
		lblNomDutilisateur.setForeground(new Color(51, 153, 204));
		lblNomDutilisateur.setBounds(59, 85, 151, 14);
		panel.add(lblNomDutilisateur);
		
		loginField = new JTextField();
		loginField.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13));
		loginField.setBounds(243, 85, 151, 20);
		panel.add(loginField);
		loginField.setColumns(10);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
		lblMotDePasse.setForeground(new Color(51, 153, 204));
		lblMotDePasse.setBounds(59, 122, 151, 14);
		panel.add(lblMotDePasse);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13));
		passwordField.setBounds(243, 122, 151, 20);
		panel.add(passwordField);
		
		JButton btnNewButton = new JButton("Ouvrir session");
		btnNewButton.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));

		
	btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {

				
				String login, mot_de_passe;
				login=loginField.getText();
				mot_de_passe=passwordField.getText();
				System.out.println("login"+login);
				
				if (login.equals("responsable") && mot_de_passe.equals("123456")){
					System.out.println(login);
					
					try {
						Responsable1_Importer_donnes respo;
						respo = new Responsable1_Importer_donnes();
						respo.frame.setVisible(true);
					    frame.dispose();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				else{
					
				try{
					
					ReadExcel listeEnseignant = new ReadExcel();
					
					listeEnseignant.setInputFile("ListeEnseignant.xls");
					try {
						 listeEnseignant.read();
				 } catch (IOException e2) {
				 		// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String [][]matriceEnseignant  = null ;
					
					try {
						matriceEnseignant = listeEnseignant.getMatriceEnseignant(); /// on prend en compte la 1 ere ligne
					
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					
					String nomEnseignant="";
					
					// stoker les enseignants dans une matrice de string  2: colonne id + nom et prenom  
					// le vecteur contient dans la ligne 0 les données
					
					String [][] vectEnseignant = new String [matriceEnseignant.length][2]; /// contient la liste des enseignants dans le fichier "ListeEnseignant.xls"
					
					for (int j = 0 ; j<matriceEnseignant.length; j++){
						
						nomEnseignant="";
						vectEnseignant[j][0]=matriceEnseignant[j][0];
						for (int k = 1 ; k<matriceEnseignant[0].length; k++){
						
							nomEnseignant = nomEnseignant.trim()+ " "+ matriceEnseignant[j][k].trim()  ;
							vectEnseignant[j][1]=nomEnseignant.trim();
							
						}
						
					}
					boolean nomExsiste = false ; 
					int indice=0;
						for (int i = 0 ; i< vectEnseignant.length; i++){
							if (vectEnseignant[i][1].equalsIgnoreCase(login.trim())){
								nomExsiste = true ; 
								indice++;
								break;
							}
						}
					
							if (nomExsiste == true && mot_de_passe.equals("123456")){
								setId_utilisateur(Integer.parseInt(vectEnseignant[indice][0]));
								setNomprenomutilisateur(vectEnseignant[indice][1]);
								Interface_Enseignant interEnsei= new Interface_Enseignant( Integer.parseInt(vectEnseignant[indice][0]),  vectEnseignant[indice][1]  );
								interEnsei.frame.setVisible(true);		
					            frame.dispose();
					            
							}
						
							else {
								JOptionPane.showMessageDialog(null, "Login ou Mot de passe incorrect ! ");
							}
						
					//////////ouveture de l interface enseignant
					
					}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Erreur invalide");
			   }
			
			
			}
			}
			
			
			});
		btnNewButton.setForeground(new Color(102, 153, 204));
		btnNewButton.setBounds(275, 189, 133, 41);
		panel.add(btnNewButton);
		
		JLabel label = new JLabel("");
		label.setBounds(122, 42, 111, 141);
		label.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/icon_ECN.JPG")));
		frame.getContentPane().add(label);
		
		JLabel lblNewLabel1 = new JLabel("_____________________________________________________");
		lblNewLabel1.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 40));
		
		
		lblNewLabel1.setForeground(new Color(0, 153, 204));
		lblNewLabel1.setBackground(Color.RED);
		lblNewLabel1.setBounds(132, 131, 1397, 82);
		frame.getContentPane().add(lblNewLabel1);
		
		JLabel lblNewLabel_6 = new JLabel("Planning des soutenances TFE");
		lblNewLabel_6.setForeground(new Color(0, 153, 204));
		lblNewLabel_6.setFont(new java.awt.Font("Calibri", Font.BOLD, 40));
		lblNewLabel_6.setBounds(539, 116, 534, 47);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/calendrier-icone-4830-64.png")));
		label_4.setBounds(1325, 78, 95, 105);
		frame.getContentPane().add(label_4);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/horloge-heure-icone-6905-96.png")));
		label_3.setBounds(1275, 54, 118, 96);
		frame.getContentPane().add(label_3);
		
		
		
	}
}

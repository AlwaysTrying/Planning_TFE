import java.awt.EventQueue;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Window.Type;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;


public class Responsable1_Importer_donnes {

	 JFrame frame;
	 requete_base_donne rq = new requete_base_donne();
	 String date_soutenance="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Responsable1_Importer_donnes window = new Responsable1_Importer_donnes();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Responsable1_Importer_donnes() throws ClassNotFoundException, SQLException {
		initialize();
	}
	
	
	private void openActionPerformed(java.awt.event.ActionEvent evt) {                                     

	     //création dun nouveau filechosser
	    JFileChooser chooser = new JFileChooser();

	//intitulé du bouton
	chooser.setApproveButtonText("Choix du fichier"); 
	//affiche la boite de dialogue 
	chooser.showOpenDialog(null); 
	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
	{
	AbstractButton status = null;
	status.setText(chooser.getSelectedFile().getAbsolutePath());
	}                                    
	}
	
	private String showOpenFileDialog(){
		
		FileFilter xls = new FiltreSimple("Fichiers xls",".xls");
		JFileChooser chooser = new JFileChooser();
		//chooser.setFileSelectionMode(JFileChooser..DIRECTORIES_ONLY);
		chooser.setCurrentDirectory(new File("/"));
		//chooser.changeToParentDirectory();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(xls);
		
		
		
		int retour=chooser.showOpenDialog(null);	
	
		if(retour==JFileChooser.APPROVE_OPTION){
			   // un fichier a été choisi (sortie par OK)
			   // nom du fichier  choisi 
			chooser.getSelectedFile().getName();
			   // chemin absolu du fichier choisi
			chooser.getSelectedFile().getAbsolutePath();
			  
			   //System.out.println(chooser.getSelectedFile().getAbsolutePath());
			   return chooser.getSelectedFile().getAbsolutePath();
			   
			}else
				//System.out.println("ici pas de fichier choisie !");// pas de fichier choisi
				return null;
		
		
	}
	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
		private void initialize() throws ClassNotFoundException, SQLException {
			
			
			rq.connexion_base();
			frame = new JFrame();
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Responsable1_Importer_donnes.class.getResource("/Icons/icon_ECN.JPG")));
			System.out.println("ici");
			frame.getContentPane().setPreferredSize(new Dimension(200, 100));
			frame.setPreferredSize(new Dimension(68, 0));
			frame.setType(Type.POPUP);
			frame.setBounds(100, 100, 1700, 900);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setExtendedState(frame.MAXIMIZED_BOTH);
			frame.getContentPane().setLayout(null);
			
			JLabel lblNewLabel_3 = new JLabel("Planning des soutenances TFE");
			lblNewLabel_3.setForeground(new Color(0, 153, 204));
			lblNewLabel_3.setFont(new Font("Calibri", Font.BOLD, 40));
			lblNewLabel_3.setBounds(539, 116, 534, 47);
			frame.getContentPane().add(lblNewLabel_3);
			
			
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(318, 224, 1200, 600);
			panel_1.setBorder(null);
			frame.getContentPane().add(panel_1);
			panel_1.setLayout(null);
			
			final JButton btnParcourrir = new JButton("Parcourrir");
			btnParcourrir.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/dossier-ouvrez-icone-4960-32.png")));
			btnParcourrir.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnParcourrir.setBounds(251, 443, 135, 54);
			panel_1.add(btnParcourrir);
			btnParcourrir.setVisible(false);
			
			final JButton btnAfficher = new JButton("Afficher Planning");
			btnAfficher.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				try {
					Emploi emp = new Emploi();
					emp.frame.setVisible(true);
					frame.dispose();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
					
					
				}
			});
			btnAfficher.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/bureau-presentation-x-icone-9742-32.png")));
			btnAfficher.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnAfficher.setBounds(505, 443, 192, 54);
			panel_1.add(btnAfficher);
			btnAfficher.setVisible(false);
			
			JButton btnQuitter = new JButton("Quitter");
			btnQuitter.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/balle-fermer-cute-stop-icone-4372-32.png")));
			btnQuitter.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnQuitter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					frame.dispose();
				}
			});
			btnQuitter.setBounds(715, 443, 135, 54);
			panel_1.add(btnQuitter);
			
			JPanel panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_2.setBounds(71, 11, 848, 163);
			panel_1.add(panel_2);
			
			
			JLabel lblVeuillezChoisirLa = new JLabel("Veuillez choisir la date pr\u00E9vue des soutenances :");
			lblVeuillezChoisirLa.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblVeuillezChoisirLa.setBounds(114, 85, 385, 67);
			panel_2.add(lblVeuillezChoisirLa);
			panel_2.setVisible(false);
			
		
			
			final JDateChooser dateChooser = new JDateChooser();
			dateChooser.getCalendarButton().setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/calendrier-icone-4830-32.png")));
			dateChooser.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
				
				
			});
			dateChooser.setBounds(474, 102, 217, 35);
			panel_2.add(dateChooser);
			
			JButton btnEnregistrer = new JButton("Enregistrer");
			btnEnregistrer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					java.util.Date variableDate=dateChooser.getDate();
					//System.out.println( variableDate);

					if (variableDate == null){
						JOptionPane.showMessageDialog(null, " Choisir la date des soutenances");
					}
					else {
						java.util.Date date_util = new java.util.Date();
						java.sql.Date date_sql = new java.sql.Date(variableDate.getTime());
						//System.out.println("date_soutenance "+ date_soutenance);
						try {
							
							boolean existe1 = rq.getDateSoutenance();
							if (existe1 == false ) {
								rq.insertDateSoutenance(date_sql.toString());
								JOptionPane.showMessageDialog(null, " la date des soutenances est enregistrée");
							}
							else {
								rq.deleteDateSoutenance() ;
								rq.insertDateSoutenance(date_sql.toString());
								JOptionPane.showMessageDialog(null, " la date des soutenances est enregistrée");
							}
							
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			});
			btnEnregistrer.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/disque-un-stylo-enregistrer-enregistrer-sous-ecrivez-icone-5740-32.png")));
			btnEnregistrer.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnEnregistrer.setBounds(242, 518, 144, 54);
			btnEnregistrer.setVisible(false);
			panel_1.add(btnEnregistrer);
			
			
			JPanel panel = new JPanel();
			panel.setBounds(71, 200, 848, 245);
			panel_1.add(panel);
			panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel.setLayout(null);
			panel.setVisible(false);
			
			final JLabel lblVeuillezIndiquerLe = new JLabel("Veuillez indiquer le chemin du fichier contenant les donn\u00E9es des \u00E9leves");
			lblVeuillezIndiquerLe.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblVeuillezIndiquerLe.setBounds(208, 66, 556, 67);
			panel.add(lblVeuillezIndiquerLe);
			
			final JLabel label_1 = new JLabel("NB : Les informations des étudiants doivent obéir à la forme standard des données");
			label_1.setForeground(new Color(255, 0, 0));
			label_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
			label_1.setBounds(79, 184, 556, 67);
			panel.add(label_1);
			
			JLabel label_2 = new JLabel("");
			label_2.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/info-icone-7967-64.png")));
			label_2.setBounds(96, 66, 67, 74);
			panel.add(label_2);
			
			JLabel lblLaDateChoisie = new JLabel("La date choisie des soutenance :");
			lblLaDateChoisie.setForeground(new Color(72, 61, 139));
			lblLaDateChoisie.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblLaDateChoisie.setBounds(208, 11, 385, 67);
			panel.add(lblLaDateChoisie);
			
			JLabel label = new JLabel("");
			label.setBounds(122, 42, 111, 141);
			label.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/icon_ECN.JPG")));
			frame.getContentPane().add(label);
			
			JLabel lblNewLabel = new JLabel("_____________________________________________________");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
			lblNewLabel.setForeground(new Color(0, 153, 204));
			lblNewLabel.setBackground(Color.RED);
			lblNewLabel.setBounds(132, 131, 1397, 82);
			frame.getContentPane().add(lblNewLabel);
			
			JLabel label_4 = new JLabel("");
			label_4.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/calendrier-icone-4830-64.png")));
			label_4.setBounds(1325, 78, 95, 105);
			frame.getContentPane().add(label_4);
			
			JLabel label_3 = new JLabel("");
			label_3.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/horloge-heure-icone-6905-96.png")));
			label_3.setBounds(1275, 54, 118, 96);
			frame.getContentPane().add(label_3);
			
			/**************************************************************************************/
				
			boolean exsite = rq.getDateSoutenance();
			System.out.println(exsite);
			if (exsite == false){
				panel_2.setVisible(true);
				panel_2.setBounds(71, 100, 848, 263);
				btnEnregistrer.setBounds(552,400,144,54);
				btnQuitter.setBounds(715, 400, 135, 54);
				btnEnregistrer.setVisible(true);
			}
			else if (exsite == true){
				/// si la date est déha enregistré dans la base
				date_soutenance= rq.getDate_Soutenance();
				
				lblLaDateChoisie.setText(lblLaDateChoisie.getText() + " "+ date_soutenance.toString());
				panel.setBounds(71, 100, 848, 263);
				panel.setVisible(true);
				btnQuitter.setBounds(715, 400, 135, 54);
				btnParcourrir.setBounds(552, 400, 140, 54);
				btnParcourrir.setVisible(true);
			
			}
			
			btnParcourrir.addActionListener(new ActionListener() {
					
				public void actionPerformed(ActionEvent arg0) {
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
					
					String [][] vectEnseignant = new String [matriceEnseignant.length][2]; 
					
					for (int j = 0 ; j<matriceEnseignant.length; j++){
						
						nomEnseignant="";
						vectEnseignant[j][0]=matriceEnseignant[j][0];
						for (int k = 1 ; k<matriceEnseignant[0].length; k++){
						
							nomEnseignant = nomEnseignant.trim()+ " "+ matriceEnseignant[j][k].trim()  ;
							vectEnseignant[j][1]=nomEnseignant.trim();
							
						}
						
					}
					
					insertion_matrice_base i = new insertion_matrice_base();
					
					ReadExcel test = new ReadExcel(); // lecture du fichier des etudiants
					
					/////
					connexion_base cnx = new connexion_base();
					try {
						
						cnx.connexion_base();
						} 
					catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					Statement st = null;
					 try {
						st = cnx.getst();
					} 
					catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						test.setInputFile(showOpenFileDialog());
						test.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int nbl = test.get_nbreligne_fichier();
					int nbc = test.get_nbrecolonne_fichier();
				
					
					String matriceEtudiant [][]  = new String [nbl][nbc];
					
					matriceEtudiant = test.get_matrice();
					
					try {
						i.insertion_matrice(matriceEtudiant,vectEnseignant,st,nbl);
						lblVeuillezIndiquerLe.setText("Les informations des stages des étudiants sont enregistrées");
						label_1.setVisible(false);
						
						
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					btnAfficher.setBounds(520,400,190,54);
					btnAfficher.setVisible(true);
					btnParcourrir.setVisible(false);
			
				}});
			btnParcourrir.addMouseListener(new MouseAdapter() {
			
				
			});
	}
}

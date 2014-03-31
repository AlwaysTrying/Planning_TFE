import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

import choco.kernel.model.constraints.cnf.ALogicTree.Type;
import choco.palm.cbj.search.JumpContradictionException;

import com.mysql.jdbc.Statement;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.ButtonGroup;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;


public class Interface_Enseignant {

	JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JDateChooser dateChooser;
	JRadioButton rdbtnChoisirUneAutre;
	JRadioButton rdbtnConfirmerLaDate;
	static JLabel lblNewLabel_2;
	private static int idEnseignant ; 
	private static String nomEnseignant ; 
	String date_choisie ="";
	requete_base_donne rq = new requete_base_donne();
	String  date_soutenance="";
	
	
	public int getIdEnseignant() {
		return idEnseignant;
	}

	public void setIdEnseignant(int idEnseignant) {
		this.idEnseignant = idEnseignant;
	}

	public String getNoEnseignant() {
		return nomEnseignant;
	}

	public void setNoEnseignant(String noEnseignant) {
		this.nomEnseignant = noEnseignant;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
	
				try {
//					
					Interface_Enseignant window = new Interface_Enseignant(idEnseignant ,nomEnseignant);
					window.frame.setVisible(true);
					System.out.println(idEnseignant + "idEnseignant");
					System.out.println(nomEnseignant + "nomEnseignant");

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
	public Interface_Enseignant() throws SQLException, ClassNotFoundException {
		initialize();
	}
	public Interface_Enseignant(int idEnseignant ,String noEnseignant ) throws SQLException, ClassNotFoundException{
		this.idEnseignant = idEnseignant;
		this.nomEnseignant = noEnseignant;
		
		initialize();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws SQLException, ClassNotFoundException {
		rq.connexion_base();
		
		date_soutenance= rq.getDate_Soutenance();
		date_choisie= date_soutenance;
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Responsable1_Importer_donnes.class.getResource("/Icons/icon_ECN.JPG")));
		frame.getContentPane().setPreferredSize(new Dimension(200, 100));
		frame.setPreferredSize(new Dimension(68, 0));
		frame.setBounds(100, 100, 1700, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		
		
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
		
		
		
		
		JLabel lblNewLabel = new JLabel("Formulaire de disponibilit\u00E9s pour les soutenances TFE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(new Color(51, 153, 204));
		lblNewLabel.setBounds(506, 252, 542, 31);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 0, 0));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 204)));
		panel.setFont(new Font("Tahoma", Font.BOLD, 28));
		panel.setBounds(453, 306, 645, 143);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(26, 45, 37, 47);
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\WAFA\\git\\Planning_TFE\\Planning_TFE\\src\\Icons\\personne.JPG"));
		panel.add(lblNewLabel_1);
		
		JLabel lblNom = new JLabel("Nom et pr\u00E9nom : ");
		lblNom.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNom.setBounds(102, 78, 194, 14);
		panel.add(lblNom);
		
		JLabel lblPrnom = new JLabel("Identifiant Enseignant :");
		lblPrnom.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrnom.setBounds(102, 31, 194, 14);
		panel.add(lblPrnom);
		
		JLabel lblNewLabel_2 = new JLabel(nomEnseignant);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_2.setBounds(298, 78, 276, 14);
		panel.add(lblNewLabel_2);
		
		
		JLabel lblNewLabel_3 = new JLabel("13045987O");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3.setBounds(298, 31, 249, 14);
		panel.add(lblNewLabel_3);
	
		
		final JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 204)));
		panel_1.setBounds(453, 460, 645, 251);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblVeuillezConfirmerLa = new JLabel("Veuillez confirmer la date ou choisir une autre date si vous ne serai pas disponible.");
		lblVeuillezConfirmerLa.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVeuillezConfirmerLa.setBounds(10, 77, 625, 14);
		panel_1.add(lblVeuillezConfirmerLa);
		
		final JDateChooser dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/calendrier-icone-4830-32.png")));

		
	    dateChooser.setBounds(361, 178, 176, 31);
	    panel_1.add(dateChooser);
	    
	    
	    final JPanel panel_2 = new JPanel();
	    panel_2.setLayout(null);
	    panel_2.setVisible(false);
	    panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 204)));
	    panel_2.setBounds(34, 460, 588, 251);
		frame.getContentPane().add(panel_2);
	    
		JLabel lblVeuillezEntrerVos = new JLabel("Veuillez entrer vos disponibilit\u00E9s :");
		lblVeuillezEntrerVos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVeuillezEntrerVos.setBounds(74, 77, 299, 14);
		panel_2.add(lblVeuillezEntrerVos);
		
		JLabel lblCrnau = new JLabel("Disponibilit\u00E9 1 :        De ");
		lblCrnau.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCrnau.setBounds(100, 147, 169, 14);
		panel_2.add(lblCrnau);
		
		final JLabel lblDisponibilit = new JLabel("Disponibilit\u00E9 2 :        De");
		lblDisponibilit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDisponibilit.setBounds(100, 201, 151, 14);
		
		panel_2.add(lblDisponibilit);
		
	    
	    /**************/
		 final Vector comboBoxItems=new Vector();
		 String [] tab1 = {"","8h", "9h", "10h","11h"};
		
		 for (int i = 0 ; i<tab1.length;i++){
			 comboBoxItems.add(tab1[i]); 
			 
		 }

		 final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);

		final JComboBox comboBox = new JComboBox(model);
		
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setToolTipText("");
		comboBox.setBounds(279, 141, 50, 20);
		panel_2.add(comboBox);
		
		JLabel lblA = new JLabel("A:");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblA.setBounds(359, 144, 46, 14);
		panel_2.add(lblA);
		
		final JComboBox comboBox_1 = new JComboBox();
		
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_1.setToolTipText("");
		comboBox_1.setBounds(388, 141, 50, 20);
		panel_2.add(comboBox_1);
		
		  Vector comboBoxItems1=new Vector();
		 String [] tab3 = {"", "14h","15h", "16h","17h"};
		
		 for (int i = 0 ; i<tab3.length;i++){
			 comboBoxItems1.add(tab3[i]); 
			 
		 }

		 final DefaultComboBoxModel model1 = new DefaultComboBoxModel(comboBoxItems1);
		
		
		final JComboBox comboBox_2 = new JComboBox(model1);
		comboBox_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		comboBox_2.setToolTipText("");
		comboBox_2.setBounds(279, 195, 50, 20);
		
		panel_2.add(comboBox_2);
		
		final JLabel label_8 = new JLabel("A:");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_8.setBounds(359, 198, 46, 14);
		panel_2.add(label_8);
		
		final JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_3.setToolTipText("");
		comboBox_3.setBounds(388, 195, 50, 20);
		panel_2.add(comboBox_3);
		
		JLabel lblLaDateDe_1 = new JLabel("La date de soutenance TFE :");
		lblLaDateDe_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLaDateDe_1.setBounds(74, 31, 299, 14);
		panel_2.add(lblLaDateDe_1);
		
		lblLaDateDe_1.setText("La date choisie des soutenance des TFE : ");
		
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
			
				int k = comboBox.getSelectedIndex();
				System.out.println(k);
				String [] tab2 = {"9h", "10h","11h","12h"};
				 Vector comboBoxItems=new Vector();
				for (int i = k-1 ; i<tab2.length;i++){
					 comboBoxItems.add(tab2[i]); 
					 
				 }
				  DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
				comboBox_1.setModel(model);
				
			}
		});
		

		comboBox_2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				int k = comboBox_2.getSelectedIndex();
				System.out.println(k);
				String [] tab2 = {"15h ", "16h","17h","18h"};
				 Vector comboBoxItems=new Vector();
				for (int i = k-1 ; i<tab2.length;i++){
					 comboBoxItems.add(tab2[i]); 
					 
				 }
				  DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
				comboBox_3.setModel(model);
	
			}
		});
		
		
		
		
		/************/
	    
	    
		
		final JRadioButton rdbtnConfirmerLaDate = new JRadioButton("Confirmer la date");
		rdbtnConfirmerLaDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonGroup.add(rdbtnConfirmerLaDate);
		rdbtnConfirmerLaDate.setBounds(92, 134, 151, 23);
		panel_1.add(rdbtnConfirmerLaDate);
		dateChooser.setVisible(false);
		
		final JRadioButton rdbtnChoisirUneAutre = new JRadioButton("Choisir une autre date");
		rdbtnChoisirUneAutre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnChoisirUneAutre.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (rdbtnChoisirUneAutre.isSelected()==false) {
					
					 dateChooser.setVisible(false);
					date_choisie= date_soutenance;
					 //date_choisie /***************************************************************
					 }
				else{
					//System.out.println("nn");
				     
				dateChooser.setVisible(true);
				}
				
			}
		});
		rdbtnChoisirUneAutre.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		rdbtnChoisirUneAutre.addMouseListener(new MouseAdapter() {
	
		});
		
			
		buttonGroup.add(rdbtnChoisirUneAutre);
		rdbtnChoisirUneAutre.setBounds(92, 186, 223, 23);
		panel_1.add(rdbtnChoisirUneAutre);
		
		JLabel lblLaDateDe = new JLabel("La date de soutenance TFE : ");
		lblLaDateDe.setBounds(10, 32, 445, 14);
		panel_1.add(lblLaDateDe);
		lblLaDateDe.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		
		
		
		
	    lblLaDateDe.setText("La date des soutenances des TFE :  ");
	    lblLaDateDe.setText(lblLaDateDe.getText() + " "+ date_soutenance.toString());
	    
	    final JLabel lblLaDateChoisie = new JLabel("");
	    lblLaDateChoisie.setVisible(false);
	    lblLaDateChoisie.setBounds(360, 32, 180, 14);
	    panel_2.add(lblLaDateChoisie);
	    lblLaDateChoisie.setText(date_choisie);
	    lblLaDateChoisie.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/balle-fermer-cute-stop-icone-4372-32.png")));
		btnQuitter.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnQuitter.setBounds(947, 722, 135, 54);
		
		frame.getContentPane().add(btnQuitter);
		
		
		final JButton btnConfirmer = new JButton("Enregistrer");
		btnConfirmer.setIcon(new ImageIcon(Interface_Enseignant.class.getResource("/Icons/disque-un-stylo-enregistrer-enregistrer-sous-ecrivez-icone-5740-32.png")));
		btnConfirmer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String [] x = null;
				x=getDisponibilité(comboBox,comboBox_1,comboBox_2,comboBox_3);
				
				connexion_base cnx = new connexion_base();
				try {
					cnx.connexion_base();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				insert_disponibilite (cnx,x,idEnseignant);
				try {
					insert_date_choisie ( cnx, date_choisie, idEnseignant, nomEnseignant);
					System.out.println("date choisie apres enreg : "+ date_choisie);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, " Informations Enregistrées");
				
			}
		});
		btnConfirmer.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnConfirmer.setVisible(false);
		btnConfirmer.setBounds(614, 722, 141, 54);
		frame.getContentPane().add(btnConfirmer);
		
		
		
		final JButton btnSuivant = new JButton("Suivant");
		btnSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				if (rdbtnChoisirUneAutre.isSelected()==true) {
					
					java.util.Date variableDate=dateChooser.getDate();
					
					if (variableDate == null){
						JOptionPane.showMessageDialog(null, " Choisissez  la date des soutenances");
					}
					else {
						java.util.Date date_util = new java.util.Date();
						java.sql.Date date_sql = new java.sql.Date(variableDate.getTime());
						date_choisie = date_sql.toString();
						
					}
					
				}
				
				else if (rdbtnChoisirUneAutre.isSelected()==false || rdbtnConfirmerLaDate.isSelected()== true){
					
					date_choisie = date_soutenance;
					
					
				}
				panel_2.setVisible(true);
				btnConfirmer.setVisible(true);
				btnConfirmer.setBounds(784, 722, 135, 54);
				panel_2.setBounds(453, 460, 645, 251);
				panel_1.setVisible(false);
				btnSuivant.setVisible(false);
				System.out.println("date_choisie !!!  = " + date_choisie);
				
				lblLaDateChoisie.setText(""+date_choisie.toString());
			    panel_2.add(lblLaDateChoisie);

				lblLaDateChoisie.setVisible(true);
				
	
			}
		});
		btnSuivant.setIcon(new ImageIcon(Interface_Enseignant.class.getResource("/Icons/suivant-icone-7661-32.png")));
		btnSuivant.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				java.util.Date variableDate=dateChooser.getDate();
				System.out.println( variableDate);

				java.util.Date date_util = new java.util.Date();
				java.sql.Date date_sql = new java.sql.Date(variableDate.getTime());
				System.out.println(date_sql);

				 Interface_Enseignant_dispo win = new Interface_Enseignant_dispo();
				
			
				 JFrame frame2 = win.frame;
				 frame2.getComponent(0);
				 frame.getContentPane().setVisible(false);
				JPanel panel3= new JPanel(); 
				panel3.add(frame2);

				 }
		});
		btnSuivant.setBounds(784, 722, 135, 54);
		frame.getContentPane().add(btnSuivant);
	
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
public String[] getDisponibilité(JComboBox comboBox, JComboBox comboBox1, JComboBox comboBox2,JComboBox comboBox3){
		
		String[] disponibilité=new String[4];
		String Dispo= comboBox.getSelectedItem().toString();
		String Dispo1= comboBox1.getSelectedItem().toString();
		String Dispo2 = comboBox2.getSelectedItem().toString();
		String Dispo3 =  comboBox3.getSelectedItem().toString();
		
		disponibilité[0] = Dispo.substring(0,Dispo.length()-1);
		disponibilité[1] = Dispo1.substring(0,Dispo1.length()-1);
		disponibilité[2] = Dispo2.substring(0,Dispo2.length()-1);
		disponibilité[3] = Dispo3.substring(0,Dispo3.length()-1);
		return disponibilité; 
	}

	 static ResultSet  resultSet = null ;static ResultSet  resultSet1 = null ;
	 
	 public void insert_date_choisie (connexion_base cnx,String date_choisie, int id, String nom) throws SQLException{
		 Statement st = null;
		 Statement st1 = null;
		 st1 = (Statement) cnx.getst();
		 st = (Statement) cnx.getst();
		 int nb = 0;
		 String sql1="SELECT count(*) FROM `enseignant` WHERE id_enseignant="+id;
		 resultSet = st.executeQuery(sql1);
		 while (resultSet.next()) 
		  { 
			 String nbre= resultSet.getString("COUNT(*)");
			   nb =Integer.parseInt (nbre);	
			 
		  }
		 
		  if (nb == 0){
			  String sql2=" INSERT INTO `enseignant`(`id_enseignant`, `nom_enseignant`, `prenom_enseignant`,  `date_choisie`) VALUES ( " 
		  + id +" ,' "+ nom +"', '-' ,'"+ date_choisie.toString()+"' )";
			 System.out.println("sql 2 = "+ sql2);
			  st1.executeUpdate(sql2);
		  
		  }	else {
			  
			  String sql2= " UPDATE `enseignant` SET `date_choisie`= '" + date_choisie +"' WHERE `id_enseignant` =" +id ;
			  st1.executeUpdate(sql2); 
		  }
	 }

public void insert_disponibilite (connexion_base cnx,String[]Disponibilité, int id){
	Statement st = null;
	Statement st1 = null;
	Statement st2 = null;
	
	 try {
		 
		st = (Statement) cnx.getst();
		st1 = (Statement) cnx.getst();
		st2 = (Statement) cnx.getst();
		String sql1="SELECT count(*) FROM `disponibilite` WHERE id_enseignant="+id;
		
		String sql ="INSERT INTO `disponibilite`( `disponibilite1`, `disponibilite2`, `disponibilite3`, `disponibilite4`, `id_enseignant`) VALUES ('"+Disponibilité[0]+ "','"+Disponibilité[1]+
				"','"+Disponibilité[2]+
				"','"+Disponibilité[3]+ "',"+id+")";		
		
		
		
		resultSet = st.executeQuery(sql1);
		  int nbreT = 0; int indiceI =0;
		  while (resultSet.next()) 
		  { 
				
			  String nbre= resultSet.getString("COUNT(*)");
			   nbreT =Integer.parseInt (nbre);	
			   System.out.println(nbreT);
			   if(nbreT==0)
				st1.executeUpdate(sql);   
			   else {
				   String sql4="SELECT `id_disponibilite` FROM `disponibilite` WHERE id_enseignant="+id;
				   resultSet1 = st2.executeQuery(sql4);
				   while (resultSet1.next()) 
					  {
					   
					   String indiceS= resultSet1.getString("id_disponibilite");
					   indiceI =Integer.parseInt (indiceS);
					   System.out.println("nbreT indice id_dispo = "+ indiceI);
					  }
				   
				   String sql3="UPDATE `disponibilite` SET `disponibilite1`="+Disponibilité[0]+",`disponibilite2`="+Disponibilité[1]+","
							+ "`disponibilite3`="+Disponibilité[2]+",`disponibilite4`="+Disponibilité[3]+", `id_enseignant` ="+ id + " WHERE  `id_disponibilite`= "+indiceI;

					  st1.executeUpdate(sql3);
				   
			   }
				   
				   
	      }
	 }
		  catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
}
